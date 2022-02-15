package com.common.hibernate;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public abstract class EntityRepository<T> {

    protected final EntityManager entityManager;



    public EntityRepository(EntityManager entityManager) {

        Assert.notNull(entityManager, "entityManager must be not null");

        this.entityManager = entityManager;
    }

    @Transactional
    public void save(T object) {

        entityManager.persist(object);
    }

    @Transactional
    public void update(T object) {

        entityManager.merge(object);
    }

    @Transactional
    public void delete(T object) {

        entityManager.remove(object);
    }
}
