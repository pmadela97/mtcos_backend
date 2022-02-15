package com.log.domain;

import org.springframework.lang.Nullable;

import javax.persistence.Embeddable;

@Embeddable
public class LogChange {

    private String fieldName;
    private String previousValue;
    private String nextValue;


    public LogChange(@Nullable String fieldName, @Nullable String previousValue, @Nullable String nextValue) {

        this.fieldName = fieldName;
        this.previousValue = previousValue;
        this.nextValue = nextValue;
    }

    public LogChange() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public String getNextValue() {
        return nextValue;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public void setNextValue(String nextValue) {
        this.nextValue = nextValue;
    }
}
