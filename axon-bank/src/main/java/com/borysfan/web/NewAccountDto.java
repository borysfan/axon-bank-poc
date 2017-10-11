package com.borysfan.web;

public class NewAccountDto {
    private String accountNumber;
    private Long overdraft;

    public NewAccountDto() {
    }

    public NewAccountDto(String accountNumber, Long overdraft) {
        this.accountNumber = accountNumber;
        this.overdraft = overdraft;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Long getOverdraft() {
        return overdraft;
    }
}
