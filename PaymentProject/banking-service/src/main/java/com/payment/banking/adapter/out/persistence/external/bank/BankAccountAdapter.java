package com.payment.banking.adapter.out.persistence.external.bank;

import com.payment.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.banking.application.port.out.RequestExternalFirmbankingPort;
import com.payment.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {


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

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {
        // 실제로 외부 은행의 HTTP API를 호출하여 펌뱅킹 요청을 처리하는 로직을 구현해야 합니다.
        // 그 결과를 FrimbankingResult로 파싱 받아야 합니다.
        // 결과가 true 라고 가정합시다.
        FirmbankingResult result = new FirmbankingResult(0); // 0: 성공, 1: 실패
        return result;
    }
}
