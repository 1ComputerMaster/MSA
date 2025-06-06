package com.payment.membership.adapter.out.persistence;

import com.payment.membership.application.port.out.FindMembershipPort;
import com.payment.membership.application.port.out.ModifyMembershipPort;
import com.payment.membership.application.port.out.RegisterMembershipPort;
import com.payment.membership.domain.Membership;
import com.payment.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {
    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipValid membershipValid, Membership.MembershipCorp membershipCorp) {
        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getMembershipName(),
                        membershipAddress.getMembershipAddress(),
                        membershipEmail.getMembershipEmail(),
                        membershipValid.isMembershipValid(),
                        membershipCorp.isMembershipCorp()
                )
        );
    }

    @Override
    public MembershipJpaEntity findByMembershipId(Membership.MembershipId membershipId) {
        return membershipRepository.getById(Long.valueOf(membershipId.getMembershipId()));
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipValid membershipValid, Membership.MembershipCorp membershipCorp) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.valueOf(membershipId.getMembershipId()));
        entity.setCorp(membershipCorp.isMembershipCorp());
        entity.setName(membershipName.getMembershipName());
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setValid(membershipValid.isMembershipValid());
        entity.setEmail(membershipEmail.getMembershipEmail());
        return entity;
    }
}
