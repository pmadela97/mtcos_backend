package com.semester.interfaces;

import com.semester.application.SemesterDto;
import com.semester.application.SemesterQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/semester/list")
public class SemesterListController {

    private final SemesterQueries semesterQueries;


    @Autowired
    public SemesterListController(SemesterQueries semesterQueries) {

        Assert.notNull(semesterQueries, "semesterQueries must be not null");

        this.semesterQueries = semesterQueries;
    }


    @GetMapping
    private List<SemesterDto> initView() {

        return semesterQueries.getAllDto();
    }

    @GetMapping("/noninactive")
    private List<SemesterDto> getNonInactive() {

        return semesterQueries.getActiveAndCreatedSemesters();
    }
}
