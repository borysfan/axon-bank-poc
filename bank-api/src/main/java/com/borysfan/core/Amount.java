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

    BigDecimal getValue() {
        return value;
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

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                '}';
    }
}
