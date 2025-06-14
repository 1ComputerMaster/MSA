package com.payment.banking.adapter.out.persistence;

import com.payment.banking.domain.FirmbankingRequest;
import com.payment.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RequestFirmbankingMapper {
    public FirmbankingRequest mapToDomainEntity(RequestFirmbankJpaEntity requestFirmbankJpaEntity, UUID uuid) {
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmbankingRequestId(
                        String.valueOf(requestFirmbankJpaEntity.getRequestedFirmBankId())
                ),
                new FirmbankingRequest.FromBankName(
                        requestFirmbankJpaEntity.getFromBankName()
                ),
                new FirmbankingRequest.FromBankAccountNumber(
                        requestFirmbankJpaEntity.getFromBankAccountNumber()
                ),
                new FirmbankingRequest.ToBankName(
                        requestFirmbankJpaEntity.getToBankName()
                ),
                new FirmbankingRequest.ToBankAccountNumber(
                        requestFirmbankJpaEntity.getToBankAccountNumber()
                ),
                new FirmbankingRequest.MoneyAccount(
                        requestFirmbankJpaEntity.getMoneyAmount()
                ),
                new FirmbankingRequest.FirmbankingStatus(
                        requestFirmbankJpaEntity.getFirmbankingStatus()
                ),
                uuid
        );
    }
}
