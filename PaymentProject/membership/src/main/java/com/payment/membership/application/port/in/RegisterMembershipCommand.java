package com.payment.membership.application.port.in;

import common.SelfValidating;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
    @NotNull
    private final String name;
    @NotNull
    @NotEmpty
    private final String email;
    @NotNull
    @NotEmpty
    private final String address;
    @AssertTrue
    private final boolean isValid;
    private final boolean isCorp;

    public RegisterMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;
        // SelfValidating이 자동으로 위 필드를 선언적으로만 적어주어도 아래의 validateSelf가 validation 한다.
        this.validateSelf();
    }
}
