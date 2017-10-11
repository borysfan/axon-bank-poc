package com.borysfan.transaction;

import com.borysfan.Host;
import com.borysfan.core.AccountId;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class MoneyTransactionSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyTransactionSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;
    private AccountId toAccount;

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionRequestedEvent event) {
        LOGGER.info("TransactionRequestedEvent {}", Host.getName());
        this.toAccount = event.getToAccountId();
        commandGateway.send(new WithdrawMoneyCommand(event.getFromAccountId(), event.getTransactionId(), event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        LOGGER.info("MoneyWithdrawnEvent {}", Host.getName());
        commandGateway.send(new DepositMoneyCommand(toAccount, event.getTransactionId(), event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        LOGGER.info("MoneyDepositedEvent {}", Host.getName());
        commandGateway.send(new CompleteTransactionCommand(event.getTransactionId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionCompletedEvent event) {
        LOGGER.info("TransactionCompletedEvent {}", Host.getName());
    }

}
