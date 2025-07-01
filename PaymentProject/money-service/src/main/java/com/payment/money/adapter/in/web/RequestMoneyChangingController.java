package com.payment.money.adapter.in.web;

import com.payment.money.application.port.in.IncreaseMoneyChangingCommand;
import com.payment.money.application.port.in.IncreaseMoneyRequestUsecase;
import com.payment.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {
    private final IncreaseMoneyRequestUsecase increaseMoneyRequestUsecase;

    @PostMapping(path = "/money/increase/")
    MoneyChangingResultDetail increaseMoneyChaningRequest(@RequestBody IncreaseMoneyChangingRequest request){
        IncreaseMoneyChangingCommand command = IncreaseMoneyChangingCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUsecase.increaseMoney(command);

        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                        moneyChangingRequest.getMoneyChangingRequestId(),
                moneyChangingRequest.getChangingMoneyAmount(),
                0, // 증액 타입은 0으로 고정
                    1// 성공 상태로 고정
                );
        return resultDetail;
    }


    @PostMapping(path = "/money/increase-async/")
    MoneyChangingResultDetail increaseMoneyChaningRequestAsync(@RequestBody IncreaseMoneyChangingRequest request){
        IncreaseMoneyChangingCommand command = IncreaseMoneyChangingCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUsecase.increaseMoneyAsync(command);

        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                moneyChangingRequest.getChangingMoneyAmount(),
                0, // 증액 타입은 0으로 고정
                1// 성공 상태로 고정
        );
        return resultDetail;
    }

    @PostMapping(path = "/money/decrease/")
    MoneyChangingResultDetail registerMembership(@RequestBody DecreaseMoneyChangingRequest request){

//        DecreaseMoneyChangingCommand command = DecreaseMoneyChangingCommand.builder()
//                .targetMembershipId(request.getTargetMembershipId())
//                .amount(request.getAmount())
//                .build();
//
//        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUsecase.decreaseMoney(command);
//
//        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
//                        moneyChangingRequest.getMoneyChangingRequestId(),
//                        1, // 감액 타입은 1로 고정
//                        0, // 성공 상태로 고정
//                        moneyChangingRequest.getChangingMoneyAmount()
//                );
//        return resultDetail;
        return null;
    }
}
