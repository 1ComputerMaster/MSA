package com.payment.banking.application.port.in;

import com.payment.banking.domain.RegisteredBankAccount;
import com.payment.common.Usecase;

@Usecase
public interface RegisteredBankAccountUsecase {
    RegisteredBankAccount registerBankAccount(RegisteredBankAccountCommand command);
}
