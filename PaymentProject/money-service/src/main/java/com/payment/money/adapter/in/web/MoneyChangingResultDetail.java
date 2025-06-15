package com.payment.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId; // 머니 변액 요청 ID
    private int amount;
    //증액과 감액을 표현하는 데이터 필요
    private int moneyChangingResultType; // 0: 증액, 1: 감액
    private int moneyChangingResultStatus; // 0: 요청, 1: 완료, 2: 성공, 3: 실패


}
