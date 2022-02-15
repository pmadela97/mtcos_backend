package com.log.application;

public class LogDto {

    private final long id;
    private final String makerUsername;
    private final String consumerUsername;
    private final String dateTime;
    private final String logType;
    private final String fieldName;
    private final String previousValue;
    private final String nextValue;


    public LogDto(long id, String makerUsername, String consumerUsername, String dateTime, String logType, String fieldName, String previousValue, String nextValue) {

        this.id = id;
        this.makerUsername = makerUsername;
        this.consumerUsername = consumerUsername;
        this.dateTime = dateTime;
        this.logType = logType;
        this.fieldName = fieldName;
        this.previousValue = previousValue;
        this.nextValue = nextValue;
    }

    public long getId() {
        return id;
    }

    public String getMakerUsername() {
        return makerUsername;
    }

    public String getConsumerUsername() {
        return consumerUsername;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLogType() {
        return logType;
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
}
