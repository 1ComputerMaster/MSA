# Kafka

- Kafka는 실시간 이벤트 스트리밍 플랫폼
- 확장 용이성, 고성능 실시간 데이터 처리
- 고가용성, 고성능
- 규모가 작은 비즈니스에서는 비적합한 오픈 소스이다.
- 실시간성이 큰 서비스라면 사용하는데 적합하다.

## Why Kafka?

- **대규모**의 실시간 이벤트/데이터 스트리밍이 필요한 비즈니스에 적합하다.
- -> 트위터, 카카오, 넷플릭스, 우버

## Kafka의 기본 개념

![alt text](ImageDirectory/카프카.png)

**Producer**
- 메세지를 발행하는 **주체**
- Producing 자체가 메세지를 발행하는 동작

**Consumer**
- 메세지를 소비하는 **주체**
- Consume 자체가 메세지를 가져와 처리하는 동작

**Consumer Group**
- 메세지를 소비하는 **주체 집단**

## Kafka 기본 개념 (Cluster, Topic)

**Producer**
- **Kafka Cluster**에 존재하는 **Kafka Broker**에 발행하고 이를 **Broker에 있는 Topic**에 메세지를 발행하는 **주체**

**Consumer**
- **Kafka Cluster**에 존재하는  **Kafka Broker**에 소비하고 이를 **Broker에 있는 Topic**의 메세지를 처리하는 **주체**

**Apache Zookeeper**
- 분산 처리 시스템에서 분산 처리를 위한 코디네이터
- **누가 리더인지, 어느 상황인지, 동기화 상태등을 관리**
- Kafka Cluster를 관리함 (K8S의 마스터 노드와 같은 역할)

## Kafka Topic?
- 메세지를 발행하고 소비 할 수 있는 객체(Object)
- Kafka Topic은 고가용성, 고성능을 구현하는 핵심 개념임
- **비즈니스 별로 토픽 설정 값이 달라 질 수 있다.**

## Kafka Topic (파티션)
- 토픽은 1개 이상의 파티션으로 이루어져 있습니다.
- 하나의 토픽에 포함된 메세지들을 물리적으로 분리하여 저장하는 저장소로 
- 하나의 메세지가 하나의 파티션에 들어가는 형태로 구현 되어 있습니다.
  - Not Sharding it's Partitioning!
- Topic에 Partition은 하나의 메세지가 하나의 Partition 한 개에 들어갑니다. (샤딩 처럼 모든 곳에 나누어져 들어가는 형태가 아닙니다.)
- **파티션 -> 성능과 직결되는 요소입니다.**
- 일반적으로 파티션이 많으면 성능이 향상되어집니다.

## Kafka Topic 파티션과 파티션 레플리카

- Replication Factor는 가용성을 위해서 두는 것입니다.
- 하나의 파티션은, Kafka Cluster 내에서 1개 이상의 복제본을 가질 수 있습니다.
  - RF(Replication Factor는)는 복제본의 갯수를 의미하기 때문에 최소 1개 이상이죠.
- Broker와 Replication Factor는 사실 1:1 일 때 가장 좋은 가용성을 가집니다.
- 너무 RF가 커도 저장공간 낭비가 됩니다.

## Kafka Topic (ISR, In-sync Replica)

- Sync가 되었다고 판단 가능한 레플리카 그룹
- Partiton의 복제본이 많아지면 가용성이 늘어납니다.
  - 그러면 역으로 Produce 시에도 복제해야할 데이터가 늘어납니다.
> **ISR 그룹**이란 하나의 파티션에 대한 Replica들이 동기화 된 그룹을 의미합니다.
> ISR이 크다라는 의미는 Produce 시에 Partition에 더 많은 복제본을 무조건 생성을 시킵니다.
> -> 역으로 Produce 시 신뢰성은 높아지지만, 지연 시간이 증가합니다. 

## Kafka 설계 시 유의 할 것

- 토픽에 포함된 파티션 갯수
- 토픽에 포함된 파티션의 복제본 갯수
- 토픽의 ISR 그룹에 포함된 파티션 그룹

## Zookeeper

- Kafka Cluster 또한 분산 시스템으로 Zookeeper가 필요하다.
- Cluster 내의 Broker 들의 메시징 Skew 관리가 필요하다 (Skew : 데이터 분산의 정도)
- 어떤 Broker가 Leader Broker인지 선정 필요 **(Controller 선정)**

### Docker-compose 셋팅
```yaml

  zookeeper:
    image: 'arm64v8/zookeeper:3.8'
    networks:
      - fastcampuspay_network
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
      - ZOO_TLS_CLIENT_AUTH=none
      - ZOO_TLS_QUORUM_CLIENT_AUTH=none

  kafka:
    image: 'bitnami/kafka:3.4.0'
    networks:
      - fastcampuspay_network
    ports:
      - '9092:9092'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_LISTENERS=LC://kafka:29092,LX://kafka:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=LC://kafka:29092,LX://${DOCKER_HOST_IP:-localhost}:9092
      - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=LC:PLAINTEXT,LX:PLAINTEXT
      - KAFKA_CFG_INTER_BROKER_LISTENER_NAME=LC
    depends_on:
      - zookeeper

  kafka-ui:
    image: provectuslabs/kafka-ui
    container_name: kafka-ui
    networks:
      - fastcampuspay_network
    ports:
      - "8989:8080"
    restart: always
    depends_on:
      - kafka
      - zookeeper
    environment:
      - KAFKA_CLUSTERS_0_NAME=local
      - KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:29092
      - KAFKA_CLUSTERS_0_ZOOKEEPER=zookeeper:2181
```