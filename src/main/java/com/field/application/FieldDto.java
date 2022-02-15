package com.field.application;

import com.field.domain.Degree;
import com.field.domain.Type;

public class FieldDto {

    private final long id;
    private final String name;
    private final long facultyId;
    private final String facultyName;
    private final Type type;
    private final Degree degree;

    public FieldDto(long id, String name, long facultyId, String facultyName, Type type, Degree degree) {

        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.type = type;
        this.degree = degree;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public Type getType() {
        return type;
    }

    public Degree getDegree() {
        return degree;
    }

    public String getFacultyName() {
        return facultyName;
    }
}
