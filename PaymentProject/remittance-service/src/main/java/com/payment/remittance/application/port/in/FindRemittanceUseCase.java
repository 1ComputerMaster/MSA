package com.payment.remittance.application.port.in;


import com.payment.remittance.domain.RemittanceRequest;

import java.util.List;

public interface FindRemittanceUseCase {
    List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
