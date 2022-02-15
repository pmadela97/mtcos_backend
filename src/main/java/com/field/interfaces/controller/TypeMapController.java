package com.field.interfaces.controller;


import com.field.domain.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/field/type/map")
class TypeMapController {

    @GetMapping
    private Map<String,String> initView() {

        return Arrays.stream(Type.values()).collect(Collectors.toMap(Type::name,Type::getValue));
    }
}
