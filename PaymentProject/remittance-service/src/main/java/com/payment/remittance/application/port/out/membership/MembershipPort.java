package com.payment.remittance.application.port.out.membership;

public interface MembershipPort {

    MembershipStatus getMembershipStatus(String membershipId);
}
