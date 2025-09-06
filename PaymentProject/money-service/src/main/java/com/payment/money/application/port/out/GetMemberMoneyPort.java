package com.payment.money.application.port.out;

import com.payment.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.payment.money.domain.MemberMoney;

public interface GetMemberMoneyPort {
    MemberMoneyJpaEntity getMemberMoney(MemberMoney.MemberMoneyId membershipId);
}
