package com.payment.banking.application.port.in;

import com.payment.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class FirmbankingRequestCommand extends SelfValidating<FirmbankingRequestCommand> {
    @NotNull
    private String fromBankName;
    @NotNull
    private String fromBankAccountNumber;
    @NotNull
    private String toBankName;
    @NotNull
    private String toBankAccountNumber;
    @NotNull
    private int moneyAmount; // only Won
    @NotNull

    public FirmbankingRequestCommand(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;

        // Validate the command
        this.validateSelf();
    }

    public String getFromBankName() {
        return fromBankName;
    }

    public String getFromBankAccountNumber() {
        return fromBankAccountNumber;
    }

    public String getToBankName() {
        return toBankName;
    }

    public String getToBankAccountNumber() {
        return toBankAccountNumber;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }
}
