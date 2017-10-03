package com.borysfan.core;

import java.math.BigDecimal;

public class OverdraftLimit {

    private final BigDecimal value;

    public OverdraftLimit(Long value) {
        this(new BigDecimal(value));
    }

    public OverdraftLimit(BigDecimal value) {
        this.value = value;
    }

    BigDecimal getValue() {
        return value;
    }

    public Long asLong() {
        return value.longValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OverdraftLimit that = (OverdraftLimit) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
