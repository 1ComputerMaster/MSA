package com.payment.membership.adapter.out.persistence;

import com.payment.membership.domain.Membership;
import org.springframework.stereotype.Component;

@Component
public class MambershipMapper {
    public Membership mapToDomainEntity(MembershipJpaEntity membershipJpaEntity){
        return  Membership.generateMember(
                new Membership.MembershipId(String.valueOf(membershipJpaEntity.getMembershipId())),
                new Membership.MembershipName(membershipJpaEntity.getName()),
                new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
                new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
                new Membership.MembershipValid(membershipJpaEntity.isValid()),
                new Membership.MembershipCorp(membershipJpaEntity.isValid())
                );
    }
}
