package com.payment.money.adapter.axon.aggregate;

import com.payment.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.payment.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.payment.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.payment.money.adapter.axon.event.MemberMoneyCreatedEvent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


//모든 변경은 아래의 Aggregate를 통해서 바꾸어야 합니다.
@Aggregate()
@Data
@Slf4j
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        log.info("MemberMoneyCreatedCommand received: {}", command);

        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
    }
    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command){
        log.info("IncreaseMemberMoneyCommand received: {}", command);
        id = command.getAggregateIdentifier();

         apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        log.info("MemberMoneyCreatedEvent received: {}", event);
        this.id = UUID.randomUUID().toString();
        this.membershipId = Long.valueOf(event.getMembershipId());
        this.balance = 0;
    }
    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        log.info("MemberMoneyCreatedEvent received: {}", event);
        this.id = event.getAggregateIdentifier();
        this.membershipId = Long.valueOf(event.getTargetMembershipId());
        this.balance = event.getAmount();
    }

}
