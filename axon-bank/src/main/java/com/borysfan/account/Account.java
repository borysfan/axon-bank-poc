package com.borysfan.account;

import com.borysfan.Host;
import com.borysfan.core.AccountId;
import com.borysfan.core.Balance;
import com.borysfan.core.OverdraftLimit;
import com.borysfan.core.OverdraftLimitExceededException;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@AggregateRoot
public class Account implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

    @AggregateIdentifier
    private AccountId accountId;

    private Balance balance;

    private OverdraftLimit overdraftLimit;

    private SampleBean sampleBean;

    public Account() {
        //no-arg ctor
    }

    public Account(AccountId accountId, OverdraftLimit overdraftLimit) {
        apply(new AccountCreatedEvent(accountId, overdraftLimit));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand withdrawMoneyCommand) throws OverdraftLimitExceededException {
        LOGGER.info("WithdrawMoneyCommand {}", Host.getName());
        if (balance.with(overdraftLimit).canWithdrawn(withdrawMoneyCommand.getAmount())) {
            apply(new MoneyWithdrawnEvent(accountId, withdrawMoneyCommand.getTransactionId(), withdrawMoneyCommand.getAmount(), balance.decrease(withdrawMoneyCommand.getAmount())));
        } else {
            throw new OverdraftLimitExceededException();
        }
    }

    @CommandHandler
    public void handle(DepositMoneyCommand depositMoneyCommand) {
        LOGGER.info("DepositMoneyCommand {}", Host.getName());
        apply(new MoneyDepositedEvent(accountId, depositMoneyCommand.getTransactionId(), depositMoneyCommand.getAmount(), this.balance.increase(depositMoneyCommand.getAmount())));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        LOGGER.info("AccountCreatedEvent {}", Host.getName());
        this.accountId = accountCreatedEvent.getAccountId();
        this.balance = new Balance(BigDecimal.ZERO);
        this.overdraftLimit = accountCreatedEvent.getOverdraftLimit();
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        LOGGER.info("MoneyWithdrawnEvent {}", Host.getName());
        this.balance = moneyWithdrawnEvent.getBalance();
    }

    @EventSourcingHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent) {
        LOGGER.info("MoneyDepositedEvent {}", Host.getName());
        this.balance = moneyDepositedEvent.getBalance();
    }

    @Autowired
    public void setSampleBean(SampleBean sampleBean) {
        this.sampleBean = sampleBean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!accountId.equals(account.accountId)) return false;
        if (!balance.equals(account.balance)) return false;
        return overdraftLimit.equals(account.overdraftLimit);
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + overdraftLimit.hashCode();
        return result;
    }
}
