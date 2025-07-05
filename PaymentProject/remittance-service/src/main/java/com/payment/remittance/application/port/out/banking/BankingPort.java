package com.payment.remittance.application.port.out.banking;

public interface BankingPort {

    BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber);

    //법인 계좌 -> 외부 은행 계좌
    boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount);
}
