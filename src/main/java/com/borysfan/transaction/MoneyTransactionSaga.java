package com.borysfan.transaction;

import com.borysfan.core.AccountId;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class MoneyTransactionSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    private AccountId toAccount;

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionRequestedEvent event) {
        this.toAccount = event.getToAccountId();
        commandGateway.send(new WithdrawMoneyCommand(event.getFromAccountId(), event.getTransactionId(), event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        commandGateway.send(new DepositMoneyCommand(toAccount, event.getTransactionId(), event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        commandGateway.send(new CompleteTransactionCommand(event.getTransactionId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionCompletedEvent event) {

    }

}
