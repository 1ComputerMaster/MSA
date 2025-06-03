package com.payment.membership.adapter.in.web;

import com.payment.membership.application.port.in.FindMembershipCommand;
import com.payment.membership.application.port.in.FindMembershipUsecase;
import com.payment.membership.application.port.in.RegisterMembershipCommand;
import com.payment.membership.application.port.in.RegisterMembershipUsecase;
import com.payment.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {
    private final FindMembershipUsecase findMembershipUsecase;

    @GetMapping(path = "/membership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId){
        //Request

        //Usecase ~~ Request를 바로 주는 것이 아닌 request -> command로 추상화 시켜서 Usecase와 직접 붙입니다.
        // 그 이유는 앞의 Request의 영향을 받아서 Usecase 까지 영향 받기 싫으니깐

        //Usecase는 Command를 받아야 합니다.
        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        // Code로써 Request의 변경에 따라서 대응 할 수 잇다.
        return ResponseEntity.ok(findMembershipUsecase.findMembership(command));
    }
}
