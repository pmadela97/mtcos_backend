package com.tutor.infrastructure.hibernate.query;

import com.common.hibernate.EntityQuery;
import com.tutor.application.TutorDto;
import com.tutor.application.TutorQueries;
import com.tutor.domain.Tutor;
import com.user.application.UserDto;
import com.user.application.UserQueries;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class HibernateTutorQueries extends EntityQuery<Tutor> implements TutorQueries {

    private final UserQueries userQueries;

    @Autowired
    public HibernateTutorQueries(EntityManager entityManager, UserQueries userQueries) {

        super(entityManager, Tutor.class);

        Assert.notNull(userQueries, "userQueries must be not null");

        this.userQueries = userQueries;
    }

    @Override
    public List<TutorDto> getAllTutorListDto() {

        return getAll().stream()
                .map(this::createShortDto)
                .collect(toList());
    }

    @Override
    public Optional<TutorDto> findEntityDtoById(long id) {

        return findEntityById(id)
                .map(this::createFullDto);
    }


    @Override
    public List<TutorDto> findTutorDtoListBySubject(long subjectId) {
        // String queryString = "SELECT t from Tutor t LEFT JOIN tutors_subject ts ON ts.tuto_id = t.id WHERE ts.subject_id = ?1";
        TypedQuery<Tutor> query = entityManager.createQuery("queryString", Tutor.class);

       return query.setParameter(1, subjectId).getResultStream().map(this::createShortDto).collect(toList());
    }

    private TutorDto createShortDto(Tutor tutor) {

        return new TutorDto(
                tutor.getId(),
                tutor.getTitle(),
                new UserDto(
                        tutor.getUser().getFirstName(),
                        tutor.getUser().getSecondName(),
                        tutor.getUser().getLastName(),
                        tutor.getUser().getEmailAddress()
                )
        );
    }

    private TutorDto createShortDtoWithUserId(Tutor tutor) {

        return new TutorDto(
                tutor.getId(),
                tutor.getTitle(),
                new UserDto(
                        tutor.getUser().getId(),
                        tutor.getUser().getFirstName(),
                        tutor.getUser().getSecondName(),
                        tutor.getUser().getLastName(),
                        tutor.getUser().getEmailAddress(),
                        tutor.getUser().getUserRole().name()
                )
        );
    }
    private TutorDto createFullDto(Tutor tutor) {

        return new TutorDto(
                tutor.getId(),
                tutor.getTitle(),
                userQueries.createDto(tutor.getUser()));
    }
}