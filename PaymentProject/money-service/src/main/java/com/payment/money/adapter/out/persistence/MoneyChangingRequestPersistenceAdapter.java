package com.payment.money.adapter.out.persistence;

import com.payment.common.PersistenceAdapter;
import com.payment.money.application.port.in.CreateMemberMoneyPort;
import com.payment.money.application.port.out.GetMemberMoneyPort;
import com.payment.money.application.port.out.IncreaseMoneyPort;
import com.payment.money.domain.MemberMoney;
import com.payment.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, CreateMemberMoneyPort, GetMemberMoneyPort {
    private final SpringDataMoneyChangingRequestRepository increaseMoneyRepository;
    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.MoneyChangingType moneyChangingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus, MoneyChangingRequest.Uuid uuid) {
        return increaseMoneyRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        moneyChangingType.getChangingType(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        LocalDateTime.now(),
                        moneyChangingStatus.getChangingMoneyStatus(),
                        UUID.randomUUID()
                )
        );
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId membershipId,
                                              int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try{
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        }catch (Exception e){
            entity = new MemberMoneyJpaEntity(
                    Long.valueOf(membershipId.getMembershipId()),
                    increaseMoneyAmount,
                    ""
            );
            return memberMoneyRepository.save(entity);
        }
    }

    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.valueOf(memberId.getMembershipId()),
                0,
                aggregateIdentifier.getAggregateIdentifier()
        );
        memberMoneyRepository.save(entity);
    }

    @Override
    public MemberMoneyJpaEntity getMemberMoney(MemberMoney.MemberMoneyId membershipId) {
        return null;
    }
}
