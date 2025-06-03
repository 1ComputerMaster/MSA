# Membership service

## API 설계

### Query

- 고객정보를(MembershipId) 통한 고객정보의 조회
- find - membership 
  - RequestParams : membershipId
  - Response : Membership (membershipId, name, addr,...)

### Command

- 필요한 고객 정보를 통한 신규 고객 맴버십의 생성
- register - membership
  - RequestParams : Membership
  - Response : Registered Membership with response code (200,400,500)