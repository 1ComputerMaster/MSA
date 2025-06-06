package com.payment.membership;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.membership.adapter.in.web.ModifyMembershipRequest;
import com.payment.membership.adapter.in.web.RegisterMembershipRequest;
import com.payment.membership.domain.Membership;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ModifyMembershipControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() throws Exception {
        RegisterMembershipRequest request = new RegisterMembershipRequest(
                "name",
                "address",
                "email",
                false
        );

        //아래 테스트 코드를 통해서 코드 오류를 찾음
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/membership/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRegisterMembership() throws Exception{
        //아래 인풋을 가지고
        ModifyMembershipRequest request = new ModifyMembershipRequest(
                "1",
                "changedName",
                "address",
                "email",
                false,
                true
        );
        //post 요청을 보내면 성공

        Membership membership = Membership.generateMember(
                new Membership.MembershipId("1"),
                new Membership.MembershipName("changedName"),
                new Membership.MembershipEmail("email"),
                new Membership.MembershipAddress("address"),
                new Membership.MembershipValid(true),
                new Membership.MembershipCorp(false)

        );
        //아래 테스트 코드를 통해서 코드 오류를 찾음
        mockMvc.perform(
                MockMvcRequestBuilders.post("/membership/modify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membership)));
    }
}
