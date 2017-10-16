package com.borysfan.core;

public class PhoneNumber {

    private final String number;

    public PhoneNumber(String number) {
        if (number == null || !number.matches("\\d{3}-\\d{3}-\\d{3}")) {
            throw new IllegalArgumentException("Invalid phone number format. Expected format ex. 123-456-789");
        }
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
