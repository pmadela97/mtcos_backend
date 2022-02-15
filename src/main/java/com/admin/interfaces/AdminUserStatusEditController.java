package com.admin.interfaces;

import com.user.application.UserApplicationService;
import com.user.application.UserQueries;
import com.user.domain.User;
import com.user.interfaces.form.UserStatusForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/user/status")
public class AdminUserStatusEditController{

    private final UserQueries userQueries;
    private final UserApplicationService userApplicationService;

    private final static String P_ID = "id";
    private final static String M_USER_FORM = "userForm";

    @Autowired
    public AdminUserStatusEditController(UserQueries userQueries, UserApplicationService userApplicationService) {

        Assert.notNull(userQueries, "userQueries must be not null");
        Assert.notNull(userApplicationService, "userService must be not null");

        this.userQueries = userQueries;
        this.userApplicationService = userApplicationService;
    }

    @PostMapping
    private ResponseEntity<?> setUserStatus(@RequestBody UserStatusForm userStatusForm) {

        Optional<User> user = userQueries.findEntityById(userStatusForm.getId());

        if (user.isPresent()) {

            userApplicationService.setUserStatus(user.get().getId(), userStatusForm.getStatus());

            return ResponseEntity.ok().build();
        }
        else {

            return ResponseEntity.badRequest().build();
        }
    }
}