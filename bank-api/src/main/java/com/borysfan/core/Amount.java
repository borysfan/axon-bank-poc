package com.borysfan.core;

import java.math.BigDecimal;

public class Amount {

    private final BigDecimal value;

    public Amount(Long value) {
        this(new BigDecimal(value));
    }

    public Amount(BigDecimal value) {
        this.value = value;
    }

    public Amount subtract(Amount amount) {
        return new Amount(value.subtract(amount.value));
    }

    BigDecimal getValue() {
        return value;
    }

    public boolean isGreater(Amount amount) {
        return value.compareTo(amount.value) > 0;
    }

    public boolean isLess(Amount amount) {
        return value.compareTo(amount.value) < 0;
    }

    public Long asLong(){
        return value.longValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Amount amount = (Amount) o;

        return value.equals(amount.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
