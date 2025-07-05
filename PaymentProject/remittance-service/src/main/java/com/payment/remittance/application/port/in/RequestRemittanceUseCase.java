package com.payment.remittance.application.port.in;


import com.payment.remittance.domain.RemittanceRequest;

public interface RequestRemittanceUseCase {
    RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
