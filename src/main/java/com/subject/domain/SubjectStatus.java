package com.subject.domain;

public enum SubjectStatus {

    ACTIVE("AKTYWNY"),
    INACTIVE("NIEAKTYWNY");

    private final String value;

    SubjectStatus(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
