package com.borysfan.web;

public class NewWithdrawDto {
    private String accountNumber;
    private Long amount;

    public NewWithdrawDto() {
    }

    public NewWithdrawDto(String accountNumber, Long amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Long getAmount() {
        return amount;
    }
}
