package com.faculty.interfaces;


import com.faculty.application.FacultyDto;
import com.faculty.application.FacultyQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/list")
class FacultyListController {

    private final FacultyQueries facultyQueries;


    @Autowired
    public FacultyListController(FacultyQueries facultyQueries) {

        Assert.notNull(facultyQueries, "facultyQueries must be not null");

        this.facultyQueries = facultyQueries;
    }


    @GetMapping
    private List<FacultyDto> initView() {

        return facultyQueries.getAllDto();
    }
}
