package com.borysfan.web;

public class NewTransferDto {

    private String fromAccountNumber;
    private String toAccountNumber;
    private Long amount;

    public NewTransferDto() {
    }

    public NewTransferDto(String fromAccountNumber, String toAccountNumber, Long amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public Long getAmount() {
        return amount;
    }
}
