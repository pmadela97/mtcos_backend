package com.field.infrastructure.hibernate.command;

import com.common.hibernate.EntityRepository;
import com.field.domain.Field;
import com.field.domain.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateFieldRepository extends EntityRepository<Field> implements FieldRepository {

    @Autowired
    public HibernateFieldRepository(EntityManager entityManager) {
        super(entityManager);
    }
}
