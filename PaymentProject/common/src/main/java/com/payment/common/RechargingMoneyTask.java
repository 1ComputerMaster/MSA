package com.payment.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RechargingMoneyTask { // 증액

    private String taskID;
    private String taskName;

    private String membershipID;

    // 필요한 서브테스크 어떠한 타입을 가지는지
    private List<SubTask> subTaskList;

    // 법인 계좌
    private String toBankName;

    // 법인 계좌 번호
    private String toBankAccountNumber;

    private int moneyAmount; // won only
}