package com.faculty.infrastructure.hibernate.query;

import com.common.hibernate.EntityQuery;
import com.faculty.application.FacultyDto;
import com.faculty.application.FacultyQueries;
import com.faculty.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
class HibernateFacultyQueries extends EntityQuery<Faculty> implements FacultyQueries {

    @Autowired
    HibernateFacultyQueries(EntityManager entityManager) {

        super(entityManager, Faculty.class);
    }


    private FacultyDto create(Faculty faculty) {

        return new FacultyDto(faculty.getId(), faculty.getName());
    }

    @Override
    public List<FacultyDto> getAllDto() {

        return getAll().stream()
                .map(this::create)
                .collect(toList());
    }
}