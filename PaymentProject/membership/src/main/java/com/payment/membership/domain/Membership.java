package com.payment.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {
    @Getter
    private final String membershipId;
    @Getter
    private final String name;
    @Getter
    private final String email;
    @Getter
    private final String address;
    @Getter
    private final boolean isValid;
    @Getter
    private final boolean isCorp;

    // Membership은 오염이 되면 안되는 클래스, 고객 정보는 핵심 도메인
    // 파괴 되는 것을 막음
    // generate Member를 통하지 않고는 맴버십 클래스를 만들 수 없음
    public static Membership generateMember(
            MembershipId membershipId, MembershipName name, MembershipEmail email, MembershipAddress address, MembershipValid isValid, MembershipCorp isCorp
            )
    {
        return new Membership(membershipId.membershipId, name.membershipName, email.membershipEmail, address.membershipAddress, isValid.membershipValid, isCorp.membershipCorp);
    }

    //아래의 정해진 static class를 생성하지 않으면 맴버십을 생성 할 수 없음
    @Value
    public static class MembershipId{
        public MembershipId(String value){
            this.membershipId = value;
        }
        String membershipId;
    }
    @Value
    public static class MembershipName{
        public MembershipName(String value){
            this.membershipName = value;
        }
        String membershipName;
    }
    @Value
    public static class MembershipEmail{
        public MembershipEmail(String value){
            this.membershipEmail = value;
        }
        String membershipEmail;
    }
    @Value
    public static class MembershipAddress{
        public MembershipAddress(String value){
            this.membershipAddress = value;
        }
        String membershipAddress;
    }
    @Value
    public static class MembershipValid{
        public MembershipValid(boolean value){
            this.membershipValid = value;
        }
        boolean membershipValid;
    }
    @Value
    public static class MembershipCorp{
        public MembershipCorp(boolean value){
            this.membershipCorp = value;
        }
        boolean membershipCorp;
    }
}
