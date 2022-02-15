package com.field.interfaces;

import org.springframework.lang.Nullable;

public class FieldForm {

    public final static String F_ID = "id";
    public final static String F_NAME = "name";
    public final static String F_FACULTY = "faculty";
    public final static String F_TYPE = "type";
    public final static String F_DEGREE = "degree";

    private Long id;
    private String name;
    private long faculty;
    private String type;
    private String degree;

    public FieldForm() {
    }

    public FieldForm(@Nullable Long id, String name, long faculty, String type, String degree) {

        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.type = type;
        this.degree = degree;
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

    public long getFaculty() {
        return faculty;
    }

    public void setFaculty(long faculty) {
        this.faculty = faculty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
