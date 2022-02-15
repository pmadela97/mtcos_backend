package com.user.interfaces.controller;


import com.user.application.UserDto;
import com.user.application.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/user/details")
class UserDetailsListController {

    private static final String P_ID = "id";
    private final UserQueries userQueries;



    @Autowired
    public UserDetailsListController(UserQueries userQueries) {

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }


    @GetMapping
    private ResponseEntity<?> initView(@RequestParam(name = P_ID, required = false) Long id) {

        if (id == null || id == 0) {

            return ResponseEntity.badRequest().build();
        }
        else {

            try {

                UserDto userDto = userQueries.findUserDtoById(id).orElseThrow(EntityNotFoundException::new);

                return ResponseEntity.ok(userDto);
            }
            catch (EntityNotFoundException e) {

                return ResponseEntity.badRequest().build();
            }
        }
    }
}
