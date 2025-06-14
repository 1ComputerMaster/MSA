package com.payment.banking.application.port.out;

import com.payment.banking.adapter.out.persistence.external.bank.BankAccount;
import com.payment.banking.adapter.out.persistence.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
