package com.user.infrastructure.hibernate.query;

import com.common.hibernate.EntityQuery;
import com.user.application.UserDto;
import com.user.application.UserQueries;
import com.user.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.user.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
class HibernateUserQueries extends EntityQuery<User> implements UserQueries {

    private static final String M_USERNAME = "username";
    private static final String M_USER_ROLE = "userRole";
    private static final String M_EMAIL_ADDRESS = "emailAddress";

    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    HibernateUserQueries(EntityManager entityManager) {

    super(entityManager, User.class);
    criteriaBuilder = entityManager.getCriteriaBuilder();

    }

    @Override
    @Transactional
    public Optional<User> findUserByUsername(String username) {

        Assert.notNull(username, "username must be not null");


            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(typeParameterClass);
            Root<User> root = criteriaQuery.from(typeParameterClass);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(M_USERNAME), username));

            TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

            return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Optional<User> findUserByEmailAddress(String emailAddress) {

        Assert.notNull(emailAddress, "emailAddress must be not null");


            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(typeParameterClass);
            Root<User> root = criteriaQuery.from(typeParameterClass);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(M_EMAIL_ADDRESS), emailAddress));

            TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

            return query.getResultStream().findFirst();

    }

    @Override
    public List<UserDto> getAllDto() {

        return getAll().stream()
                .map(this::createDto)
                .collect(toList());
    }

    @Override
    public Optional<UserDto> findUserDtoById(long id) {

        return findEntityById(id)
                .map(this::createDto);
    }

    @Override
    @Transactional
    public Optional<UserDto> findUserDtoByUsername(String username) {

        Assert.hasText(username, "username must be not null");

        return findUserByUsername(username)
                .map(this::createDto);
    }

    @Override
    public List<UserDto> getAllDtoByUserRole(UserRole userRole) {

        Assert.notNull(userRole, "userRole must be not null");


            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(typeParameterClass);
            Root<User> root = criteriaQuery.from(typeParameterClass);

            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(M_USER_ROLE), userRole));

            TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

           return query.getResultStream().map(this::createDto).collect(toList());
    }

    @Override
    public UserDto createDto(User user) {

        Assert.notNull(user, "user must be not null");

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getSecondName(),
                user.getLastName(),
                user.getMobileNumber(),
                user.getEmailAddress(),
                user.getAddress().getStreet(),
                user.getAddress().getBuildingNumber(),
                user.getAddress().getCity(),
                user.getAddress().getPostCode(),
                user.getStatus().name(),
                user.getUserRole().name());
    }
}