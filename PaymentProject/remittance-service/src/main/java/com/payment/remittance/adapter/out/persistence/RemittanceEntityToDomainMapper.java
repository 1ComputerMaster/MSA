package com.payment.remittance.adapter.out.persistence;

import com.payment.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RemittanceEntityToDomainMapper {
    private RemittanceRequest mapToDomainEntity(RemittanceRequestJpaEntity remittanceRequestJpaEntity) {
        return RemittanceRequest.generateRemittanceRequest(
                new RemittanceRequest.RemittanceRequestId(remittanceRequestJpaEntity.getFromMembershipId()),
                new RemittanceRequest.RemittanceFromMembershipId(remittanceRequestJpaEntity.getFromMembershipId()),
                new RemittanceRequest.ToBankName(remittanceRequestJpaEntity.getToBankName()),
                new RemittanceRequest.ToBankAccountNumber(remittanceRequestJpaEntity.getToBankAccountNumber()),
                new RemittanceRequest.RemittanceType(remittanceRequestJpaEntity.getRemittanceType()),
                new RemittanceRequest.Amount(remittanceRequestJpaEntity.getAmount()),
                new RemittanceRequest.RemittanceStatus(remittanceRequestJpaEntity.getRemittanceStatus())
        );
    }

    public List<RemittanceRequest> mapToDomainEntities(List<RemittanceRequestJpaEntity> remittanceRequestJpaEntities) {
        return remittanceRequestJpaEntities.stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
