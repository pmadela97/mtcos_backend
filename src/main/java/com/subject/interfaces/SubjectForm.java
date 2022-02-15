package com.subject.interfaces;

import com.subject.domain.Subject;

public class SubjectForm{

    public final static String F_CODE = "code";
    public final static String F_NAME = "name";
    public final static String F_IS_EXAM = "isExam";
    public final static String F_ECTS = "ects";
    public final static String F_SEMESTER_ID = "semesterId";
    public final static String F_SUBJECT_STATUS = "subjectStatus";

    private String code;
    private String name;
    private boolean isExam;
    private Short ects;
    private Long semester;
    private String subjectStatus;

    public SubjectForm() {
    }

    public SubjectForm(Subject subject) {

        this.code = subject.getCode();
        this.name = subject.getName();
        this.isExam = subject.isExam();
        this.ects = subject.getEcts();
        this.semester = subject.getSemester().getId();
        this.subjectStatus = subject.getSubjectStatus().name();
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

    public Short getEcts() {
        return ects;
    }

    public void setEcts(Short ects) {
        this.ects = ects;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

    public String getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(String subjectStatus) {
        this.subjectStatus = subjectStatus;
    }


}
