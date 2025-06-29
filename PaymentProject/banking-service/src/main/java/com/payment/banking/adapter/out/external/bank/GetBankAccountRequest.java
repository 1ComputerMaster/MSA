package com.payment.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class GetBankAccountRequest {
    private String bankAccountNumber;
    private String bankName;

    public GetBankAccountRequest(String bankAccountNumber, String bankName) {
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }
}
