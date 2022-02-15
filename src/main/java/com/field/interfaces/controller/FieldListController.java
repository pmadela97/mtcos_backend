package com.field.interfaces.controller;


import com.field.application.FieldDto;
import com.field.application.FieldQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/field/list")
class FieldListController {

    private final FieldQueries fieldQueries;


    @Autowired
    public FieldListController(FieldQueries fieldQueries) {

        Assert.notNull(fieldQueries, "fieldQueries must be not null");

        this.fieldQueries = fieldQueries;
    }


    @GetMapping
    private List<FieldDto> initView() {

        return fieldQueries.getAllFieldListDto();
    }
}
