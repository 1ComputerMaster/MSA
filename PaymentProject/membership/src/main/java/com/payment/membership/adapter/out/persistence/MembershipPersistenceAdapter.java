package com.payment.membership.adapter.out.persistence;

import com.payment.membership.application.port.out.RegisterMembershipPort;
import com.payment.membership.domain.Membership;
import common.PersistenceAdapter;

@PersistenceAdapter
public class MembershipPersistenceAdapter implements RegisterMembershipPort {

    @Override
    public void createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipValid membershipValid, Membership.MembershipCorp membershipCorp) {

    }
}
