package com.borysfan.mortage;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class MortageObject implements Serializable {

    @Id
    private String mortageId;

    private Long moneyToTransfer;

    public MortageObject() {
    }

    public MortageObject(String mortageId, Long moneyToTransfer) {
        this.mortageId = mortageId;
        this.moneyToTransfer = moneyToTransfer;
    }

    public void setMoneyToTransfer(Long moneyToTransfer) {
        this.moneyToTransfer = moneyToTransfer;
    }

    public String getMortageId() {
        return mortageId;
    }

    public Long getMoneyToTransfer() {
        return moneyToTransfer;
    }
}
