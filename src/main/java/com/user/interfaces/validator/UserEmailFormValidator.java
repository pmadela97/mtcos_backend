package com.user.interfaces.validator;

import com.user.application.UserQueries;
import com.user.domain.User;
import com.user.interfaces.form.UserEmailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Pattern;

import static com.common.AppConstants.VALID_EMAIL_ADDRESS;
import static com.user.interfaces.form.UserEmailForm.*;

@Service
public class UserEmailFormValidator implements Validator {

    public final static String E_OLD_EMAIL_NOT_BLANK = "Poprzedni adres email " + "nie może być pusty";
    public final static String E_OLD_EMAIL_NOT_SAME = "Poprzedni adres email " + "jest nieprawidłowy";
    public final static String E_NEW_EMAIL_NOT_BLANK = "Nowy adres email " + "nie może być pusty";
    public final static String E_NEW_EMAIL_VALID = "Nowy adres email " + "musi mieć odpowiedni format";

    private final UserQueries userQueries;

    @Autowired
    public UserEmailFormValidator(UserQueries userQueries) {

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return UserEmailForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserEmailForm userEmailForm = (UserEmailForm) target;
        validateOldEmailAddress(userEmailForm, errors);
        validateNewEmailAddress(userEmailForm.getNewEmailAddress(), errors);
    }

    private void validateNewEmailAddress(String newEmailAddress, Errors errors) {

        if (checkIfBlank(newEmailAddress, errors, F_NEW_EMAIL_ADDRESS, E_NEW_EMAIL_NOT_BLANK)) {

            return;
        }

        if (!Pattern.matches(VALID_EMAIL_ADDRESS, newEmailAddress)) {

            errors.reject(F_NEW_EMAIL_ADDRESS, E_NEW_EMAIL_VALID);

            return;
        }
    }

    private void validateOldEmailAddress(UserEmailForm userEmailForm, Errors errors) {

        if (checkIfBlank(userEmailForm.getOldEmailAddress(), errors, F_OLD_EMAIL_ADDRESS, E_OLD_EMAIL_NOT_BLANK)) {

            return;
        }

        User user = userQueries.findEntityById(userEmailForm.getId()).orElseThrow(EntityNotFoundException::new);

        if (!user.getEmailAddress().equals(userEmailForm.getOldEmailAddress())) {

            errors.reject(F_OLD_EMAIL_ADDRESS, E_OLD_EMAIL_NOT_SAME);

            return;
        }

    }
    private boolean checkIfBlank(String value, Errors errors, String fieldName, String errorName) {

        if (!StringUtils.hasText(value)) {

            errors.reject(fieldName, errorName);

            return true;
        }
        return false;
    }
}

