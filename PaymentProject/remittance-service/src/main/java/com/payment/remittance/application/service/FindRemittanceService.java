package com.payment.remittance.application.service;

import com.payment.common.Usecase;
import com.payment.remittance.adapter.out.persistence.RemittanceEntityToDomainMapper;
import com.payment.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.payment.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.payment.remittance.application.port.in.FindRemittanceCommand;
import com.payment.remittance.application.port.in.FindRemittanceUseCase;
import com.payment.remittance.application.port.out.FindRemittancePort;
import com.payment.remittance.domain.RemittanceRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Usecase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceEntityToDomainMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        return mapper.mapToDomainEntities(
                findRemittancePort.findRemittanceHistory(command)
        );
    }
}
