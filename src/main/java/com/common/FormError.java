package com.common;

import io.jsonwebtoken.lang.Assert;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class FormError {

    private final String fieldName;
    private final String errorMessage;


    public FormError(String fieldName, String errorMessage) {

        Assert.notNull(fieldName, "fieldNameMustBeNotNull");
        Assert.notNull(fieldName, "errorMessage");

        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }


    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public static Object[] map(Errors errors) {

        return errors.getAllErrors().stream().map(error -> new FormError(error.getCode(), error.getDefaultMessage())).collect(Collectors.toList()).toArray();
    }
}
