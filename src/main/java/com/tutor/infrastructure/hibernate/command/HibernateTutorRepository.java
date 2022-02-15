package com.tutor.infrastructure.hibernate.command;

import com.common.hibernate.EntityRepository;
import com.tutor.domain.Tutor;
import com.tutor.domain.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class HibernateTutorRepository extends EntityRepository<Tutor> implements TutorRepository {

    @Autowired
    public HibernateTutorRepository(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    @Transactional
    public void save(Tutor object) {

        entityManager.persist(object.getUser());
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(Tutor object) {

        entityManager.merge(object.getUser());
        entityManager.merge(object);
    }
}
