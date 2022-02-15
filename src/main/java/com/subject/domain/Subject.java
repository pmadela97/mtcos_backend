package com.subject.domain;

import com.common.hibernate.AppEntity;
import com.semester.domain.Semester;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject implements AppEntity{
    @Id
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isExam;

    @Column(nullable = false)
    private short ects;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(nullable = false)
    private SubjectStatus subjectStatus;



    public Subject() {
    }

    public Subject(String code, String name, boolean isExam, short ects, Semester semester, SubjectStatus subjectStatus) {

        Assert.hasText(code, "code must hastText");
        Assert.hasText(name, "name must hastText");
        Assert.notNull(isExam, "isExam must be not null");
        Assert.isTrue(ects >=0, "ects must be true");
        Assert.notNull(semester, "semester must be not null");
        Assert.notNull(subjectStatus, "subjectStatus must be not null");

        this.code = code;
        this.name = name;
        this.isExam = isExam;
        this.ects = ects;
        this.semester = semester;
        this.subjectStatus = subjectStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    public short getEcts() {
        return ects;
    }

    public void setEcts(short ects) {
        this.ects = ects;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public SubjectStatus getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(SubjectStatus subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }
}
