package com.subject.interfaces;

import com.subject.application.SubjectDto;
import com.subject.application.SubjectQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject/list")
public class SubjectListController{

    private final SubjectQueries subjectQueries;


    @Autowired
    public SubjectListController(SubjectQueries subjectQueries) {

        Assert.notNull(subjectQueries, "subjectQueries must be not null");

        this.subjectQueries = subjectQueries;
    }

    @GetMapping("/all")
    private List<SubjectDto> getAll() {

        return subjectQueries.getAllDto();
    }
}
