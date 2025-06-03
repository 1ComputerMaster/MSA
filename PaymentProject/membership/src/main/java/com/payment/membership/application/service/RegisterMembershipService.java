package com.payment.membership.application.service;

import com.payment.membership.application.port.in.RegisterMembershipCommand;
import com.payment.membership.application.port.in.RegisterMembershipUsecase;
import com.payment.membership.domain.Membership;

public class RegisterMembershipService implements RegisterMembershipUsecase {
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        // command -> DB와 통신


        //biz logic -> DB

        // external System
        //port, adapter

        return null;
    }
}
