package com.user.interfaces.controller;

import com.common.FormError;
import com.user.application.UserApplicationService;
import com.user.interfaces.form.UserPasswordForm;
import com.user.interfaces.validator.UserPasswordFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/password")
public class UserPasswordEditController {

    private final UserApplicationService userApplicationService;
    private final UserPasswordFormValidator userPasswordFormValidator;


    @Autowired
    public UserPasswordEditController(UserApplicationService userApplicationService, UserPasswordFormValidator userPasswordFormValidator) {

        Assert.notNull(userApplicationService, "userService must be not null");
        Assert.notNull(userPasswordFormValidator, "userPasswordFormValidator must be not null");

        this.userApplicationService = userApplicationService;
        this.userPasswordFormValidator = userPasswordFormValidator;
    }


    @PostMapping
    private ResponseEntity<?> changeUserPassword(@Valid @RequestBody UserPasswordForm userPasswordForm, Errors errors) {

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));
        }

       try {

            userApplicationService.changeUserPassword(userPasswordForm);

            return ResponseEntity.ok().build();
        }
       catch (EntityNotFoundException e) {

           return ResponseEntity.badRequest().build();
       }
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(userPasswordFormValidator);
    }
}