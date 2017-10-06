package com.borysfan.transaction;

import com.borysfan.core.api.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@Aggregate
public class Transaction {

    @AggregateIdentifier
    private String transactionId;

    private boolean completed;

    @CommandHandler
    public Transaction(RequestTransactionCommand command) {
        apply(new TransactionRequestedEvent(command.getTransactionId(), command.getFromAccountId(), command.getToAccountId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(TransactionRequestedEvent event) {
        this.transactionId = event.getTransactionId();
        this.completed = false;
    }

    @CommandHandler
    public void handle(CompleteTransactionCommand command) {
        apply(new TransactionCompletedEvent(command.getTransactionId()));
    }

    @EventSourcingHandler
    public void on(TransactionCompletedEvent event) {
        this.completed = true;
        markDeleted();
    }

    @CommandHandler
    public void handle(CancelTransactionCommand command) {
        apply(new TransactionCancelledEvent(command.getTransactionId()));
    }
}
