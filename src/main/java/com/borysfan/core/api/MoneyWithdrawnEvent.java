package com.borysfan.core.api;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.Balance;

public class MoneyWithdrawnEvent {

    private final AccountId accountId;
    private final String transactionId;
    private final Amount amount;
    private final Balance balance;

    public MoneyWithdrawnEvent(AccountId accountId, String transactionId, Amount amount, Balance balance) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.balance = balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Amount getAmount() {
        return amount;
    }

    public Balance getBalance() {
        return balance;
    }
}
