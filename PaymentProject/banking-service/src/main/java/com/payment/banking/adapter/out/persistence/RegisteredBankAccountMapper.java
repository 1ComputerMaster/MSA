package com.payment.banking.adapter.out.persistence;

import com.payment.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {
    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity){
        return  RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(registeredBankAccountJpaEntity.getRegisteredBankId() + "")
                , new RegisteredBankAccount.MembershipId(registeredBankAccountJpaEntity.getMembershipId())
                , new RegisteredBankAccount.BankName(registeredBankAccountJpaEntity.getBankName())
                , new RegisteredBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber())
                , new RegisteredBankAccount.LinkedStatusIsValid(registeredBankAccountJpaEntity.isLinkedStatusIsValid())
                );
    }
}
