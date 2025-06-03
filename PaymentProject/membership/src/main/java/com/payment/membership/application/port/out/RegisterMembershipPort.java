package com.payment.membership.application.port.out;

import com.payment.membership.domain.Membership;

public interface RegisterMembershipPort {
    void createMembership(
           Membership.MembershipName membershipName,
           Membership.MembershipEmail membershipEmail,
           Membership.MembershipAddress membershipAddress,
           Membership.MembershipValid membershipValid,
           Membership.MembershipCorp membershipCorp
    );
}
