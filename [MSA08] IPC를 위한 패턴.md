# IPC를 위한 패턴

- IPC : Inter Process Communication
- "프로세스 간 통신" -> "서비스 간 통신" -> MSA
- ![alt text](ImageDirectory/OSI7Layer_TCPIP_Layer.png)
- TCP -> HTTP
- 일반적인 IPC는 크게 2가지로 나뉨
  
## Sync(동기) 방식

- Response를 기다린다. (HTTP, gRPC 방식)
- 선행작업이 필수적인 경우
- 비교적 빠른 작업인 경우

### HTTP (Sync)

- **PUT** : **멱등성** 필수, 리소스 변경을 위한 메서드
- **DELETE** : 리소스를 삭제하기 위한 메서드
- **POST** : 리소스를 생성하기 위한 메서드
- **GET** : 리소스를 가져오기 위한 메서드

### gRPC (Sync)

- Protocol Buffer를 기반으로 하는 원격 프로세저 호출 프레임 워크
- Server to Server Call에서 사용 (Proto 파일을 가지고 직접 서버가 소통해야 함)
- 각 언어마다 스텁을 생성해서 통신을 해야 합니다.
- Risky 함

## ASync(비동기) 방식

- Queue를 이용해서 Produce, Consume을 하는 방식으로 통신을 함
  - MQTT, Kafka, RabbitMQ
- 매우 복잡하고 리소스 소모가 많은 작업의 경우
- 비교적 한정된 컴퓨터 리소스를 가지는 경우
- 응답 대기시간을 최소화 하기 위함
- MSA 환경에서 Event Bus로 통신해서 필요한 것만 통신 해서 쓸 수 있으니 결합도도 낮추고 Queue에 이미 들어간 데이터는 장애가 나더라도 복구 가능하니 사용하는 것이 좋다.


