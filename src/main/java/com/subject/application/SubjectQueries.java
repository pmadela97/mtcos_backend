package com.subject.application;

import com.subject.domain.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectQueries{

    List<Subject> getAll();

    List<SubjectDto> getAllDto();

    Optional<Subject> findSubjectByCode(String code);

    Optional<SubjectDto> findSubjectDtoByCode(String code);
}
