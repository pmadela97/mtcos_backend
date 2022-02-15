package com.faculty.infrastructure.hibernate.command;

import com.common.hibernate.EntityRepository;
import com.faculty.domain.Faculty;
import com.faculty.domain.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
class HibernateFacultyRepository extends EntityRepository<Faculty> implements FacultyRepository {


    @Autowired
    HibernateFacultyRepository(EntityManager entityManager) {

        super(entityManager);
    }
}
