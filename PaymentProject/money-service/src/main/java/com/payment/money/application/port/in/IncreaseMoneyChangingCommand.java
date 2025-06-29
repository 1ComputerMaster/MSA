package com.payment.money.application.port.in;

import com.payment.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyChangingCommand extends SelfValidating<IncreaseMoneyChangingCommand> {

    @NotNull
    private final String targetMembershipId;
    @NotNull
    private final int amount;

    public IncreaseMoneyChangingCommand(
            String targetMembershipId,
            int amount) {
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;

        this.validateSelf();
    }
}
