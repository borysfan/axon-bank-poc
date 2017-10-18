package com.borysfan.web;

public class NewTrancheDto {

    private String mortageId;
    private String trancheId;
    private Long amount;

    public String getMortageId() {
        return mortageId;
    }

    public String getTrancheId() {
        return trancheId;
    }

    public Long getAmount() {
        return amount;
    }
}
