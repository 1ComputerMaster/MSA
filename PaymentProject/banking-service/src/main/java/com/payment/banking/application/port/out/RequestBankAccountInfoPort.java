package com.payment.banking.application.port.out;

import com.payment.banking.adapter.out.external.bank.BankAccount;
import com.payment.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
