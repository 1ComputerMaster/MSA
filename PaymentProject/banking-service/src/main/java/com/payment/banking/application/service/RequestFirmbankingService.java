package com.payment.banking.application.service;

import com.payment.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.payment.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.payment.banking.adapter.out.persistence.RequestFirmbankJpaEntity;
import com.payment.banking.adapter.out.persistence.RequestFirmbankingMapper;
import com.payment.banking.adapter.out.persistence.external.bank.BankAccount;
import com.payment.banking.adapter.out.persistence.external.bank.ExternalFirmbankingRequest;
import com.payment.banking.adapter.out.persistence.external.bank.FirmbankingResult;
import com.payment.banking.adapter.out.persistence.external.bank.GetBankAccountRequest;
import com.payment.banking.application.port.in.FirmbankingRequestCommand;
import com.payment.banking.application.port.in.RegisteredBankAccountCommand;
import com.payment.banking.application.port.in.RegisteredBankAccountUsecase;
import com.payment.banking.application.port.in.RequestFirmbankingUsecase;
import com.payment.banking.application.port.out.RegisterBankAccountPort;
import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.banking.application.port.out.RequestExternalFirmbankingPort;
import com.payment.banking.application.port.out.RequestFirmBankingPort;
import com.payment.banking.domain.FirmbankingRequest;
import com.payment.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class RequestFirmbankingService implements RequestFirmbankingUsecase {
    private final RequestFirmBankingPort requestFirmBankingPort;
    private final RequestFirmbankingMapper requestFirmbankingMapper;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    @Override
    public FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand requestFirmbankingCommand) {
        //Business Logic
        // a -> b 계좌

        //1. 요청에 대해 정보를 먼저 write "요청" 상태로
        RequestFirmbankJpaEntity entity = requestFirmBankingPort.createFirmbanking(
                new FirmbankingRequest.FromBankName(requestFirmbankingCommand.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(requestFirmbankingCommand.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(requestFirmbankingCommand.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(requestFirmbankingCommand.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAccount(requestFirmbankingCommand.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0)
                );
        //2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
            requestFirmbankingCommand.getFromBankName(),
            requestFirmbankingCommand.getFromBankAccountNumber(),
            requestFirmbankingCommand.getToBankName(),
            requestFirmbankingCommand.getToBankAccountNumber()));
        UUID randomUUID = UUID.randomUUID();
        // Transaction ID를 다시 수정 할 때 생성해서 디버깅 할 때 용이하게 한다.
        entity.setUuid(randomUUID.toString());
        //3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest를 업데이트 한다.
        if(result.getResultCode() == 0) { // 성공
            entity.setFirmbankingStatus(1); // 완료 상태로 변경
        } else {
            entity.setFirmbankingStatus(2); // 실패 상태로 변경
        }
        //4. 결과를 리턴 (modify된 엔티티를 리턴함)
        return requestFirmbankingMapper.mapToDomainEntity(
                requestFirmBankingPort.modifyFirmbankingRequest(entity), randomUUID);
    }

}
