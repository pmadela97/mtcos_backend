package com.tutor.interfaces;

import com.tutor.application.TutorDto;
import com.tutor.application.TutorQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/adm/tutor/list")
public class TutorListController{

    private final TutorQueries tutorQueries;


    @Autowired
    public TutorListController(TutorQueries tutorQueries) {

        Assert.notNull(tutorQueries, "tutorQueries must be not null");

        this.tutorQueries = tutorQueries;
    }


    @GetMapping
    private List<TutorDto> initView() {

        return tutorQueries.getAllTutorListDto();
    }
}
