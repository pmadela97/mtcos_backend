package com.faculty.interfaces;

public class FacultyForm {

    private long id;
    private String name;

    public FacultyForm(long id, String name) {

        this.id = id;
        this.name = name;
    }

    public FacultyForm() {
    }

    public long getId() {
        return id;
    }

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
