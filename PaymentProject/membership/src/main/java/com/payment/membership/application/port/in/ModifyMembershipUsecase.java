package com.payment.membership.application.port.in;

import com.payment.membership.domain.Membership;

public interface ModifyMembershipUsecase {
    Membership modifyMembership(ModifyMembershipCommamd commamd);
}
