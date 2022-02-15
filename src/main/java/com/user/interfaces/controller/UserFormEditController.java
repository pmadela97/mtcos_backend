package com.user.interfaces.controller;


import com.common.FormError;
import com.log.application.LogApplicationService;
import com.user.application.UserApplicationService;
import com.user.application.UserQueries;
import com.user.domain.User;
import com.user.interfaces.form.UserForm;
import com.user.interfaces.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import static com.user.domain.UserRole.ADM_WORKER;

@RestController
    @RequestMapping("/api/admin/user/form")
class UserFormEditController{

    private final UserQueries userQueries;
    private final UserApplicationService userApplicationService;
    private final UserFormValidator userFormValidator;
    private final LogApplicationService logApplicationService;

    private final static String P_ID = "id";


    @Autowired
    public UserFormEditController(UserQueries userQueries, UserApplicationService userApplicationService, UserFormValidator userFormValidator, LogApplicationService logApplicationService) {

        Assert.notNull(userQueries, "userQueries must be not null");
        Assert.notNull(userFormValidator, "userFormValidator must be not null");
        Assert.notNull(userApplicationService, "userService must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.userQueries = userQueries;
        this.userApplicationService = userApplicationService;
        this.userFormValidator = userFormValidator;
        this.logApplicationService = logApplicationService;
    }


    @GetMapping
    private UserForm initView(@RequestParam(name = P_ID, required = false) @Nullable Long id) {

        if (id != null && id != 0) {

            User user = userQueries.findEntityById(id)
                    .orElseThrow(EntityNotFoundException::new);

            UserForm userForm = new UserForm(user);

            return userForm;
        }
        else {

            return new UserForm();
        }
    }

    @PostMapping
    private ResponseEntity<?> submitForm(@Valid @RequestBody UserForm userForm, Errors errors) {

        if(errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));

        }

        if (userForm.getId() == null || userForm.getId() == 0) {

            userApplicationService.addNewUser(userForm, ADM_WORKER);
        }
        else {

            userApplicationService.editUser(userForm);
        }

        return ResponseEntity.ok().build();
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(userFormValidator);
    }
}