package com.payment.remittance.application.port.out.money;

import lombok.Data;

//  송금 서비스에서만 필요한 최소한의 머니의 정보
@Data
public class MoneyInfo {

    private String membershipId;
    private int balance;
}
