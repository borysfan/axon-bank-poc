package com.borysfan.mortage;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
public class TrancheTransferSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private String trancheTransferId;
    private String mortageId;
    private Amount amount;
    private AccountId accountId;

    @StartSaga
    @SagaEventHandler(associationProperty = "trancheTransferId")
    public void on(TransferTrancheRequestedEvent event) {
        SagaLifecycle.associateWith("mortageId", event.getMortageId());
        SagaLifecycle.associateWith("accountId", event.getMortageId());
        this.trancheTransferId = event.getTrancheTransferId();
        this.mortageId = event.getMortageId();
        this.amount = event.getAmount();
        this.accountId = event.getAccountId();
        commandGateway.send(new SubtractMoneyFromMortageCommand(mortageId, event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "mortageId")
    public void on(MoneySubtractedFromMortageEvent event) {
        commandGateway.send(new DepositMoneyCommand(accountId, UUID.randomUUID().toString(), amount));
    }

    @SagaEventHandler(associationProperty = "accountId")
    @EndSaga
    public void on(MoneyDepositedEvent event) {
        //do nothing
    }

}
