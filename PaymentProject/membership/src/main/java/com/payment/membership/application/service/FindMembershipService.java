package com.payment.membership.application.service;

import com.payment.membership.adapter.out.persistence.MambershipMapper;
import com.payment.membership.adapter.out.persistence.MembershipJpaEntity;
import com.payment.membership.application.port.in.FindMembershipCommand;
import com.payment.membership.application.port.in.FindMembershipUsecase;
import com.payment.membership.application.port.out.FindMembershipPort;
import com.payment.membership.domain.Membership;
import com.payment.common.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Usecase
@Transactional
public class FindMembershipService implements FindMembershipUsecase {
    private final FindMembershipPort findMembershipPort;
    private final MambershipMapper mambershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {

        MembershipJpaEntity entity = findMembershipPort.findByMembershipId(new Membership.MembershipId(command.getMembershipId()));

        return mambershipMapper.mapToDomainEntity(entity);
    }
}
