package com.payment.money.adapter.out.persistence;

import com.payment.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingRequestMapper {
    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestJpaEntity entity){
        return MoneyChangingRequest.generateMoneyChangingRequest(
               new MoneyChangingRequest.MoneyChangingRequestId(String.valueOf(entity.getMoneyChangingRequestId())),
                new MoneyChangingRequest.TargetMembershipId(entity.getTargetMembershipId()),
                new MoneyChangingRequest.MoneyChangingType(entity.getMoneyChangingType()),
                new MoneyChangingRequest.ChangingMoneyAmount(entity.getMoneyAmount()),
                new MoneyChangingRequest.MoneyChangingStatus(entity.getChangingMoneyStatus()),
                new MoneyChangingRequest.Uuid(entity.getUuid()),
                new MoneyChangingRequest.CreatedAt(entity.getCreatedAt())
        );
    }
}
