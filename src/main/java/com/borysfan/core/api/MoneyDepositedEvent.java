package com.borysfan.core.api;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.Balance;

public class MoneyDepositedEvent {

    private final AccountId accountId;
    private final Amount amount;
    private final Balance balance;

    public MoneyDepositedEvent(AccountId accountId, Amount amount, Balance balance) {
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Balance getBalance() {
        return balance;
    }
}
