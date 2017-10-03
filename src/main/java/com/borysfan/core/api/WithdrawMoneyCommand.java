package com.borysfan.core.api;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class WithdrawMoneyCommand {

    @TargetAggregateIdentifier
    private final AccountId accountId;
    private final Amount amount;

    public WithdrawMoneyCommand(AccountId accountId, Amount amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }
}
