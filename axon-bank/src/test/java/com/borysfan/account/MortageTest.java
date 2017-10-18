package com.borysfan.account;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.api.*;
import com.borysfan.mortage.Mortage;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

public class MortageTest {

    private AggregateTestFixture<Mortage> fixture;
    private final String mortageId = "M1";
    private final AccountId accountId = new AccountId("A1");
    private final Amount amount = new Amount(1000L);

    @Before
    public void before() {
        fixture = new AggregateTestFixture<>(Mortage.class);
    }

    @Test
    public void shouldCreateNewMortage() {
        fixture.givenNoPriorActivity()
                .when(new CreateMortageCommand(mortageId, accountId, amount))
                .expectEvents(new MortageCreatedEvent(mortageId, accountId, amount));
    }

    @Test
    public void shouldSubtractMoneyFromMortage() {
        fixture.given(new MortageCreatedEvent(mortageId, accountId, amount))
                .when(new SubtractMoneyFromMortageCommand(mortageId, new Amount(100L)))
                .expectEvents(new MoneySubtractedFromMortageEvent(mortageId, new Amount(900L)));
    }

    @Test
    public void shouldRequestTranchTranfer(){
        final String trancheId = "1";
        fixture.given(new MortageCreatedEvent(mortageId, accountId, amount), new MoneySubtractedFromMortageEvent(mortageId, new Amount(900L)))
                .when(new TransferTrancheCommand(trancheId, mortageId, new Amount(100L)))
                .expectEvents(new TransferTrancheRequestedEvent(trancheId, mortageId, accountId, new Amount(100L)));
    }


}
