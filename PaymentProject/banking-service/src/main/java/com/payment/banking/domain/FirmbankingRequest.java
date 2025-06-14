package com.payment.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmbankingRequest {
    @Getter
    private final String firmbankingRequestId;
    @Getter
    private final String fromBankName;
    @Getter
    private final String toBankName;
    @Getter
    private final String fromBankAccountNumber;
    @Getter
    private final String toBankAccountNumber;
    @Getter
    private final int moneyAccount;
    @Getter
    private final int firmbankingStatus; // 0: 요청, 1: 완료, 2: 실패
    @Getter
    private final UUID uuid;

    public static FirmbankingRequest generateFirmbankingRequest(
            FirmbankingRequest.FirmbankingRequestId firmbankingRequestId,
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAccount moneyAccount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus,
            UUID uuid
            )
    {
        return new FirmbankingRequest(
                firmbankingRequestId.getFirmbankingRequestId(),
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankingAccountNumber(),
                moneyAccount.getMoneyAccount(),
                firmbankingStatus.getFirmbankingStatus(),
                uuid
        );
    }


    @Value
    public static class FirmbankingRequestId {
        public FirmbankingRequestId(String value){
            this.firmbankingRequestId = value;
        }
        String firmbankingRequestId;
    }
    @Value
    public static class FromBankName {
        public FromBankName(String value){
            this.fromBankName = value;
        }
        String fromBankName;
    }
    @Value
    public static class FromBankAccountNumber {
        public FromBankAccountNumber (String value){
            this.fromBankAccountNumber = value;
        }
        String fromBankAccountNumber;
    }
    @Value
    public static class ToBankAccountNumber{
        public ToBankAccountNumber(String value){
            this.toBankingAccountNumber = value;
        }
        String toBankingAccountNumber;
    }
    @Value
    public static class MoneyAccount{
        public MoneyAccount(int value){
            this.moneyAccount = value;
        }
        int moneyAccount;
    }
    @Value
    public static class FirmbankingStatus{
        public FirmbankingStatus(int value){
            this.firmbankingStatus = value;
        }
        int firmbankingStatus;
    }
    @Value
    public static class ToBankName {
        public ToBankName(String value){
            this.toBankName = value;
        }
        String toBankName;
    }
}
