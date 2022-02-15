package com.tutor.interfaces;

import com.tutor.application.TutorDto;
import com.tutor.application.TutorQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutor/dto")
public class TutorDetailsController{

    private final TutorQueries tutorQueries;

    @Autowired
    public TutorDetailsController(TutorQueries tutorQueries) {

        Assert.notNull(tutorQueries, "tutorQueries must be not null");

        this.tutorQueries = tutorQueries;
    }


    @GetMapping("/details")
    private TutorDto getTutorDetails(@RequestParam(name = "id") Long id) {

        return tutorQueries.findEntityDtoById(id).orElse(null);
    }

}
