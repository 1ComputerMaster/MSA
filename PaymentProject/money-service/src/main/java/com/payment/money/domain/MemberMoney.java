package com.payment.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {
    @Getter
    private final String memberMoneyId;
    @Getter
    private final String membershipId;
    @Getter
    private final int balance; //잔액

    public static MemberMoney generateMemberMoney(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            Balance balance
            ) {
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                balance.getBalance());
    }
    @Value
    public static class MemberMoneyId {
        private final String memberMoneyId;
        public MemberMoneyId(String value) {
            this.memberMoneyId = value;
        }
    }

    @Value
    public static class MembershipId {
        private final String membershipId;
        public MembershipId(String value) {
            this.membershipId = value;
        }
    }

    @Value
    public static class Balance {
        private final int balance;
        public Balance(int value) {
            this.balance = value;
        }
    }

}
