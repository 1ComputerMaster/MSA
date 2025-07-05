package com.payment.remittance.application.port.out;

import com.payment.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.payment.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);

    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}
