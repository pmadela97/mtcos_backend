package com.semester.infrastructure.command;

import com.common.hibernate.EntityRepository;
import com.semester.domain.Semester;
import com.semester.domain.SemesterRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateSemesterRepository extends EntityRepository<Semester> implements SemesterRepository {

    public HibernateSemesterRepository(EntityManager entityManager) {

        super(entityManager);
    }
}
