package com.borysfan.account;

import com.borysfan.core.AccountId;
import com.borysfan.core.Balance;
import com.borysfan.core.OverdraftLimit;
import com.borysfan.core.OverdraftLimitExceededException;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import java.math.BigDecimal;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class Account {

    @AggregateIdentifier
    private AccountId accountId;

    private Balance balance;

    private OverdraftLimit overdraftLimit;

    public Account() {
        //no-arg ctor
    }

    @CommandHandler
    public Account(CreateAccountCommand createAccountCommand) {
        apply(new AccountCreatedEvent(createAccountCommand.getAccountId(), createAccountCommand.getOverdraftLimit()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand withdrawMoneyCommand) throws OverdraftLimitExceededException {
        if (balance.with(overdraftLimit).canWithdrawn(withdrawMoneyCommand.getAmount())) {
            apply(new MoneyWithdrawnEvent(accountId, withdrawMoneyCommand.getAmount(), balance.decrease(withdrawMoneyCommand.getAmount())));
        } else {
            throw new OverdraftLimitExceededException();
        }
    }

    @CommandHandler
    public void handle(DepositMoneyCommand depositMoneyCommand) {
        apply(new MoneyDepositedEvent(accountId, depositMoneyCommand.getAmount(), this.balance.enrease(depositMoneyCommand.getAmount())));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountId = accountCreatedEvent.getAccountId();
        this.balance = new Balance(BigDecimal.ZERO);
        this.overdraftLimit = accountCreatedEvent.getOverdraftLimit();
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        this.balance = moneyWithdrawnEvent.getBalance();
    }

    @EventSourcingHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent) {
        this.balance = moneyDepositedEvent.getBalance();
    }
}
