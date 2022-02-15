package com.field.interfaces.controller;


import com.field.domain.Degree;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/field/degree/map")
class DegreeMapController {

    @GetMapping
    private Map<String,String> initView() {

        return Arrays.stream(Degree.values()).collect(Collectors.toMap(Degree::name,Degree::getValue));
    }
}
