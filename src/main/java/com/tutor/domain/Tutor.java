package com.tutor.domain;

import com.common.hibernate.AppEntity;
import com.user.domain.User;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Table(name = "tutors")
@Entity
public class Tutor implements AppEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Tutor(String title, User user) {

        this.title = title;
        this.user = user;
    }

    public Tutor() {
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
