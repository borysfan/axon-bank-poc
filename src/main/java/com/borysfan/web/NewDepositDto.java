package com.borysfan.web;

public class NewDepositDto {
    private String accountNumber;
    private Long amount;

    public NewDepositDto() {
    }

    public NewDepositDto(String accountNumber, Long amount) {
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
