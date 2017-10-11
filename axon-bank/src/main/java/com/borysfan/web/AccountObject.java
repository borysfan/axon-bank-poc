package com.borysfan.web;

import org.springframework.data.annotation.Id;

public class AccountObject {

    @Id
    private String accountId;
    private Long balance;
    private Long overdraft;

    public AccountObject() {
    }

    public AccountObject(String accountId, Long overdraft) {
        this.accountId = accountId;
        this.overdraft = overdraft;
        this.balance = 0L;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setOverdraft(Long overdraft) {
        this.overdraft = overdraft;
    }

    public String getAccountId() {
        return accountId;
    }

    public Long getOverdraft() {
        return overdraft;
    }

    public Long getBalance() {
        return balance;
    }

}
