package com.payment.remittance.application.service;

import com.payment.common.Usecase;
import com.payment.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.payment.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.payment.remittance.application.port.in.RequestRemittanceCommand;
import com.payment.remittance.application.port.in.RequestRemittanceUseCase;
import com.payment.remittance.application.port.out.RequestRemittancePort;
import com.payment.remittance.application.port.out.banking.BankingPort;
import com.payment.remittance.application.port.out.membership.MembershipPort;
import com.payment.remittance.application.port.out.membership.MembershipStatus;
import com.payment.remittance.application.port.out.money.MoneyInfo;
import com.payment.remittance.application.port.out.money.MoneyPort;
import com.payment.remittance.domain.RemittanceRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Usecase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final RemittanceRequestMapper mapper;
    private final MembershipPort membershipPort;
    private final MoneyPort moneyPort;
    private final BankingPort bankingPort;
    
    //TODO 실제  IPC 통신부 상세 Adapter 패키지 내부 구현 필요함
    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)
        RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(command);

        // 1. from 멤버십 상태 확인 (membership Svc)
        MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getFromMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }

        // 2. 잔액 존재하는지 확인 (money svc)
        MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

        // 2-1. 잔액이 충분치 않은 경우. -> 충전이 필요한 경우
        if (moneyInfo.getBalance() < command.getAmount()) {
            // 만원 단위로 올림하는 Math 함수
            // 뱅킹 서비스 특성상 만원 단위로만 충전이 가능
            int rechargeAmount = (int) Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000;

            // 2-1. 잔액이 충분하지 않다면, 충전 요청 (money svc)
            boolean moneyResult = moneyPort.requestMoneyRecharging(command.getFromMembershipId(), rechargeAmount);
            if (!moneyResult) {
                return null;
            }
        }

        // 3. 송금 타입 (고객/은행)
        if (command.getRemittanceType() == 0) {
            // 3: 내부 고객 송금
            // 3-1. 내부 고객일 경우
            // from 고객 머니 감액, to 고객 머니 증액 (money svc)
            boolean remittanceResultFrom;
            boolean remittanceResultTo;
            remittanceResultFrom = moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());
            remittanceResultTo = moneyPort.requestMoneyIncrease(command.getToMembershipId(), command.getAmount());
            if (!remittanceResultFrom || !remittanceResultTo) {
                return null;
            }

        } else if (command.getRemittanceType() == 1) {
            // 3: 외부 은행 계좌 송금
            // 3-1. 외부 은행 계좌
            // 외부 은행 계좌가 적절한지 확인 (banking svc)
            // 법인계좌 -> 외부 은행 계좌 펌뱅킹 요청 (banking svc)
            boolean remittanceResult = bankingPort.requestFirmbanking(command.getToBankName(), command.getToBankAccountNumber(), command.getAmount());
            if (!remittanceResult) {
                return null;
            }
        }

        // 4. 송금 요청 상태를 성공으로 기록 (persistence layer) -> 송금이 완료된 기록임
        entity.setRemittanceStatus("success");
        boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);
        if (result) {
            return mapper.mapToDomainEntity(entity);
        }
        return null;
    }
}
