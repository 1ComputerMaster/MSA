package com.payment.banking.application.port.out;

public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);
}
