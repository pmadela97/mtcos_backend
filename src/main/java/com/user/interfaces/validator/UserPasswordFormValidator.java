package com.user.interfaces.validator;

import com.user.application.UserQueries;
import com.user.domain.User;
import com.user.interfaces.form.UserPasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Pattern;

import static com.common.AppConstants.*;
import static com.user.interfaces.form.UserPasswordForm.*;

@Service
public class UserPasswordFormValidator implements Validator {

    public final static String E_OLD_PASSWORD_NOT_BLANK = "Poprzednie hasło " + "nie może być puste";
    public final static String E_OLD_PASSWORD_NOT_SAME = "Poprzednie hasło " + "jest nieprawidłowe";
    public final static String E_NEW_PASSWORD_NOT_BLANK = "Nowe hasło " + "nie może być puste";
    public final static String E_NEW_PASSWORD_VALID = "Nowe hasło " + "musi mieć od 8 do 16 znaków";

    private final UserQueries userQueries;

    @Autowired
    public UserPasswordFormValidator(UserQueries userQueries) {

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return UserPasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserPasswordForm userPasswordForm = (UserPasswordForm) target;
        validateOldPassword(userPasswordForm, errors);
        validateNewPassword(userPasswordForm.getNewPassword(), errors);
    }

    private void validateNewPassword(String newPassword, Errors errors) {

        if (checkIfBlank(newPassword, errors, F_NEW_PASSWORD, E_NEW_PASSWORD_NOT_BLANK)) {

            return;
        }
        else if (!Pattern.matches(VALID_PASSWORD, newPassword)) {

            errors.reject(F_NEW_PASSWORD, E_NEW_PASSWORD_VALID);

        }
    }

    private void validateOldPassword(UserPasswordForm userPasswordForm, Errors errors) {

        if (checkIfBlank(userPasswordForm.getOldPassword(), errors, F_OLD_PASSWORD, E_OLD_PASSWORD_NOT_BLANK)) {

            return;
        }

        User user = userQueries.findEntityById(userPasswordForm.getId()).orElseThrow(EntityNotFoundException::new);

        if (!user.checkIfPasswordMatches(userPasswordForm.getOldPassword())) {

            errors.reject(F_OLD_PASSWORD, E_OLD_PASSWORD_NOT_SAME);
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

