package com.user.infrastructure.hibernate.command;

import com.common.hibernate.EntityRepository;
import com.user.domain.User;
import com.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
class HibernateUserRepository extends EntityRepository<User> implements UserRepository {


    @Autowired
    HibernateUserRepository(EntityManager entityManager) {

        super(entityManager);
    }
}
