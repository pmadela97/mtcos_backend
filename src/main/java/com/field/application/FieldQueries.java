package com.field.application;

import com.field.domain.Field;

import java.util.List;
import java.util.Optional;

public interface FieldQueries {

    List<Field> getAll();

    List<FieldDto> getAllFieldListDto();

    Optional<Field> findEntityById(long id);

    Optional<FieldDto> findFieldDtoById(long id);

    List<FieldDto> findFieldListDtoByFacultyId(long facultyId);

    Optional<FieldDto> findFieldDtoByName(String name);
}
