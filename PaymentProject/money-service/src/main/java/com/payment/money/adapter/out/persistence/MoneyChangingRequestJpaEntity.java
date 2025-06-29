package com.payment.money.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "moneyChangingRequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingRequestJpaEntity {

    @Id
    @GeneratedValue
    private Long moneyChangingRequestId;
    private String targetMembershipId;
    private int moneyChangingType; // 0: 증액, 1: 감액
    private int moneyAmount;
    private LocalDateTime createdAt;
    private int changingMoneyStatus; // 0: 요청, 1: 완료, 2: 성공, 3: 실패
    private UUID uuid;

    public MoneyChangingRequestJpaEntity(String targetMembershipId, int moneyChangingType, int moneyAmount, LocalDateTime createdAt, int changingMoneyStatus, UUID uuid) {
        this.targetMembershipId = targetMembershipId;
        this.moneyChangingType = moneyChangingType;
        this.moneyAmount = moneyAmount;
        this.createdAt = createdAt;
        this.changingMoneyStatus = changingMoneyStatus;
        this.uuid = uuid;
    }
}
