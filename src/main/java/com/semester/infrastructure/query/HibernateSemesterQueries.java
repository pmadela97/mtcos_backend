package com.semester.infrastructure.query;

import com.common.hibernate.EntityQuery;
import com.semester.application.SemesterDto;
import com.semester.application.SemesterQueries;
import com.semester.domain.Semester;
import com.semester.domain.SemesterStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.common.AppConstants.DATE_TIME_DTO_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;

@Service
public class HibernateSemesterQueries extends EntityQuery<Semester> implements SemesterQueries {

    @Autowired
    public HibernateSemesterQueries(EntityManager entityManager) {
        super(entityManager, Semester.class );
    }


    @Override
    public List<SemesterDto> getAllDto() {

        return getAll().stream()
                .map(this::create)
                .collect(toList());
    }

    @Override
    public Optional<SemesterDto> findSemesterDtoById(long id) {

        return findEntityById(id)
                .map(this::create);
    }

    @Override
    public List<SemesterDto> getActiveAndCreatedSemesters() {

        return getAll().stream()
                .filter(semester -> semester.getSemesterStatus() != SemesterStatus.DEACTIVATED)
                .map(this::create).collect(toList());
    }

    private SemesterDto create(Semester semester) {

        return new SemesterDto(semester.getId(),
                semester.getName(),
                semester.getSemesterStatus(),
                semester.getStartDateTime().format(ofPattern(DATE_TIME_DTO_PATTERN)),
                semester.getFinishDateTime().format(ofPattern(DATE_TIME_DTO_PATTERN)));
    }
}
