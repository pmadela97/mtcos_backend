package com.log.interfaces;

import com.log.application.LogDto;
import com.log.application.LogQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/log/list")
class LogListController {

    private final LogQueries logQueries;


    @Autowired
    public LogListController(LogQueries logQueries) {

        Assert.notNull(logQueries, "logQueries must be not null");

        this.logQueries = logQueries;
    }


    @GetMapping
    private List<LogDto> initView() {

        return logQueries.getAllLogDto();
    }
}