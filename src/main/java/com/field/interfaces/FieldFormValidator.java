package com.field.interfaces;

import com.field.application.FieldQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.field.interfaces.FieldForm.*;

@Service
public class FieldFormValidator implements Validator {

    public final static String E_NAME_NOT_BLANK = "Nazwa kierunku" + " nie może być pusta";
    private static final String E_NAME_UNIQUE = "Nazwa kierunku musi być unikalna";
    public final static String E_FACULTY_NOT_NULL = "Kierunek" + " nie może być pusty";
    public final static String E_TYPE_NOT_BLANK = "Tryb studiów" + " nie może być pusty";
    public final static String E_DEGREE_NOT_BLANK = "Stopień studiów" + " nie może być pusty";


    private final FieldQueries fieldQueries;


    @Autowired
    public FieldFormValidator(FieldQueries fieldQueries) {

        Assert.notNull(fieldQueries, "fieldQueries must be not null");
        this.fieldQueries = fieldQueries;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return FieldForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        FieldForm fieldForm = (FieldForm) target;
        validateName(fieldForm, errors);
        validateFaculty(fieldForm.getFaculty(), errors);
        validateType(fieldForm.getType(), errors);
        validateDegree(fieldForm.getDegree(), errors);
    }

    private void validateName(FieldForm fieldForm, Errors errors) {


        if (isBlank(fieldForm.getName(),errors, F_NAME,  E_NAME_NOT_BLANK)) {

            return;
        }
    }

    private void validateFaculty(long faculty, Errors errors) {

        if (faculty < 0) {

            errors.reject(F_FACULTY, E_FACULTY_NOT_NULL);
        }
    }

    private void validateType(String type, Errors errors) {

        isBlank(type, errors, F_TYPE, E_TYPE_NOT_BLANK);
    }

    private void validateDegree(String degree, Errors errors) {

        isBlank(degree, errors, F_DEGREE, E_DEGREE_NOT_BLANK);
    }


    private boolean isBlank(String value, Errors errors, String fieldName, String errorName) {

        if (!StringUtils.hasText(value)) {

            errors.reject(fieldName, errorName);

            return true;
        }

        return false;
    }

    private boolean isNotParsed(String value, Errors errors, String fieldName, String errorName) {

        try {

            LocalDateTime.parse(value);

            return false;
        }
        catch (DateTimeParseException e) {

            errors.reject(fieldName, errorName);

            return true;
        }
    }
}
