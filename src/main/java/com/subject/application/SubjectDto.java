package com.subject.application;

import com.semester.application.SemesterDto;
import com.subject.domain.Subject;
import com.subject.domain.SubjectStatus;

public class SubjectDto {

    private String code;
    private String name;
    private boolean isExam;
    private short ects;
    private SemesterDto semesterDto;
    private SubjectStatus subjectStatus;



    public SubjectDto(String code, String name, boolean isExam, short ects, SemesterDto semesterdto, SubjectStatus subjectStatus) {

        this.code = code;
        this.name = name;
        this.isExam = isExam;
        this.ects = ects;
        this.semesterDto = semesterdto;
        this.subjectStatus = subjectStatus;
    }

    public SubjectDto(Subject subject) {

        this.code = subject.getCode();
        this.name = subject.getName();
        this.isExam = subject.isExam();
        this.ects = subject.getEcts();
        this.semesterDto = subject.getSemester().map();
        this.subjectStatus = subject.getSubjectStatus();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isExam() {
        return isExam;
    }

    public short getEcts() {
        return ects;
    }

    public SemesterDto getSemesterDto() {
        return semesterDto;
    }

    public SubjectStatus getSubjectStatus() {
        return subjectStatus;
    }
}
