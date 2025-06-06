package com.payment.membership.application.port.in;

import com.payment.membership.domain.Membership;
import com.payment.common.Usecase;

@Usecase
public interface RegisterMembershipUsecase {
    Membership registerMembership(RegisterMembershipCommand command);
}
