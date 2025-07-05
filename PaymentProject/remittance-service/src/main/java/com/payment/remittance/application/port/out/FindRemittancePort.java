package com.payment.remittance.application.port.out;

import com.payment.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.payment.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
