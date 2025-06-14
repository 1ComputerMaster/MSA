package com.payment.banking.adapter.out.persistence;

import com.payment.banking.application.port.out.RegisterBankAccountPort;
import com.payment.banking.application.port.out.RequestFirmBankingPort;
import com.payment.banking.domain.FirmbankingRequest;
import com.payment.banking.domain.RegisteredBankAccount;
import com.payment.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmBankingPort {
    private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;


    @Override
    public RequestFirmbankJpaEntity createFirmbanking(
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAccount moneyAccount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus) {
        return firmbankingRequestRepository.save(
                new RequestFirmbankJpaEntity(
                        fromBankAccountNumber.getFromBankAccountNumber(),
                        fromBankName.getFromBankName(),
                        toBankName.getToBankName(),
                        toBankAccountNumber.getToBankingAccountNumber(),
                        moneyAccount.getMoneyAccount(),
                        firmbankingStatus.getFirmbankingStatus(),
                        UUID.randomUUID() // 멱등성을 지키기 위해서 처음 만들 때는 생성함
                )
        );
    }

    @Override
    public RequestFirmbankJpaEntity modifyFirmbankingRequest(RequestFirmbankJpaEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }
}
