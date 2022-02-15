package com.faculty.application;

public class FacultyDto {

    private final long id;
    private final String name;

    public FacultyDto(long id, String name) {

        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
