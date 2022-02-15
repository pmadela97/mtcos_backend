package com.user.interfaces.controller;

import com.user.application.UserDto;
import com.user.application.UserQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/list")
public class UserListController{

    private final UserQueries userQueries;


    @Autowired
    public UserListController(UserQueries userQueries) {

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }


    @GetMapping
    private List<UserDto> initView() {

        return userQueries.getAllDto();
    }
}
