package com.payment.money.application.port.out;

import com.payment.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.payment.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.payment.money.domain.MemberMoney;
import com.payment.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {
    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId
            , MoneyChangingRequest.MoneyChangingType moneyChangingType
            , MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount
            , MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus
            , MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount
    );
}
