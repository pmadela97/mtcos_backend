package com.common.hibernate;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public abstract class EntityQuery<T extends AppEntity> {

    protected final EntityManager entityManager;
    protected final Class<T> typeParameterClass;


    public EntityQuery(EntityManager entityManager, Class<T> typeParameterClass) {

        Assert.notNull(entityManager, "entityManager must be not null");
        Assert.notNull(typeParameterClass, "typeParameterClass must be not null");

        this.entityManager = entityManager;
        this.typeParameterClass = typeParameterClass;
    }

    @Transactional
    public List<T> getAll() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(typeParameterClass);
        Root<T> r = cq.from(typeParameterClass);

        TypedQuery<T> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Transactional
    public Optional<T> findEntityById(long id) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(typeParameterClass);
        Root<T> r = cq.from(typeParameterClass);

        cq.select(r).where(cb.equal(r.get("id"), id));

        TypedQuery<T> query = entityManager.createQuery(cq);

        return query.getResultStream().findFirst();
    }
}
