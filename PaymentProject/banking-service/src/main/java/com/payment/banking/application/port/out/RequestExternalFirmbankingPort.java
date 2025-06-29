package com.payment.banking.application.port.out;

import com.payment.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.payment.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {
    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
