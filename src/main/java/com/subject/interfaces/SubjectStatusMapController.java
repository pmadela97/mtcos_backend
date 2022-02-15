package com.subject.interfaces;

import com.subject.domain.SubjectStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subject/status/map")
public class SubjectStatusMapController{

    @GetMapping
    private Map<String,String> initView() {

        return Arrays.stream(SubjectStatus.values()).collect(Collectors.toMap(SubjectStatus::name,SubjectStatus::getValue));
    }
}