package com.borysfan.account;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.Balance;
import com.borysfan.core.api.*;
import com.borysfan.transaction.MoneyTransactionSaga;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

    private SagaTestFixture fixture;

    @Before
    public void setUp() {
        fixture = new SagaTestFixture<>(MoneyTransactionSaga.class);
    }

    @Test
    public void shouldStartTransaction() {
        fixture.givenNoPriorActivity()
                .whenPublishingA(new TransactionRequestedEvent("t1", new AccountId("123"), new AccountId("321"), new Amount(100L)))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new WithdrawMoneyCommand(new AccountId("123"), "t1", new Amount(100L)));
    }

    @Test
    public void shouldDepositMoney() {
        fixture.givenAPublished(new TransactionRequestedEvent("t1", new AccountId("123"), new AccountId("321"), new Amount(100L)))
                .whenPublishingA(new MoneyWithdrawnEvent(new AccountId("123"), "t1", new Amount(100L), new Balance(100L)))
                .expectDispatchedCommands(new DepositMoneyCommand(new AccountId("321"), "t1", new Amount(100L)));
    }

    @Test
    public void shouldComplete() throws Exception {
        fixture.givenAPublished(new TransactionRequestedEvent("t1", new AccountId("123"), new AccountId("321"), new Amount(100L)))
                .andThenAPublished(new MoneyWithdrawnEvent(new AccountId("123"), "t1", new Amount(100L), new Balance(100L)))
                .whenPublishingA(new MoneyDepositedEvent(new AccountId("321"), "t1", new Amount(100L), new Balance(400L)))
                .expectDispatchedCommands(new CompleteTransactionCommand("t1"));
    }

    @Test
    public void shouldStopSaga() throws Exception {
        fixture.givenAPublished(new TransactionRequestedEvent("t1", new AccountId("123"), new AccountId("321"), new Amount(100L)))
                .andThenAPublished(new MoneyWithdrawnEvent(new AccountId("123"), "t1", new Amount(100L), new Balance(100L)))
                .andThenAPublished(new MoneyDepositedEvent(new AccountId("321"), "t1", new Amount(100L), new Balance(400L)))
                .whenPublishingA(new TransactionCompletedEvent("t1"))
                .expectActiveSagas(0)
                .expectNoDispatchedCommands();
    }
}
