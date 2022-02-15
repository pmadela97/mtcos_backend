package com.subject.infrastructure.hibernate.query;

import com.common.hibernate.EntityQuery;
import com.subject.application.SubjectDto;
import com.subject.application.SubjectQueries;
import com.subject.domain.Subject;
import com.user.application.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class HibernateSubjectQueries extends EntityQuery<Subject> implements SubjectQueries {

    @Autowired
    public HibernateSubjectQueries(EntityManager entityManager, UserQueries userQueries) {

        super(entityManager, Subject.class);
    }

    @Override
    public List<SubjectDto> getAllDto() {

        return getAll().stream()
                .map(this::create)
                .collect(toList());
    }

    @Override
    public Optional<Subject> findSubjectByCode(String code) {

        String queryString = "SELECT s from Subject s WHERE s.code = :code";
        TypedQuery<Subject> query = entityManager.createQuery(queryString, Subject.class);

        return query.setParameter("code", code)
                .getResultStream().findFirst();

    }

    @Override
    public Optional<SubjectDto> findSubjectDtoByCode(String code) {

        return findSubjectByCode(code)
                .map(this::create);
    }

    private SubjectDto create(Subject subject) {

        return new SubjectDto(subject);
    }
}