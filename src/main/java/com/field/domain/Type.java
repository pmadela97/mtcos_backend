package com.field.domain;

public enum Type {

    FULL_TIME("DZIENNE"), PART_TIME("ZAOCZNE");

    private String value;

    Type(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
