package com.subject.interfaces;


import com.common.FormError;
import com.log.application.LogApplicationService;
import com.subject.application.SubjectApplicationService;
import com.subject.application.SubjectQueries;
import com.subject.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequestMapping("/api/adm/subject/form")
class SubjectEditController{

    private final SubjectQueries subjectQueries;
    private final SubjectApplicationService subjectApplicationService;
    private final SubjectFormValidator subjectFormValidator;
    private final LogApplicationService logApplicationService;


    @Autowired
    public SubjectEditController(SubjectQueries subjectQueries, SubjectApplicationService subjectApplicationService, SubjectFormValidator subjectFormValidator, LogApplicationService logApplicationService) {

        Assert.notNull(subjectQueries, "subjectQueries must be not null");
        Assert.notNull(subjectApplicationService, "subjectApplicationService must be not null");
        Assert.notNull(subjectFormValidator, "subjectFormValidator must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.subjectQueries = subjectQueries;
        this.subjectApplicationService = subjectApplicationService;
        this.subjectFormValidator = subjectFormValidator;
        this.logApplicationService = logApplicationService;
    }


    @GetMapping
    private SubjectForm initView(@RequestParam(name = "code", required = false) @Nullable String code) {

       if (hasText(code)) {

           return subjectQueries.findSubjectByCode(code)
                   .map(this::create)
                   .orElse(new SubjectForm());
       }
       else {

           return new SubjectForm();
       }

    }

    @PostMapping
    private ResponseEntity<?> submitForm(@Valid @RequestBody SubjectForm subjectForm, Errors errors) {

        if(errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));

        }

            subjectQueries.findSubjectDtoByCode(subjectForm.getCode())
                    .ifPresentOrElse(
                            (subjectDto -> {subjectApplicationService.editSubject(subjectForm);})
                            ,() -> subjectApplicationService.addNewSubject(subjectForm));

        return ResponseEntity.ok().build();
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(subjectFormValidator);
    }

    private SubjectForm create(Subject subject) {

        return new SubjectForm(subject);
    }
}