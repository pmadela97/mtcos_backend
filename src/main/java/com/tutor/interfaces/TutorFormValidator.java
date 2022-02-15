package com.tutor.interfaces;

import com.user.application.UserQueries;
import com.user.domain.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.common.AppConstants.*;
import static com.tutor.interfaces.TutorForm.*;

@Service
public class TutorFormValidator implements Validator {

    public final static String E_USERNAME_NOT_BLANK = "Nazwa użytkownika" + " nie może być pusta";
    public final static String E_USERNAME_NOT_DUPLICATE = "Nazwa użytkownika" + " musi być unikalna";
    public final static String E_FIRST_NAME_NOT_BLANK = "Imię" + " nie może być puste";
    public final static String E_FIRST_NAME_BIG_FIRST_LETTER = "Imię" + " musi być z wielkiej litery";
    public final static String E_SECOND_NAME_BIG_FIRST_LETTER = "Drugie imię" + "m usi być z wielkiej litery";
    public final static String E_LAST_NAME_NOT_BLANK = "Nazwisko" + " nie może być puste";
    public final static String E_LAST_NAME_BIG_FIRST_LETTER = "Nazwisko" + " musi być z wielkiej litery";
    public final static String E_MOBILE_NUMBER_BLANK = "Numer kontaktowy" + " nie może być pusty";
    public final static String E_CITY_NOT_BLANK = "Miasto" + " nie może być puste";
    public final static String E_CITY_BIG_FIRST_LETTER = "Miasto" + " musi być z wielkiej litery";
    public final static String E_STREET_NOT_BLANK = "Ulica" + " nie może być pusta";
    public final static String E_STREET_BIG_FIRST_LETTER = "Ulica" + " musi być z wielkiej litery";
    public final static String E_BUILDING_NUMBER_NOT_BLANK = "Numer budynku" + " nie może być pusty";
    public final static String E_POST_CODE_NOT_BLANK = "Kod pocztowy" + " nie może być pusty";
    public final static String E_POST_CODE_WRONG_FORMAT = "Kod pocztowy" + " musi być w formacie XX-XXX";
    public final static String E_EMAIL_ADDRESS_NOT_BLANK = "Adres mailowy" + " nie może być pusty";
    public final static String E_EMAIL_ADDRESS_WRONG_FORMAT = "Adres mailowy" + " musi być w opdpowiednim formacie";
    public final static String E_EMAIL_ADDRESS_NOT_DUPLICATE = "Adres mailowy" + " musi być unikalny";
    public final static String E_PASSWORD_NOT_BLANK = "Hasło" + " nie może być puste";
    public final static String E_TITLE_NOT_BLNK = "Tytuł" + " nie może być pusty";

    private final UserQueries userQueries;


    @Autowired
    public TutorFormValidator(UserQueries userQueries) {

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return TutorForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TutorForm tutorForm = (TutorForm) target;
        validateUsername(tutorForm, errors);
        validateFirstName(tutorForm.getFirstName(), errors);
        validateSecondName(tutorForm.getSecondName(), errors);
        validateLastName(tutorForm.getLastName(), errors);
        validateMobileNumber(tutorForm.getMobileNumber(), errors);
        validateStreet(tutorForm.getStreet(), errors);
        validateCity(tutorForm.getCity(), errors);
        validatePostCode(tutorForm.getPostCode(), errors);
        validateEmailAddress(tutorForm, errors);
        validatePassword(tutorForm.getPassword(), tutorForm.getUserId(), errors);
        validateTitle(tutorForm.getTitle(), errors);
    }

    private void validateTitle(String title, Errors errors) {

        if (ifBlankRejectError(title, errors, F_TITLE, E_TITLE_NOT_BLNK)) {

            return;
        }
    }

