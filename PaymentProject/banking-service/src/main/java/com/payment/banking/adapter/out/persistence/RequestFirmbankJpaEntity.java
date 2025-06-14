package com.payment.banking.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "requestFirmbank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFirmbankJpaEntity {


    @Id
    @GeneratedValue
    private Long requestedFirmBankId;
    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount; // only Won
    private int firmbankingStatus; // 0: 요청, 1: 완료, 2: 실패
    private String uuid;


    public RequestFirmbankJpaEntity(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, int firmbankingStatus, UUID uuid) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmbankingStatus = firmbankingStatus;
        this.uuid = UUID.randomUUID().toString(); // Generate a new UUID for each entity
    }

    @Override
    public String toString() {
        return "RequestFirmbankJpaEntity{" +
                "requestedFirmBankId=" + requestedFirmBankId +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", firmbankingStatus=" + firmbankingStatus +
                '}';
    }
}
