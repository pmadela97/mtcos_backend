package com.user.interfaces.controller;

import com.common.FormError;
import com.user.application.UserApplicationService;
import com.user.interfaces.form.UserEmailForm;
import com.user.interfaces.validator.UserEmailFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/email")
public class UserEmailEditController {

    private final UserApplicationService userApplicationService;;
    private final UserEmailFormValidator userEmailFormValidator;


    @Autowired
    public UserEmailEditController(UserApplicationService userApplicationService, UserEmailFormValidator userEmailFormValidator) {

        Assert.notNull(userApplicationService, "userService must be not null");
        Assert.notNull(userEmailFormValidator, "userEmailFormValidator must be not null");

        this.userApplicationService = userApplicationService;
        this.userEmailFormValidator = userEmailFormValidator;
    }


    @PostMapping
    private ResponseEntity<?> changeUserEmail(@Valid @RequestBody UserEmailForm userEmailForm, Errors errors) {

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));
        }

       try {

            userApplicationService.changeUserEmail(userEmailForm);

            return ResponseEntity.ok().build();
        }
       catch (EntityNotFoundException e) {

           return ResponseEntity.badRequest().build();
       }
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(userEmailFormValidator);
    }
}