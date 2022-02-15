package com.user.application;

import com.log.application.LogApplicationService;
import com.log.domain.Log;
import com.user.domain.*;
import com.user.interfaces.form.UserEmailForm;
import com.user.interfaces.form.UserForm;
import com.user.interfaces.form.UserPasswordForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static com.log.application.LogApplicationService.createLogChange;
import static com.log.domain.LogType.CREATE;
import static com.log.domain.LogType.EDIT;
import static com.tutor.interfaces.TutorForm.*;

@Service
public class UserApplicationService implements UserDetailsService {

    private final UserQueries userQueries;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LogApplicationService logApplicationService;


    public UserApplicationService(UserQueries userQueries, UserRepository userRepository, LogApplicationService logApplicationService) {

        Assert.notNull(userQueries, "userQueries must be not null");
        Assert.notNull(userRepository, "userRepository must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.userQueries = userQueries;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.logApplicationService = logApplicationService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userQueries.findUserByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public void addNewUser(UserForm userForm, UserRole userRole) {

        Assert.notNull(userForm, "userForm must be not null");

        User user = createNewUser(userForm, userRole);

        userRepository.save(user);
        logApplicationService.addLog(getCurrentPrincipalName(), user.getUsername(), CREATE, createLogChange(userRole.name(), "", userForm.getUsername()));
    }

    public void editUser(UserForm userForm) {

        Assert.notNull(userForm, "userForm must be not null");

        userQueries.findEntityById(userForm.getId()).ifPresent( user -> {

            List<Log> logList = setValues(userForm, user);
            userRepository.update(user);
            logApplicationService.addLogList(logList);
        });
    }

    public void setUserStatus(long id, UserStatus status) {

        User user = userQueries.findEntityById(id).orElseThrow(EntityNotFoundException::new);

        String previousValue = user.getStatus().name();
        user.setStatus(status);
        String nextValue = user.getStatus().name();

        logApplicationService.addLog(getCurrentPrincipalName(), user.getUsername(), EDIT, createLogChange("status",previousValue, nextValue));
        userRepository.update(user);
    }

    public void changeUserPassword(UserPasswordForm userPasswordForm) {

        User user = userQueries.findEntityById(userPasswordForm.getId()).orElseThrow(EntityNotFoundException::new);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.changePassword(passwordEncoder.encode(userPasswordForm.getNewPassword()));

        logApplicationService.addLog(getCurrentPrincipalName(), user.getUsername(), EDIT, createLogChange("password","encrypted", "encrypted"));
        userRepository.update(user);
    }

    public void changeUserEmail(UserEmailForm userEmailForm) {

        User user = userQueries.findEntityById(userEmailForm.getId()).orElseThrow(EntityNotFoundException::new);

        String previousValue = user.getEmailAddress();
        user.setEmailAddress(userEmailForm.getNewEmailAddress());
        String nextValue = user.getEmailAddress();

        logApplicationService.addLog(getCurrentPrincipalName(), user.getUsername(), EDIT, createLogChange("email", previousValue, nextValue));
        userRepository.update(user);

    }

    private String getCurrentPrincipalName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private List<Log> setValues(UserForm form, User user) {

        String currentPrincipalName = getCurrentPrincipalName();

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
        return logList;
    }

    private User createNewUser(UserForm userForm, UserRole userRole) {

        Assert.notNull(userForm, "userForm must be not null");
        Assert.notNull(userRole, "userRole must be not null");

        return new User(userRole,
                userForm.getUsername(),
                userForm.getFirstName(),
                userForm.getSecondName(),
                userForm.getLastName(),
                userForm.getMobileNumber(),
                new Address(userForm.getStreet(), userForm.getBuildingNumber(), userForm.getCity(), userForm.getPostCode()),
                UserStatus.ACTIVE,
                userForm.getEmailAddress(),
                passwordEncoder.encode(userForm.getPassword()).toCharArray());
    }

}