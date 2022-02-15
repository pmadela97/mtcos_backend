package com.field.infrastructure.hibernate.query;

import com.common.hibernate.EntityQuery;
import com.field.application.FieldDto;
import com.field.application.FieldQueries;
import com.field.domain.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class HibernateFieldQueries extends EntityQuery<Field> implements FieldQueries {


    @Autowired
    public HibernateFieldQueries(EntityManager entityManager) {
        super(entityManager, Field.class);
    }

    @Override
    public List<FieldDto> getAllFieldListDto() {

        return getAll().stream()
                .map(this::createDto)
                .collect(toList());
    }

    @Override
    public Optional<FieldDto> findFieldDtoById(long id) {

        return findEntityById(id)
                .map(this::createDto);
    }

    @Override
    public List<FieldDto> findFieldListDtoByFacultyId(long facultyId) {

        Assert.notNull(facultyId, "facultyId must be not null");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Field> criteriaQuery = criteriaBuilder.createQuery(Field.class);
        Root<Field> root = criteriaQuery.from(Field.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("faculty"), facultyId));

        TypedQuery<Field> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().map(this::createDto).collect(toList());
    }

    @Override

    public Optional<FieldDto> findFieldDtoByName(String name) {
        Assert.hasText(name, "facultyId must has taxt");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Field> criteriaQuery = criteriaBuilder.createQuery(Field.class);
        Root<Field> root = criteriaQuery.from(Field.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<Field> query = entityManager.createQuery(criteriaQuery);

        return query.getResultStream().map(this::createDto).findFirst();

    }

    private FieldDto createDto(Field field) {

        return new FieldDto(field.getId(), field.getName(), field.getFaculty().getId(), field.getFaculty().getName(), field.getType(), field.getDegree());
    }
}