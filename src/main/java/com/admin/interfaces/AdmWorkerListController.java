package com.admin.interfaces;

import com.user.application.UserDto;
import com.user.application.UserQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.user.domain.UserRole.*;

@RestController
@RequestMapping("/api/admin/adm/list")
public class AdmWorkerListController {

    private final UserQueries userQueries;


    @Autowired
    public AdmWorkerListController(UserQueries userQueries) {

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }


    @GetMapping
    private List<UserDto> initView() {

        return userQueries.getAllDtoByUserRole(ADM_WORKER);
    }
}
