package com.borysfan.web;

import com.borysfan.core.api.AccountCreatedEvent;
import com.borysfan.core.api.MoneyDepositedEvent;
import com.borysfan.core.api.MoneyWithdrawnEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class AccountDetails {

    private final AccountObjectRepository accountObjectRepository;

    public AccountDetails(AccountObjectRepository accountObjectRepository) {
        this.accountObjectRepository = accountObjectRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        accountObjectRepository.save(new AccountObject(accountCreatedEvent.getAccountId().asString(), accountCreatedEvent.getOverdraftLimit().asLong()));
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        AccountObject accountObject = accountObjectRepository.findOne(moneyWithdrawnEvent.getAccountId().asString());
        accountObject.setBalance(moneyWithdrawnEvent.getBalance().asLong());
        accountObjectRepository.save(accountObject);
    }

    @EventHandler
    public void on(MoneyDepositedEvent moneyDepositedEvent) {
        AccountObject accountObject = accountObjectRepository.findOne(moneyDepositedEvent.getAccountId().asString());
        accountObject.setBalance(moneyDepositedEvent.getBalance().asLong());
        accountObjectRepository.save(accountObject);
    }

}
