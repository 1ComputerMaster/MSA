package com.payment.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataFirmbankingRequestRepository extends JpaRepository<RequestFirmbankJpaEntity, Long> {
    
}
