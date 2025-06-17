# PaymentProject

> **Microservice-based Payment System**  
> Spring Boot, Axon, Kafka, Docker Compose 기반 통합 결제 플랫폼 MVP

---

## 📦 프로젝트 개요

PaymentProject는 MSA(Microservice Architecture)와 이벤트 기반 CQRS 패턴을 적용하여 **회원 관리**, **계좌 관리**, **송금(개발 예정)**, **결제(개발 예정)**, **정산(개발 예정)** 기능을 분리된 서비스로 구현한 결제 시스템 프로토타입입니다.  

각 서비스는 독립적으로 배포·운영되며, Docker Compose 환경에서 손쉽게 로컬 구동이 가능합니다.

---

## 🔍 아키텍처

```text
┌─────────────┐ ⟷ ┌──────────────┐ ⟷ ┌──────────────┐ ⟷ ┌──────────────┐
│ membership  │    │ banking      │    │ money        │    │ remittance   │
│  -service   │ ⟷ │  -service    │ ⟷ │  -service    │ ⟷ │  -service    │
└─────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
        ⟷                                         ⟷
        │                                         │
        ⟷                                         ⟷
┌──────────────┐ ⟷ ┌──────────────┐ ⟷ ┌──────────────┐
│ payment-     │    │ settlement-  │    │ membership   │
│ service      │ ⟷ │ service      │ ⟷ │ -service     │
└──────────────┘    └──────────────┘    └──────────────┘



• 서비스 간 이벤트 전파 및 CQRS: Axon Server (또는 Kafka)  
• 비동기 메시징: Kafka / Zookeeper  
• 데이터 저장소: MySQL (InnoDB)  
• 로컬 개발환경: Docker Compose
```

## 🚀 주요 서비스

| 서비스명                   | 설명              |
| :--------------------- | :-------------- |
| **membership-service** | 사용자(회원) 가입·조회   |
| **banking-service**    | 계좌 생성, 잔액 조회·관리 |
| **money-service**      | 머니 충전·발급 관리     |
| **payment-service**    | 외부 가맹점 결제 처리(개발 예정) | 
| **remittance-service** | 사용자간 송금 처리(개발 예정) |
| **settlement-service** | 정산 내역 집계·조회(개발 예정)|

## 🛠️ 기술 스택
- **Language & Framework**
  - Java 17, Spring Boot 3.5

  - Spring Data JPA, Hibernate 6

  - Axon Framework 4 (CQRS & Event Sourcing)

  - SpringDoc OpenAPI UI

- **Messaging & Database**

  - Kafka 3, Zookeeper 3.8

  - Axon Server 4.6 (Dev Mode)

  - MySQL 8.0 (InnoDB, Partitioning)

- **DevOps & Container**

  - Docker, Docker Compose v3.9

  - Linux Bridge Network

  - WSL 2 (Windows 환경)

- **CI/CD & Build**

  - Gradle 8, Spring Dependency Management

## 📂 프로젝트 구조
```bash
.
├── membership/             # User ↔ Account 연동 · REST API
├── banking/                # 계좌 CRUD · 트랜잭션 관리
├── money/                  # 머니 충전·발행, CQRS 명령 핸들러
├── remittance/             # 송금 흐름 Command / Event
├── settlement/             # 정산 내역 집계 서비스
├── payment/                # 실제 결제 처리 Command / Event
├── common/                 # 공통 유틸·라이브러리
└── docker-compose.yaml     # 로컬 통합 환경 (MySQL / Kafka / Axon / Services)
```

## ⚙️ 로컬 실행
**1. 환경 준비**

Docker Desktop (WSL 2 백엔드)

Java 17, Gradle 8

**2. Docker 이미지 빌드**
```bash
# 프로젝트 루트
./gradlew clean bootJar
```

**3. Docker Compose 기동**

```bash
docker-compose up --build -d
```

**4. 서비스 확인**

- http://localhost:8081/swagger-ui.html (회원·계좌)

- http://localhost:8082/swagger-ui.html (머니·송금)

- http://localhost:8083/swagger-ui.html (결제·정산)

## ✅ 특징 & 배운 점
- **MSA 분할 설계**: 서비스 책임을 명확히 분리하고 독립 배포 가능

- **CQRS & Event Sourcing**: Axon Framework 를 활용해 명령(Command)과 조회(Query) 모델 분리

- **비동기 메시징**: Kafka 를 통한 이벤트 스트리밍, 비동기 확장성 확보

- **Docker 환경 자동화**: 개발 초기부터 Docker Compose 로 통합 환경 구성

- **DevOps 경험**: WSL2, Port Reservation, Docker Healthcheck, Volume 관리

## 📈 향후 개선 과제
**보안 강화**: JWT 기반 인증·인가, HTTPS 적용

**Auto-Scaling**: Kubernetes 클러스터 배포

**Observability**: Prometheus / Grafana 모니터링
