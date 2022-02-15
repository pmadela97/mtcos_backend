package com.semester.interfaces;

public class SemesterForm {

    public final static String F_NAME = "name";
    public final static String F_SEMESTER_STATUS = "semesterStatus";
    public final static String F_START_DATE_TIME = "startDateTime";
    public final static String F_FINISH_DATE_TIME = "finishDateTime";

    private Long id;
    private String name;
    String semesterStatus;
    String startDateTime;
    String finishDateTime;

    public SemesterForm(Long id, String name, String semesterStatus, String startDateTime, String finishDateTime) {
        this.id = id;
        this.name = name;
        this.semesterStatus = semesterStatus;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
    }

    public SemesterForm() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSemesterStatus() {
        return semesterStatus;
    }

    public void setSemesterStatus(String semesterStatus) {
        this.semesterStatus = semesterStatus;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(String finishDateTime) {
        this.finishDateTime = finishDateTime;
    }
}
