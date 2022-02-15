package com.semester.interfaces;

import com.common.FormError;
import com.semester.application.SemesterApplicationService;
import com.semester.application.SemesterDto;
import com.semester.application.SemesterQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/semester/form")
public class SemesterEditController {


    private final SemesterQueries semesterQueries;
    private final SemesterApplicationService semesterApplicationService;
    private final SemesterFormValidator semesterFormValidator;

    private final static String P_ID = "id";


    @Autowired
    public SemesterEditController(SemesterQueries semesterQueries, SemesterApplicationService semesterApplicationService, SemesterFormValidator semesterFormValidator) {

        Assert.notNull(semesterQueries, "semesterQueries must be not null");
        Assert.notNull(semesterApplicationService, "semesterApplicationService must be not null");
        Assert.notNull(semesterFormValidator, "semesterFormValidator must be not null");

        this.semesterQueries = semesterQueries;
        this.semesterApplicationService = semesterApplicationService;
        this.semesterFormValidator = semesterFormValidator;
    }


    @GetMapping
    private SemesterForm initView(@RequestParam(name = P_ID, required = false) @Nullable Long id) {

        if (id != null && id != 0) {

            SemesterDto semesterDto = semesterQueries.findSemesterDtoById(id)
                    .orElseThrow(EntityNotFoundException::new);

            return new SemesterForm(
                    semesterDto.getId(),
                    semesterDto.getName(),
                    semesterDto.getSemesterStatus().name(),
                    semesterDto.getStartDateTime(),
                    semesterDto.getFinishDateTime());
        }
        else {

            return new SemesterForm();
        }
    }

    @PostMapping
    private ResponseEntity<?> submitForm(@Valid @RequestBody SemesterForm semesterForm, Errors errors) {

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));
        }

        if (semesterForm.getId() == null || semesterForm.getId() == 0) {

            semesterApplicationService.addNewSemester(semesterForm);
        }
        else {

            semesterApplicationService.updateSemester(semesterForm);
        }

        return ResponseEntity.ok().build();
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(semesterFormValidator);
    }
}
