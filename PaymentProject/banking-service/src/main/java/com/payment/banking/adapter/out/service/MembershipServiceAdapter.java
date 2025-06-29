package com.payment.banking.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.banking.application.port.out.GetMembershipPort;
import com.payment.banking.application.port.out.Membership;
import com.payment.banking.application.port.out.MembershipStatus;
import com.payment.common.CommonHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class MembershipServiceAdapter implements GetMembershipPort {
    private final CommonHttpClient commonHttpClient;
    private final String membershipServiceUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {
        //TODO HTTP CLIENT 구현
        String url = membershipServiceUrl + "/membership/" + membershipId;
        try {
            String jsonBody = commonHttpClient.sendGetRequest(url).body();
            Membership membership = objectMapper.readValue(jsonBody, Membership.class);
            if(membership != null && membership.isValid()) {
                return new MembershipStatus(membership.getMembershipId(), true);
            }else {
                return new MembershipStatus(membershipId, false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
