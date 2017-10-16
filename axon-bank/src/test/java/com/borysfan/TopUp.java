package com.borysfan;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.Balance;
import com.borysfan.core.PhoneNumber;
import com.borysfan.core.api.*;
import com.borysfan.topUp.TopUpService;
import com.borysfan.topUp.TopUpTelephoneSaga;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TopUp {

    private SagaTestFixture fixture;
    private String transactionId;
    private AccountId accountId;
    private Amount amount;
    private PhoneNumber phoneNumber;

    @Before
    public void setUp() {
        fixture = new SagaTestFixture<>(TopUpTelephoneSaga.class);
        fixture.registerResource(new TopUpService());
        transactionId = UUID.randomUUID().toString();
        accountId = new AccountId("123");
        amount = new Amount(5L);
        phoneNumber = new PhoneNumber("123-456-789");
    }

    @Test
    public void shouldStartTopUpOperation() {
        fixture.givenNoPriorActivity()
            .whenPublishingA(new TelephoneTopUpRequestedEvent(transactionId, accountId, amount, phoneNumber))
            .expectActiveSagas(1)
            .expectDispatchedCommands(new WithdrawMoneyCommand(accountId, transactionId, amount));
    }

    @Test
    public void shouldWithdrawnMoneyFromAccount() {
        fixture.givenAPublished(new TelephoneTopUpRequestedEvent(transactionId, accountId, amount, phoneNumber))
                .whenPublishingA(new MoneyWithdrawnEvent(accountId, transactionId, amount, new Balance(100L)))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new CompleteTopUpTelephoneCommand(transactionId));
    }

    @Test
    public void shouldTopUpTelephoneAccount() throws Exception {
        fixture.givenAPublished(new TelephoneTopUpRequestedEvent(transactionId, accountId, amount, phoneNumber))
                .andThenAPublished(new MoneyWithdrawnEvent(accountId, transactionId, amount, new Balance(100L)))
                .whenPublishingA(new TopUpCompletedEvent(transactionId))
                .expectActiveSagas(0);
    }
}
