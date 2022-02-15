package com.student.domain;


import com.common.hibernate.AppEntity;
import com.field.domain.Field;
import com.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

@Table(name = "students")
@Entity
public class Student implements AppEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @JoinColumn(nullable = false)
    long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "field_id")
    Field field;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private StudentStatus studentStatus;

    public Student() {
    }

    @Autowired
    public Student(User user, Field field, StudentStatus studentStatus) {


        this.user = user;
        this.field = field;
        this.studentStatus = studentStatus;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

}