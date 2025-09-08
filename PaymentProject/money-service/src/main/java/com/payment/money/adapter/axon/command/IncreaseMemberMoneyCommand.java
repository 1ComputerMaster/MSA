package com.payment.money.adapter.axon.command;

import com.payment.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<IncreaseMemberMoneyCommand> {

    @NotNull
    @TargetAggregateIdentifier //Axon Framework가 이 필드를 사용하여 명령을 올바른 애그리거트 인스턴스로 라우팅
    private String aggregateIdentifier;
    @NotNull
    private final String membershipId;
    @NotNull
    private final int amount;

}
