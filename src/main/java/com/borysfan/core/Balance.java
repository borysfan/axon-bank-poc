package com.borysfan.core;

import java.math.BigDecimal;

public class Balance {

    private final BigDecimal value;

    public Balance(Long value) {
        this(new BigDecimal(value));
    }

    public Balance(BigDecimal value) {
        this.value = value;
    }

    public Balance decrease(Amount amount) {
        return new Balance(value.subtract(amount.getValue()));
    }

    public Balance enrease(Amount amount) {
        return new Balance(value.add(amount.getValue()));
    }

    public Balance with(OverdraftLimit overdraftLimit) {
        return new Balance(value.add(overdraftLimit.getValue()));
    }

    public boolean canWithdrawn(Amount amount) {
        return this.value.compareTo(amount.getValue()) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        return value.equals(balance.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
