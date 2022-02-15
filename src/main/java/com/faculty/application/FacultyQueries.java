package com.faculty.application;

import com.faculty.domain.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyQueries {

    List<Faculty> getAll();

    List<FacultyDto> getAllDto();

    Optional<Faculty> findEntityById(long id);


}
