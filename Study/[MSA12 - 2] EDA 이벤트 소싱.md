# Event

- 이벤트는 누락될 가능성이 거의 없으면서 느슨한 결합을 가능하게 만듭니다.

## Event Driven
- 모든 데이터의 변경은 이벤트를 통해서 처리합니다.

**데이터 영속화 방식 (일반적인 경우)**
- e.g. MemberID가 "3"인 유저를 관악구에서 서초구로 주소정보를 업데이트 해줘
- 위 방식은 기존의 정보는 관심이 없다.
- 중요한 경우 로깅 장치를 구현 해야함

**데이터 영속화 방식 (이벤트 드리븐 방식)**
- 하나의 도메인을 특정 할 수 있는 Key와 변경정보를 가진 이벤트를 발행
- membership svc -> 변경 Event 소비 (Consume) -> 도메인의 최종 버전 정보를 변경
- 장애가 발생하더라도 메세지 브로커에 의존해서 일관성을 구현 할 수 있습니다.

## Event Driven - Request-Driven

- 비동기 방식을 사용하기 때문에 서비스간 통신 중에 100 Request에 대해서 -> 100개의 프로세싱이 진행이 가능합니다.
- 데이터 정합성을 유지 할 수 있습니다.

## EDA의 단점

- Event를 잘못 발행, Consume 이후 잘못된 처리를 하는 순간
- 데이터 정합성 및 트랜잭션이 크게 망가 질 수 있음
- **백업, 통제 정책이 잘 수립되어야 함**

## Eventuate

**Eventuate Local** : 하나의 서비스 환경에서 Event Sourcing의 구현을 도와주는 오픈소스

**Eventuate Tram** : Event Sourcing과 CQRS를 구현을 도와주는 오픈소스

## Axon Framework

**Axon Framework**
- 태생부터 DDD, CQRS, Event Sourcing을 지원하는 프레임워크 (Open Source)
- 레퍼런스, 문서, 커뮤니티가 잘 형성 되어있으므로 사용해본다.
- Spring Boot와 잘 통합되어 사용이 가능


**Axon Server**
- Axon Framework를 사용하여 배포된 어플리케이션의 조율자 역할을 합니다.
- imbedding된 큐잉을 내재하여 유량 조절을 함
- Event를 저장하기도 하고, Event를 발행하기도 합니다. (Routing, Observable)

**Axon Framework의 구성 요소**
- Command Bus : Command를 전달하는 역할
- Command Handler : Command를 처리하는 역할
- Domain, Aggregate : 도메인 모델을 표현하는 역할 (Aggregate : Command를 할 수 있는 도메인 단위)
- Event Bus : Event를 전달하는 역할
- Event Handler : Event를 처리하는 역할

## 결론

- 기존 MSA에서 일반적으로 사용하던 Sync 방식에서 Event 라는 개념이 들어오면서 Event Sourcing을 활용한 Event Driven 모델이 생겼음
- EDA는 일관성 및 정합성을 유지하기 위해서는 어려운 작업이 있습니다.
- Axon Framework를 활용해서 EDA를 보다 쉽게 구현할 수 있습니다. (DDD, CQRS, Event Sourcing을 지원하는 프레임워크)