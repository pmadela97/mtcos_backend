package com.tutor.interfaces;


import com.common.FormError;
import com.log.application.LogApplicationService;
import com.tutor.application.TutorApplicationService;
import com.tutor.application.TutorQueries;
import com.tutor.domain.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/adm/tutor/form")
class TutorEditController{

    private final TutorQueries tutorQueries;
    private final TutorApplicationService tutorApplicationService;
    private final TutorFormValidator tutorFormValidator;
    private final LogApplicationService logApplicationService;

    private final static String P_ID = "id";


    @Autowired
    public TutorEditController(TutorQueries tutorQueries, TutorApplicationService tutorApplicationService, TutorFormValidator tutorFormValidator, LogApplicationService logApplicationService) {

        Assert.notNull(tutorQueries, "tutorQueries must be not null");
        Assert.notNull(tutorApplicationService, "tutorApplicationService must be not null");
        Assert.notNull(tutorFormValidator, "tutorFormValidator must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.tutorQueries = tutorQueries;
        this.tutorApplicationService = tutorApplicationService;
        this.tutorFormValidator = tutorFormValidator;
        this.logApplicationService = logApplicationService;
    }


    @GetMapping
    private TutorForm initView(@RequestParam(name = P_ID, required = false) @Nullable Long id) {

        TutorForm tutorForm;

        if (id != null && id != 0) {

            Tutor tutor = tutorQueries.findEntityById(id)
                    .orElseThrow(EntityNotFoundException::new);

            tutorForm = new TutorForm(tutor);;
        }
        else {

            tutorForm = new TutorForm();
        }

        return tutorForm;
    }

    @PostMapping
    private ResponseEntity<?> submitForm(@Valid @RequestBody TutorForm tutorForm, Errors errors, Principal principal) {

        if(errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));

        }

        if (tutorForm.getId() == null || tutorForm.getId() == 0) {

            tutorApplicationService.addNewTutor(tutorForm);
        }
        else {

            tutorApplicationService.editTutor(tutorForm);
        }

        return ResponseEntity.ok().build();
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(tutorFormValidator);
    }
}