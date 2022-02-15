package com.semester.interfaces;

import com.semester.application.SemesterApplicationService;
import com.semester.application.SemesterQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/semester/delete")
public class SemesterDeleteController {


    private final SemesterQueries semesterQueries;
    private final SemesterApplicationService semesterApplicationService;

    private final static String P_ID = "id";


    @Autowired
    public SemesterDeleteController(SemesterQueries semesterQueries, SemesterApplicationService semesterApplicationService) {

        Assert.notNull(semesterQueries, "semesterQueries must be not null");
        Assert.notNull(semesterApplicationService, "semesterApplicationService must be not null");

        this.semesterQueries = semesterQueries;
        this.semesterApplicationService = semesterApplicationService;
    }

    @PostMapping
    private ResponseEntity<?> delete(@RequestParam(name = P_ID) Long id) {

            semesterApplicationService.removeSemester(id);

            return ResponseEntity.ok().build();
    }
}