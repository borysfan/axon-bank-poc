package com.borysfan.topUp;

import com.borysfan.core.Amount;
import com.borysfan.core.PhoneNumber;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class TopUpTelephoneSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopUpTelephone.class);

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient TopUpService topUpService;
    private PhoneNumber phoneNumber;
    private Amount amount;
    private String transactionId;

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TelephoneTopUpRequestedEvent event) {
        LOGGER.info("Starting new saga for telephone top up: {}", event.getTransactionId());
        this.transactionId = event.getTransactionId();
        this.amount = event.getAmount();
        this.phoneNumber = event.getPhoneNumber();
        SagaLifecycle.associateWith("transactionId", event.getTransactionId());
        commandGateway.send(new WithdrawMoneyCommand(event.getAccountId(), event.getTransactionId(), event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        topUpService.topUp(phoneNumber, amount);
        commandGateway.send(new CompleteTopUpTelephoneCommand(transactionId));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TopUpCompletedEvent event) {
        LOGGER.info("TopUp Completed.");
    }
}
