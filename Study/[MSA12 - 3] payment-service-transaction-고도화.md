# Event

## Transaction 구현이 필요한 곳을 찾아보자 Case 1 - 머니 충전

- 머니 충전 시의 트랜잭션 작업이 필요합니다.

1. 회원확인 (To Membership) (Read)
   1. 실패 시, 종료
2. 펌뱅킹 요청 (To Banking) (Write)
   1. 고객 연결 계좌 -> 페이 법인 계좌 이체 요청
3. 성공 후, Money Service DB Update (Write)
   1. 특정 고객의 잔액을 Update 해야 함
'펌뱅킹 요청은 성공 했지만 머니 DB에 업데이트가 불가능 할 경우' -> 출금은 되지만 머니 잔액이 증액 되지 않을 것임

(Money DB가 문제가 생겼을 경우)
**재시도를 통해서 해결** 
- 머니 서비스가 성공 응답을 받을 때까지 지속적으로 요청을 진행하여야 함.
  - 요청에 대한 부하가 많이 받아진다면? (SPOF)       
  - DLQ (Dead Letter Queue)
    - 재시도 후에도 실패한 작업이 들어가는 큐
    - Timeout 처럼 실패 시키는 것임

-> 비즈니스가 너무 위험성이 높기 때문에 이런식으로 구현하면 금감원에 끌려 갈 수도 있음

**DB에 실행 상태를 기록**
- RDB에 상태 기록을 통해서 트랜잭션을 구현
  - RDB 부하가 많음
  - RDB는 비용 소모가 매우 큼
-> 적절하지 않다.

**결론 : 트랜잭션의 구현이 필수적이다.**

## Transaction 구현이 필요한 곳을 찾아보자 Case 2 - 송금

1. 회원 확인 (To Membership) (Read)
   1. 실패 시, 종료
2. 머니 잔액 확인 (To Money) (Read)
   1. 머니 충분하면 머니 이동 요청 (To Money) Write
   2. 부족하면 뱅킹 충전 요청 (To Banking) Write
      1. 펌뱅킹 완료 후, 머니 이동 요청 (To Money) Write

'펌뱅킹은 되었지만 송금이 되지 않았을 때'
-> 출금이 되어, 머니 충전은 되었지만 외부 은행으로 송금이 실패
-> 사실상, 실패하여도 문제가 되지는 않는다. (내 돈이 사라진 것이 아니기 때문에 고객 영향도가 크지 않다고 볼 수 있습니다.)

**결론 : 트랜잭션 구현을 굳이 해야 할 필요가 없다.**


## Transacion을 위해서 Saga Pattern을 사용하자

### 충전 서비스에 Saga Pattern을 적용해보자

- 기존 서비스는 충전 서비스에서 머니 충전 시에 실패하게 되면 머니 서비스에서 잔액 업데이트를 다시 보상 트랜잭션을 통해서 진행했었습니다.

- (AS-IS) 기존 업데이트 방식 : Banking 서비스의 계좌 연결 시 그대로 업데이트 되어짐
- (TO-BE) 변경된 업데이트 방식 : Banking 서비스의 계좌 연결 시 업데이트가 필요할 경우, Command Handler (Axon Framework), Command를 통해서 계좌 연결 요청의 Command를 Event Queue로 넣어버립니다.
이후, 내부 Consumer가 받아서 Event Store에 넣고 Event Sourcing을 통해서 Axon Framework의 Aggregate 형태로 변환이 되어지고 DB에 저장이 됩니다.

  > 일반적인 서비스에서 큐에 넣는 방식이 무조건 DB에 직접 업데이트 해주는 방식보다 성공률이 높습니다. <br/>
  > 그래서 EDA 구조는 거의 대부분 요청에 대해서 큐를 사용합니다. <br/>
  > 어떻게든 Queue에만 넣으면 DB 상태가 다시 복구가 되면 넣기만 하면 되고 Event Queue에서 문제가 생기더라도 Event Store에 적재된 데이터를 기반으로 정합성을 맞추면 됩니다.

### Axon Framework를 적용한 머니 서비스 리팩토링 플랜

- **EDA**(Event Driven Architecture) 기반으로 리팩토링
- 충전 "Saga" 정의 후 Pattern 적용, Test Monitoring 추가
- 강제로 Transaction을 실패하는 케이스를 정의하고 **성공하는 경우**, **그렇지 않은 경우** 를 예측 할 수 있게 만들어야 함