package com.borysfan.transaction;

import com.borysfan.Host;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@Aggregate
public class Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    @AggregateIdentifier
    private String transactionId;

    private boolean completed;

    @CommandHandler
    public Transaction(RequestTransactionCommand command) {
        LOGGER.info("RequestTransactionCommand {}", Host.getName());
        apply(new TransactionRequestedEvent(command.getTransactionId(), command.getFromAccountId(), command.getToAccountId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(TransactionRequestedEvent event) {
        LOGGER.info("TransactionRequestedEvent {}", Host.getName());
        this.transactionId = event.getTransactionId();
        this.completed = false;
    }

    @CommandHandler
    public void handle(CompleteTransactionCommand command) {
        LOGGER.info("CompleteTransactionCommand {}", Host.getName());
        apply(new TransactionCompletedEvent(command.getTransactionId()));
    }

    @EventSourcingHandler
    public void on(TransactionCompletedEvent event) {
        LOGGER.info("TransactionCompletedEvent {}", Host.getName());
        this.completed = true;
        markDeleted();
    }

    @CommandHandler
    public void handle(CancelTransactionCommand command) {
        LOGGER.info("CancelTransactionCommand {}", Host.getName());
        apply(new TransactionCancelledEvent(command.getTransactionId()));
    }
}
