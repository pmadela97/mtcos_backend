package com.tutor.application;

import com.log.application.LogApplicationService;
import com.log.domain.Log;
import com.tutor.domain.Tutor;
import com.tutor.domain.TutorRepository;
import com.tutor.interfaces.TutorForm;
import com.user.application.UserQueries;
import com.user.domain.Address;
import com.user.domain.User;
import com.user.domain.UserRepository;
import com.user.domain.UserStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

import static com.log.application.LogApplicationService.createLogChange;
import static com.log.domain.LogType.CREATE;
import static com.log.domain.LogType.EDIT;
import static com.tutor.interfaces.TutorForm.*;
import static com.user.domain.UserRole.TUTOR;

@Service
public class TutorApplicationService {

    private final UserQueries userQueries;
    private final TutorQueries tutorQueries;
    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final PasswordEncoder passwordEncoder;
    private final LogApplicationService logApplicationService;


    public TutorApplicationService(UserQueries userQueries, TutorQueries tutorQueries, UserRepository userRepository,TutorRepository tutorRepository, LogApplicationService logApplicationService) {

        Assert.notNull(userQueries, "userQueries must be not null");
        Assert.notNull(tutorQueries, "tutorQueries must be not null");
        Assert.notNull(userRepository, "userRepository must be not null");
        Assert.notNull(tutorRepository, "tutorRepository must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.userQueries = userQueries;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.logApplicationService = logApplicationService;
        this.tutorRepository = tutorRepository;
        this.tutorQueries = tutorQueries;
    }

    public void addNewTutor(TutorForm tutorForm) {

        Assert.notNull(tutorForm, "tutorForm must be not null");

        Tutor tutor = createNewTutor(tutorForm);

        tutorRepository.save(tutor);
        logApplicationService.addLog(getCurrentPrincipalName(), tutor.getUser().getUsername(), CREATE, createLogChange("TUTOR", "", tutorForm.getUsername()));
    }

    public void editTutor(TutorForm tutorForm) {

        Assert.notNull(tutorForm, "tutorForm must be not null");

        tutorQueries.findEntityById(tutorForm.getId()).ifPresent( tutor -> {

            List<Log> logList = setValues(tutorForm, tutor);
            tutorRepository.update(tutor);
            logApplicationService.addLogList(logList);
        });
    }

    private Tutor createNewTutor(TutorForm form) {

        Assert.notNull(form, "userForm must be not null");

        User user = new User(TUTOR,
                form.getUsername(),
                form.getFirstName(),
                form.getSecondName(),
                form.getLastName(),
                form.getMobileNumber(),
                new Address(form.getStreet(), form.getBuildingNumber(), form.getCity(), form.getPostCode()),
                UserStatus.ACTIVE,
                form.getEmailAddress(),
                passwordEncoder.encode(form.getPassword()).toCharArray());

        return new Tutor(form.getTitle(), user);
    }
    private String getCurrentPrincipalName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private List<Log> setValues(TutorForm form, Tutor tutor) {

        String currentPrincipalName = getCurrentPrincipalName();
        User user = tutor.getUser();

        List<Log> logList = new LinkedList<>();

        if (!form.getUsername().equals(user.getUsername())) {

            String prev = user.getUsername();
            String next = form.getUsername();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_USERNAME,
                    prev,
                    next
            );

            user.setUsername(value);
            logList.add(log);
        }

        if (!form.getFirstName().equals(user.getFirstName())) {

            String prev = user.getFirstName();
            String next = form.getFirstName();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_FIRST_NAME,
                    prev,
                    next
            );

            user.setFirstName(value);
            logList.add(log);
        }

        if (!form.getSecondName().equals(user.getSecondName())) {

            String prev = user.getSecondName();
            String next = form.getSecondName();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_SECOND_NAME,
                    prev,
                    next
            );

            user.setSecondName(value);
            logList.add(log);
        }

        if (!form.getLastName().equals(user.getLastName())) {

            String prev = user.getLastName();
            String next = form.getLastName();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_LAST_NAME,
                    prev,
                    next
            );

            user.setLastName(value);
            logList.add(log);
        }

        if (!form.getMobileNumber().equals(user.getMobileNumber())) {

            String prev = user.getMobileNumber();
            String next = form.getMobileNumber();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_MOBILE_NUMBER,
                    prev,
                    next
            );

            user.setMobileNumber(value);
            logList.add(log);
        }

        if (!form.getBuildingNumber().equals(user.getAddress().getBuildingNumber())) {

            String prev = user.getAddress().getBuildingNumber();
            String next = form.getBuildingNumber();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_BUILDING_NUMBER,
                    prev,
                    next
            );

            user.getAddress().setBuildingNumber(value);
            logList.add(log);
        }

        if (!form.getCity().equals(user.getAddress().getCity())) {

            String prev = user.getAddress().getCity();
            String next = form.getCity();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_CITY,
                    prev,
                    next
            );

            user.getAddress().setCity(value);
            logList.add(log);
        }

        if (!form.getPostCode().equals(user.getAddress().getPostCode())) {

            String prev = user.getAddress().getPostCode();
            String next = form.getPostCode();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_POST_CODE,
                    prev,
                    next
            );

            user.getAddress().setPostCode(value);
            logList.add(log);
        }

        if (!form.getTitle().equals(tutor.getTitle())) {

            String prev = tutor.getTitle();
            String next = form.getTitle();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    user.getUsername(),
                    EDIT,
                    F_TITLE,
                    prev,
                    next
            );

            tutor.setTitle(value);
            logList.add(log);
        }

        return logList;
    }
}