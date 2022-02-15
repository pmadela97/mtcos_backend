package com.semester.application;

import com.semester.domain.Semester;

import java.util.List;
import java.util.Optional;

public interface SemesterQueries {

    List<Semester> getAll();

    Optional<Semester> findEntityById(long id);

    List<SemesterDto> getAllDto();

    Optional<SemesterDto> findSemesterDtoById(long id);

    List<SemesterDto> getActiveAndCreatedSemesters();
}
