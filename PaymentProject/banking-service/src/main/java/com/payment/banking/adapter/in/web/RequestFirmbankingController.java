package com.payment.banking.adapter.in.web;

import com.payment.banking.application.port.in.FirmbankingRequestCommand;
import com.payment.banking.application.port.in.RequestFirmbankingUsecase;
import com.payment.banking.domain.FirmbankingRequest;
import com.payment.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {
    private final RequestFirmbankingUsecase requestFirmbankingUsecase;

    @PostMapping(path = "/banking/firmbanking/register")
    FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request){
        //Request

        //Usecase ~~ Request를 바로 주는 것이 아닌 request -> command로 추상화 시켜서 Usecase와 직접 붙입니다.
        // 그 이유는 앞의 Request의 영향을 받아서 Usecase 까지 영향 받기 싫으니깐

        //Usecase는 Command를 받아야 합니다.

        FirmbankingRequestCommand command = FirmbankingRequestCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankAccountNumber())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();
        FirmbankingRequest firmbankingRequest = requestFirmbankingUsecase.requestFirmbanking(command);
        if(firmbankingRequest == null){
            return null; // TODO: 예외처리
        }
        // Code로써 Request의 변경에 따라서 대응 할 수 잇다.
        return firmbankingRequest;
    }
}
