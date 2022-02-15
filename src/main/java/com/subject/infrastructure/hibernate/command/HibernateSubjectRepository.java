package com.subject.infrastructure.hibernate.command;

import com.common.hibernate.EntityRepository;
import com.subject.domain.Subject;
import com.subject.domain.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateSubjectRepository extends EntityRepository<Subject> implements SubjectRepository{

    @Autowired
    public HibernateSubjectRepository(EntityManager entityManager) {
        super(entityManager);
    }

}
