package com.payment.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseMoneyChangingRequest {
    //무조건 충전 요청임
    private String targetMembershipId; // 어떤 고객이 증액 또는 감액 요청을 했는지의 맴버 정보
    private int amount;
    
}
