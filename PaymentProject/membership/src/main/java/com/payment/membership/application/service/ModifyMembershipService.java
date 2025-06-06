package com.payment.membership.application.service;

import com.payment.membership.adapter.out.persistence.MambershipMapper;
import com.payment.membership.adapter.out.persistence.MembershipJpaEntity;
import com.payment.membership.application.port.in.ModifyMembershipCommamd;
import com.payment.membership.application.port.in.ModifyMembershipUsecase;
import com.payment.membership.application.port.out.ModifyMembershipPort;
import com.payment.membership.domain.Membership;
import com.payment.common.Usecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Usecase
@Transactional
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUsecase {
    private final ModifyMembershipPort modifyMembershipPort;
    private final MambershipMapper membershipMapper;
    @Override
    public Membership modifyMembership(ModifyMembershipCommamd commamd) {
        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(commamd.getMembershipId()),
                new Membership.MembershipName(commamd.getName()),
                new Membership.MembershipEmail(commamd.getEmail()),
                new Membership.MembershipAddress(commamd.getAddress()),
                new Membership.MembershipValid(commamd.isValid()),
                new Membership.MembershipCorp(commamd.isCorp())
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
