package com.payment.banking.adapter.out.persistence;

import com.payment.banking.application.port.out.RegisterBankAccountPort;
import com.payment.banking.domain.RegisteredBankAccount;
import com.payment.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort {
    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;


    @Override
    public RegisteredBankAccountJpaEntity createRegisterBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid) {
        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );
    }
}
