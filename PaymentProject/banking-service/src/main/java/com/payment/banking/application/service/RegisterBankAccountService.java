package com.payment.banking.application.service;

import com.payment.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.payment.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.payment.banking.adapter.out.persistence.external.bank.BankAccount;
import com.payment.banking.adapter.out.persistence.external.bank.GetBankAccountRequest;
import com.payment.banking.application.port.in.RegisteredBankAccountCommand;
import com.payment.banking.application.port.in.RegisteredBankAccountUsecase;
import com.payment.banking.application.port.out.RegisterBankAccountPort;
import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class RegisterBankAccountService implements RegisteredBankAccountUsecase {
    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    @Override
    public RegisteredBankAccount registerBankAccount(RegisteredBankAccountCommand command) {
        //Business Logic
        // 1. 외부 실제 은행에 등록된 계좌인지 확인하다.
        // 외부의 은행에 이 계좌가 정상인지 확인을 해야 한다.
        // Business Logic to External System
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(
                new GetBankAccountRequest(
                        command.getBankName(),
                        command.getBankAccountNumber()
                )
        );
        boolean accountIsValid = accountInfo.isValid();
        // 2. 등록 가능한 계좌라면 등록한다. 성공시 등록 정보 리턴
        // 2-1. 등록된 계좌가 아니라면, 에러를 리턴 한다.

        if(accountIsValid){
            RegisteredBankAccountJpaEntity entity = registerBankAccountPort.createRegisterBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
            );
            return registeredBankAccountMapper.mapToDomainEntity(entity);

        }else{
            return null;
        }
    }
}
