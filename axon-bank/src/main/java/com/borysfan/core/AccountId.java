package com.borysfan.core;

public class AccountId {

    private final String accountNumber;

    public AccountId(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String asString() {
        return accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountId accountId = (AccountId) o;

        return accountNumber != null ? accountNumber.equals(accountId.accountNumber) : accountId.accountNumber == null;
    }

    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "accountNumber='" + accountNumber + "'";
    }
}
