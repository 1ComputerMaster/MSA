package com.payment.banking.application.port.out;

import com.payment.banking.adapter.out.persistence.RequestFirmbankJpaEntity;
import com.payment.banking.domain.FirmbankingRequest;

public interface RequestFirmBankingPort {
    RequestFirmbankJpaEntity createFirmbanking(
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAccount moneyAccount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus);

    RequestFirmbankJpaEntity modifyFirmbankingRequest(RequestFirmbankJpaEntity entity);
}
