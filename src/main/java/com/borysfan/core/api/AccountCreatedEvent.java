package com.borysfan.core.api;

import com.borysfan.core.AccountId;
import com.borysfan.core.OverdraftLimit;

public class AccountCreatedEvent {

    private final AccountId accountId;
    private final OverdraftLimit overdraftLimit;

    public AccountCreatedEvent(AccountId accountId, OverdraftLimit overdraftLimit) {
        this.accountId = accountId;
        this.overdraftLimit = overdraftLimit;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public OverdraftLimit getOverdraftLimit() {
        return overdraftLimit;
    }
}
