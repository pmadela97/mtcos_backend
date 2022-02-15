package com.subject.interfaces;

import com.semester.application.SemesterQueries;
import com.semester.domain.Semester;
import com.semester.domain.SemesterStatus;
import com.subject.application.SubjectQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.subject.interfaces.SubjectForm.*;

@Service
public class SubjectFormValidator implements Validator {

    public final static String E_CODE_NOT_BLANK = "Kod przedmiotu" + " nie może być pusty";
    public final static String E_CODE_UNIQUE = "Kod przedmiotu" + " musi być unikalny";
    public final static String E_NAME_NOT_BLANK = "Nazwa przedmiotu" + " nie może być pusty";
    public final static String E_ECTS_NOT_NULL = "Punkty ects" + " nie mogą być puste";
    public final static String E_ECTS_NOT_NEGATIVE = "Punkty ects" + " nie mogą być mniejsze od zera";
    public final static String E_SEMESTER_NOT_NULL = "Semestr" + " nie może być pusty";
    public final static String E_SEMESTER_NOT_FOUND = "Nie znaleziono podanego semestru";
    public final static String E_SEMESTER_NOT_ACTIVE = "Semestr nie jest już aktywny";
    public final static String E_SUBJECT_STATUS_NOT_NULL = "Semestr nie jest już aktywny";

    private final SubjectQueries subjectQueries;
    private final SemesterQueries semesterQueries;


    @Autowired
    public SubjectFormValidator(SubjectQueries subjectQueries, SemesterQueries semesterQueries) {

        Assert.notNull(semesterQueries, "semesterQueries must be not null");
        Assert.notNull(subjectQueries, "subjectQueries must be not null");

        this.subjectQueries = subjectQueries;
        this.semesterQueries = semesterQueries;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return SubjectForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SubjectForm form = (SubjectForm) target;
        validateCode(form, errors);
        validateName(form.getName(), errors);
        validateEcts(form.getEcts(), errors);
        validateSemester(form, errors);
        validateSubjectStatus(form.getSubjectStatus(), errors);
    }

    private void validateSubjectStatus(String subjectStatus, Errors errors) {

        ifBlankRejectError(subjectStatus, errors, F_SUBJECT_STATUS, E_SUBJECT_STATUS_NOT_NULL);
    }

    private void validateCode(SubjectForm form, Errors errors) {

        if (ifBlankRejectError(form.getCode(), errors, F_CODE, E_CODE_NOT_BLANK)) {

            return;
        }

        subjectQueries.findSubjectDtoByCode(form.getCode()).ifPresent((subjectDto -> errors.reject(F_CODE, E_CODE_UNIQUE)));
    }

    private void validateName(String name, Errors errors) {

        ifBlankRejectError(name, errors, F_NAME, E_NAME_NOT_BLANK);
    }

    private void validateEcts(Short ects, Errors errors) {

        if (ects == null) {

            errors.reject(F_ECTS, E_ECTS_NOT_NULL);

            return;
        }

        if (ects < 0 ) {

            errors.reject(F_ECTS, E_ECTS_NOT_NEGATIVE);
        }
    }

    private void validateSemester(SubjectForm form, Errors errors) {

        if (form.getSemester() == null) {

            errors.reject(F_SEMESTER_ID, E_SEMESTER_NOT_NULL);

            return;
        }

        Optional<Semester> semesterOptional = semesterQueries.findEntityById(form.getSemester());

        if (semesterOptional.isEmpty()) {

            errors.reject(F_SEMESTER_ID, E_SEMESTER_NOT_FOUND);

            return;
        }
        else if (semesterOptional.get().getSemesterStatus() == SemesterStatus.DEACTIVATED){

            errors.reject(F_SEMESTER_ID, E_SEMESTER_NOT_ACTIVE);

            return;
        }
    }

    private boolean ifBlankRejectError(String value, Errors errors, String fieldName, String errorName) {

        if (!StringUtils.hasText(value)) {

            errors.reject(fieldName, errorName);

            return true;
        }

        return false;
    }
}