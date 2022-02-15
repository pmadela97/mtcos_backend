package com.log.infrastructure.hibernate.command;

import com.common.hibernate.EntityRepository;
import com.log.domain.Log;
import com.log.domain.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateLogRepository extends EntityRepository<Log> implements LogRepository {

    @Autowired
    public HibernateLogRepository(EntityManager entityManager) {

        super(entityManager);
    }
}
