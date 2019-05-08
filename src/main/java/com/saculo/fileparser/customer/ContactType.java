package com.saculo.fileparser.customer;

public enum ContactType {

    UNKNOWN(0),
    EMAIL(1),
    PHONE(2),
    JABBER(3);

    private final int value;

    ContactType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
