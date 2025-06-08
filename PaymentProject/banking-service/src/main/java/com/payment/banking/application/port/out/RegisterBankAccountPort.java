package com.payment.banking.application.port.out;

import com.payment.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.payment.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort {
    RegisteredBankAccountJpaEntity createRegisterBankAccount(
              RegisteredBankAccount.MembershipId membershipId,
                RegisteredBankAccount.BankName bankName,
                RegisteredBankAccount.BankAccountNumber bankAccountNumber,
                RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid
    );
}
