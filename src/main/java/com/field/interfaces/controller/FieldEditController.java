package com.field.interfaces.controller;

import com.common.FormError;
import com.field.application.FieldApplicationService;
import com.field.application.FieldDto;
import com.field.application.FieldQueries;
import com.field.interfaces.FieldForm;
import com.field.interfaces.FieldFormValidator;
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
@RequestMapping("api/field/form")
public class FieldEditController {


    private final FieldQueries fieldQueries;
    private final FieldApplicationService fieldApplicationService;
    private final FieldFormValidator fieldFormValidator;

    private final static String P_ID = "id";


    @Autowired
    public FieldEditController(FieldQueries fieldQueries, FieldApplicationService fieldApplicationService, FieldFormValidator fieldFormValidator) {

        Assert.notNull(fieldQueries, "fieldQueries must be not null");
        Assert.notNull(fieldApplicationService, "fieldApplicationService must be not null");
        Assert.notNull(fieldFormValidator, "fieldFormValidator must be not null");

        this.fieldQueries = fieldQueries;
        this.fieldApplicationService = fieldApplicationService;
        this.fieldFormValidator = fieldFormValidator;
    }

    @GetMapping
    private FieldForm initView(@RequestParam(name = P_ID, required = false) @Nullable Long id) {

        if (id != null && id != 0) {

            FieldDto fieldDto = fieldQueries.findFieldDtoById(id)
                    .orElseThrow(EntityNotFoundException::new);

            return new FieldForm(fieldDto.getId(), fieldDto.getName(), fieldDto.getFacultyId(), fieldDto.getType().name(), fieldDto.getDegree().name());
        }
        else {

            return new FieldForm();
        }
    }

    @PostMapping
    private ResponseEntity<?> submitForm(@Valid @RequestBody FieldForm fieldForm, Errors errors) {

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(FormError.map(errors));

        }

        if (fieldForm.getId() == null || fieldForm.getId() == 0) {

            fieldApplicationService.addNewField(fieldForm);
        } else {

            fieldApplicationService.editField(fieldForm);
        }

        return ResponseEntity.ok().build();
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {

        binder.setValidator(fieldFormValidator);
    }
}