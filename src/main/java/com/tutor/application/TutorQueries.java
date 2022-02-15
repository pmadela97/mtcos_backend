package com.tutor.application;

import com.tutor.domain.Tutor;

import java.util.List;
import java.util.Optional;

public interface TutorQueries {

    List<Tutor> getAll();

    List<TutorDto> getAllTutorListDto();

    Optional<Tutor> findEntityById(long id);

    Optional<TutorDto> findEntityDtoById(long id);

    List<TutorDto> findTutorDtoListBySubject(long subjectId);
}
