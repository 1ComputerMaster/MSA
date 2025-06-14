package com.payment.banking.adapter.out.persistence.external.bank;

import lombok.Data;

@Data
public class BankAccount {
    private String bankAccountNumber;
    private String bankName;
    private boolean isValid;


}
