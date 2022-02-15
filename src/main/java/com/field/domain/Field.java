package com.field.domain;

import com.common.hibernate.AppEntity;
import com.faculty.domain.Faculty;
import org.springframework.util.Assert;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Table(name = "fields")
@Entity
public class Field implements AppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Degree degree;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Type type;

    public Field() {}


    public Field(String name, Faculty faculty, Degree degree, Type type) {

        Assert.hasText(name, "name must has text");
        Assert.notNull(faculty, "faculty must be not null");
        Assert.notNull(degree, "degree must be not null");
        Assert.notNull(type, "type must be not null");

        this.name = name;
        this.faculty = faculty;
        this.degree = degree;
        this.type = type;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}