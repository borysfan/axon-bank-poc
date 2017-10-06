package com.borysfan.core.api;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class DepositMoneyCommand {

    @TargetAggregateIdentifier
    private final AccountId accountId;
    private final String transactionId;
    private final Amount amount;

    public DepositMoneyCommand(AccountId accountId, String transactionId, Amount amount) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
