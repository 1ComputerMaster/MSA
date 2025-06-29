package com.payment.money.application.service;

import com.payment.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.payment.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.payment.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.payment.money.application.port.in.IncreaseMoneyChangingCommand;
import com.payment.money.application.port.in.IncreaseMoneyRequestUsecase;
import com.payment.money.application.port.out.IncreaseMoneyPort;
import com.payment.money.domain.MemberMoney;
import com.payment.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUsecase {
    private final MoneyChangingRequestMapper moneyChangingRequestMapper;
    private final IncreaseMoneyPort increaseMoneyPort;
    @Override
    public MoneyChangingRequest increaseMoney(IncreaseMoneyChangingCommand command) {
        
        // 머니의 충전. 증액 과정

        //1. 고객의 정보가 정상인지 확인 (멤버 서비스)
        
        //2. 고객에 연동된 계좌가 있는지, 그리고 그것이 정상적인지 확인, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)
        
        //3. 법인 계좌 상태도 정상인지 확인 (뱅킹)
        
        //4. 증액을 위한 "기록". 요청 상태로  MoneyChangingRequest 생성 (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객 연동 계좌 -> 페이 법인 계좌) (뱅킹)

        // 6. 펌뱅킹이 성공하면, MoneyChangingRequest의 상태를 완료로 변경
        // 성공시의 멤버의 MemberMoney 값 증액이 필요하다.

        // 6-1. 펌뱅킹이 실패하면, MoneyChangingRequest의 상태를 실패로 변경

        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if(!Objects.isNull(memberMoneyJpaEntity)){
            MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity = increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(0), // 증액 타입은 0로 고정
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1), // 성공 상태로 고정
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            );
            return  moneyChangingRequestMapper.mapToDomainEntity(moneyChangingRequestJpaEntity);
        }
        //TODO 실패 시 나중에 RequestEntity를 상태값을 적절히 처리하는 로직 필요
        return null;
    }
}
