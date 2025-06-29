package com.payment.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class BankAccount {
    private String bankAccountNumber;
    private String bankName;
    private boolean isValid;


}
