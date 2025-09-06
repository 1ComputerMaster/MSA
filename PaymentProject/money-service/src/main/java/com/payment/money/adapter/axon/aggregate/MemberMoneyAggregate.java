package com.payment.money.adapter.axon.aggregate;

import com.payment.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.payment.money.adapter.axon.event.MemberMoneyCreatedEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

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

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        log.info("MemberMoneyCreatedEvent received: {}", event);
        this.id = UUID.randomUUID().toString();
        this.membershipId = Long.valueOf(event.getMembershipId());
        this.balance = 0;
    }

}
