package com.faculty.domain;

import com.common.hibernate.AppEntity;
import org.springframework.util.Assert;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "faculties")
public class Faculty implements AppEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;


    public Faculty(String name) {

        Assert.hasText(name, "name must hasText");

        this.name = name;
    }

    public Faculty() {
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
}