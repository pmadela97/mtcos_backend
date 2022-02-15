package com.semester.domain;

import com.common.hibernate.AppEntity;
import com.semester.application.SemesterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.common.AppConstants.DATE_TIME_DTO_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "semesters")
public class Semester implements AppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    SemesterStatus semesterStatus;

    @Column(nullable = false)
    LocalDateTime startDateTime;

    @Column(nullable = false)
    LocalDateTime finishDateTime;


    public Semester() {
    }

    @Autowired
    public Semester(String name, SemesterStatus semesterStatus, LocalDateTime startDateTime, LocalDateTime finishDateTime) {

        Assert.hasText(name, "name must hasText");
        Assert.notNull(semesterStatus, "semesterStatus must be not null");
        Assert.notNull(startDateTime, "finishDateTime must be not null");
        Assert.notNull(finishDateTime, "finishDateTime must be not null");

        this.name = name;
        this.semesterStatus = semesterStatus;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SemesterStatus getSemesterStatus() {
        return semesterStatus;
    }

    public void setSemesterStatus(SemesterStatus semesterStatus) {
        this.semesterStatus = semesterStatus;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public SemesterDto map() {

        return new SemesterDto(
                id,
                name,
                semesterStatus,
                startDateTime.format(ofPattern(DATE_TIME_DTO_PATTERN)),
                finishDateTime.format(ofPattern(DATE_TIME_DTO_PATTERN)));
    }
}