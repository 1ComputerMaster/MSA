package com.payment.banking.application.port.in;

import com.payment.common.SelfValidating;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisteredBankAccountCommand extends SelfValidating<RegisteredBankAccountCommand> {

    @NotNull
    private final String membershipId;
    @NotNull
    private final String bankName;
    @NotNull
    @NotBlank
    private final String bankAccountNumber;
    @NotNull
    private final boolean linkedStatusIsValid;

    public RegisteredBankAccountCommand(
            String membershipId,
            String bankName,
            String bankAccountNumber,
            boolean linkedStatusIsValid) {
        // SelfValidating이 자동으로 위 필드를 선언적으로만 적어주어도 아래의 validateSelf가 validation 한다.
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkedStatusIsValid = linkedStatusIsValid;

        this.validateSelf();
    }
}
