package com.borysfan.account;


import com.borysfan.core.*;
import com.borysfan.core.api.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

    private AggregateTestFixture<Account> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Account.class);
        fixture.registerAnnotatedCommandHandler(new AccountCommandHandler(fixture.getRepository()));
    }

    @Test
    public void shouldCreateNewAccount() {
        //given
        AccountId accountId = new AccountId("1234");
        OverdraftLimit overdraftLimit = new OverdraftLimit(new BigDecimal(1000));
        //when
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand(accountId, overdraftLimit))
                .expectEvents(new AccountCreatedEvent(accountId, overdraftLimit));
    }

    @Test
    public void shouldBeAbleToWithdrawnMoney() {
        //given
        AccountId accountId = new AccountId("1234");
        OverdraftLimit overdraftLimit = new OverdraftLimit(new BigDecimal(1000));
        Amount amount = new Amount(new BigDecimal(200));
        Balance balance = new Balance(new BigDecimal(-200));
        //when
        fixture.given(new AccountCreatedEvent(accountId, overdraftLimit))
                .when(new WithdrawMoneyCommand(accountId, "t1", amount))
                .expectEvents(new MoneyWithdrawnEvent(accountId, "t1", amount, balance));
    }

    @Test
    public void shouldNotAllowedToWithdrawnToLargeAmountOfMoney() {
        //given
        AccountId accountId = new AccountId("1234");
        OverdraftLimit overdraftLimit = new OverdraftLimit(new BigDecimal(1000));
        Amount amount = new Amount(new BigDecimal(1001));

        //when
        fixture.given(new AccountCreatedEvent(accountId, overdraftLimit))
                .when(new WithdrawMoneyCommand(accountId, "t1", amount))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }

    @Test
    public void shouldWithdrawTwice() {
        //given
        AccountId accountId = new AccountId("1234");
        OverdraftLimit overdraftLimit = new OverdraftLimit(new BigDecimal(1000));

        //when
        fixture.given(new AccountCreatedEvent(accountId, overdraftLimit), new MoneyWithdrawnEvent(accountId, "t1", new Amount(999L), new Balance(-999L)))
                .when(new WithdrawMoneyCommand(accountId, "t1", new Amount(2L)))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }

    @Test
    public void shouldDepositMoney() {
        //given
        AccountId accountId = new AccountId("1234");
        OverdraftLimit overdraftLimit = new OverdraftLimit(new BigDecimal(1000));

        //when
        fixture.given(new AccountCreatedEvent(accountId, overdraftLimit))
                .when(new DepositMoneyCommand(accountId, "t1", new Amount(100L)))
                .expectEvents(new MoneyDepositedEvent(accountId, "t1", new Amount(100L), new Balance(100L)));
    }

}