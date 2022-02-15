package com.semester.interfaces;

import com.semester.application.SemesterQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.semester.interfaces.SemesterForm.*;

@Service
public class SemesterFormValidator implements Validator {

    public final static String E_NAME_NOT_BLANK = "Nazwa semesteru" + " nie może być pusta";
    public final static String E_START_DATE_TIME_NOT_BLANK = "Data rozpoczęcia" + " nie może być pusta";
    public final static String E_START_DATE_TIME_VALID = "Data rozpoczęcia" + " musi mieć prawidłowy format";
    public final static String E_START_DATE_TIME_NOT_DUPLICATED = "Data rozpoczęcia" + " musi być unikalna";
    public final static String E_START_DATE_BEFORE_FINISH_DATE = "Data rozpoczęcia" + " musi być przed datą zakończenia";

    public final static String E_FINISH_DATE_TIME_NOT_BLANK = "Data zakończenia" + " nie może być pusta";
    public final static String E_FINISH_DATE_TIME_VALID = "Data zakończenia" + " musi mieć prawidłowy format";
    public final static String E_FINISH_DATE_TIME_NOT_DUPLICATED = "Data zakończenia" + " musi być unikalna";
    public final static String E_SEMESTER_STATUS_NOT_NULL = "Status semestru nie może być pusty";

    private final SemesterQueries semesterQueries;


    @Autowired
    public SemesterFormValidator(SemesterQueries semesterQueries) {

        Assert.notNull(semesterQueries, "semesterQueries must be not null");
        this.semesterQueries = semesterQueries;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return SemesterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SemesterForm semesterForm = (SemesterForm) target;
        validateName(semesterForm.getName(), errors);
        validateStartDateTime(semesterForm, errors);
        validateFinishDateTime(semesterForm, errors);
        validateSemesterStatus(semesterForm.getSemesterStatus(), errors);
    }

    private void validateSemesterStatus(String semesterStatus, Errors errors) {

      isBlank(semesterStatus,errors,F_SEMESTER_STATUS,E_SEMESTER_STATUS_NOT_NULL);
    }

    private void validateName(String name, Errors errors) {

        if (isBlank(name,errors, F_NAME,  E_NAME_NOT_BLANK)) {

            return;
        }
    }

    private void validateStartDateTime(SemesterForm semesterForm, Errors errors) {

        if (isBlank(semesterForm.getStartDateTime(), errors, F_START_DATE_TIME, E_START_DATE_TIME_NOT_BLANK)) {

            return;
        }

        if (isNotParsed(semesterForm.getStartDateTime(), errors, F_START_DATE_TIME, E_START_DATE_TIME_VALID) ) {

            return;
        }
            semesterQueries.getAll().stream()
                    .filter(semester -> semester.getStartDateTime() == LocalDateTime.parse(semesterForm.getStartDateTime()))
                    .filter(semester -> semester.getId() != semesterForm.getId())
                    .findFirst()
                    .ifPresent(semester -> {

                        errors.reject(F_START_DATE_TIME, E_START_DATE_TIME_NOT_DUPLICATED);
                        return;
                    });
        if (LocalDateTime.parse(semesterForm.getStartDateTime()).isAfter(LocalDateTime.parse(semesterForm.getFinishDateTime()))) {

            errors.reject(F_START_DATE_TIME, E_START_DATE_BEFORE_FINISH_DATE);
        }
    }

    private void validateFinishDateTime(SemesterForm semesterForm, Errors errors) {

        if (isBlank(semesterForm.getFinishDateTime(), errors, F_FINISH_DATE_TIME, E_FINISH_DATE_TIME_NOT_BLANK)) {

            return;
        }

        if (isNotParsed(semesterForm.getFinishDateTime(), errors, F_FINISH_DATE_TIME, E_FINISH_DATE_TIME_VALID) ) {

            return;
        }

        semesterQueries.getAll().stream()
                .filter(semester -> semester.getFinishDateTime() == LocalDateTime.parse(semesterForm.getFinishDateTime()))
                .filter(semester -> semester.getId() != semesterForm.getId())
                .findFirst()
                .ifPresent( semester -> {

                    errors.reject(F_FINISH_DATE_TIME, E_FINISH_DATE_TIME_NOT_DUPLICATED);
                });
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
