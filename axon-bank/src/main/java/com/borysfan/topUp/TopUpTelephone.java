package com.borysfan.topUp;

import com.borysfan.core.Amount;
import com.borysfan.core.api.CompleteTopUpTelephoneCommand;
import com.borysfan.core.api.RequestTopUpTelephoneCommand;
import com.borysfan.core.api.TelephoneTopUpRequestedEvent;
import com.borysfan.core.api.TopUpCompletedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class TopUpTelephone {

    @AggregateIdentifier
    private String transactionId;
    private Amount amount;
    private boolean completed;

    @CommandHandler
    public void handle(RequestTopUpTelephoneCommand command) {
        apply(new TelephoneTopUpRequestedEvent(command.getTransactionId(), command.getAccountId(), command.getAmount(), command.getPhoneNumber()));
    }

    @EventSourcingHandler
    public void on(TelephoneTopUpRequestedEvent event) {
        this.transactionId = event.getTransactionId();
        this.amount = event.getAmount();
    }

    @CommandHandler
    public void handle(CompleteTopUpTelephoneCommand command) {
        apply(new TopUpCompletedEvent(command.getTransactionId()));
    }

    @EventSourcingHandler
    public void on(TopUpCompletedEvent event) {
        completed = true;
    }

}
