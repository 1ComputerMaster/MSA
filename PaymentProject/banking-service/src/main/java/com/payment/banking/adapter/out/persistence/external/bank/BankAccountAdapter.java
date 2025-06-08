package com.payment.banking.adapter.out.persistence.external.bank;

import com.payment.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {


    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // Simulate an external system call to get bank account information
        // In a real application, this would involve making an HTTP request or similar
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountNumber(request.getBankAccountNumber());
        bankAccount.setBankName(request.getBankName());
        bankAccount.setValid(true); // Assume the account is valid for this example

        return bankAccount;
    }
}
