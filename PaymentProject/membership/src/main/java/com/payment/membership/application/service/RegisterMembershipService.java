package com.payment.membership.application.service;

import com.payment.membership.adapter.out.persistence.MambershipMapper;
import com.payment.membership.adapter.out.persistence.MembershipJpaEntity;
import com.payment.membership.application.port.in.RegisterMembershipCommand;
import com.payment.membership.application.port.in.RegisterMembershipUsecase;
import com.payment.membership.application.port.out.RegisterMembershipPort;
import com.payment.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class RegisterMembershipService implements RegisterMembershipUsecase {
    private final RegisterMembershipPort registerMembershipPort;
    private final MambershipMapper mambershipMapper;
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        // command -> DB와 통신

        //biz logic -> DB
        // external System
        // port, adapter
        MembershipJpaEntity entity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipValid(command.isValid()),
                new Membership.MembershipCorp(command.isValid())
         );


        return mambershipMapper.mapToDomainEntity(entity);
    }
}
