package com.semester.interfaces;

import com.semester.domain.SemesterStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/semester/status/list")
public class SemesterStatusListController {

    @GetMapping
    private List<SemesterStatus> initView() {

        return List.of(SemesterStatus.values());
    }
}
