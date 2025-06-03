package com.payment.membership.application.port.in;

import com.payment.membership.domain.Membership;
import common.Usecase;

@Usecase
public interface RegisterMembershipUsecase {
    Membership registerMembership(RegisterMembershipCommand command);
}