    private void validateUsername(TutorForm tutorForm, Errors errors) {

        if (ifBlankRejectError(tutorForm.getUsername(), errors, F_USERNAME, E_USERNAME_NOT_BLANK)) {

            return;
        }

        Optional<User> user = userQueries.findUserByUsername(tutorForm.getUsername());

        if (user.isPresent()) {

            if (tutorForm.getUserId() == null || user.get().getId() !=  tutorForm.getUserId()) {

                errors.reject(F_USERNAME, E_USERNAME_NOT_DUPLICATE);

                return;
            }
        }
    }

    private void validatePassword(String password, Long id, Errors errors) {

        if (id == null || id == 0) {

            if (ifBlankRejectError(password, errors, F_PASSWORD, E_PASSWORD_NOT_BLANK)) {

                return;
            }
        }
    }

    private void validateEmailAddress(TutorForm tutorForm, Errors errors) {

        if (ifBlankRejectError(tutorForm.getEmailAddress(), errors, F_EMAIL_ADDRESS, E_EMAIL_ADDRESS_NOT_BLANK) ) {

            return;
        }

        if (!Pattern.matches(VALID_EMAIL_ADDRESS, tutorForm.getEmailAddress())) {

            errors.reject(F_EMAIL_ADDRESS, E_EMAIL_ADDRESS_WRONG_FORMAT);

            return;
        }
        Optional<User> emailUser = userQueries.findUserByEmailAddress(tutorForm.getEmailAddress());

        if (emailUser.isPresent()) {

            if (tutorForm.getUserId() == null || emailUser.get().getId() !=  tutorForm.getUserId()) {

                errors.reject(F_EMAIL_ADDRESS, E_EMAIL_ADDRESS_NOT_DUPLICATE);

                return;
            }
        }
    }

    private void validatePostCode(String postCode, Errors errors) {

        if (ifBlankRejectError(postCode, errors, F_POST_CODE, E_POST_CODE_NOT_BLANK)) {

            return;
        }
        else if (!Pattern.matches(VALID_POST_CODE_PATTERN, postCode)) {

            errors.reject(F_POST_CODE, E_POST_CODE_NOT_BLANK);

        }
    }

    private void validateCity(String city, Errors errors) {

        if (ifBlankRejectError(city, errors, F_CITY, E_CITY_NOT_BLANK)) {

            return;
        }
    }

    private void validateStreet(String street, Errors errors) {

        if (ifBlankRejectError(street, errors, F_CITY, E_CITY_NOT_BLANK)) {

            return;
        }
    }

    private void validateMobileNumber(String mobileNumber, Errors errors) {

        if (ifBlankRejectError(mobileNumber, errors, F_MOBILE_NUMBER, E_MOBILE_NUMBER_BLANK)) {

            return;
        }
    }

    private void validateSecondName(@Nullable String secondName, Errors errors) {

        if (StringUtils.hasText(secondName)) {

            if (!Pattern.matches(VALID_BIG_FIRST_LETTER_PATTERN, secondName)) {

                errors.reject(F_SECOND_NAME, E_SECOND_NAME_BIG_FIRST_LETTER);

                return;
            }
        }
    }

    private void validateFirstName(String firstName, Errors errors) {

        if (ifBlankRejectError(firstName, errors, F_FIRST_NAME, E_FIRST_NAME_NOT_BLANK)) {

            return;
        }

        if (!Pattern.matches(VALID_BIG_FIRST_LETTER_PATTERN, firstName)) {

            errors.reject(F_FIRST_NAME, E_FIRST_NAME_BIG_FIRST_LETTER);

            return;
        }
    }

    private void validateLastName(String lastName, Errors errors) {

        if (ifBlankRejectError(lastName, errors, F_LAST_NAME, E_LAST_NAME_NOT_BLANK)) {

            return;
        }

        if (!Pattern.matches(VALID_BIG_FIRST_LETTER_PATTERN, lastName)) {

            errors.reject(F_LAST_NAME, E_LAST_NAME_BIG_FIRST_LETTER);

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

