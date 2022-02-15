package com.field.domain;

public enum Degree {

    ENGINEERING("INŻYNIERSKIE"), MASTER("MAGISTERSKIE"), DOCTOR("DOKTORANCKIE"), LONG_CYCLE("JEDNOLITE MAGISTERSKIE");

    String value;
    Degree(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
