package com.semester.application;

import com.semester.domain.SemesterStatus;
import org.springframework.util.Assert;

public class SemesterDto {

    private final long id;
    private final String name;
    private final SemesterStatus semesterStatus;
    private final String startDateTime;
    private final String finishDateTime;

    public SemesterDto(long id, String name, SemesterStatus semesterStatus, String startDateTime, String finishDateTime) {

        Assert.isTrue(id > 0, "id must be not null");
        Assert.hasText(name, "name must hasText");
        Assert.notNull(semesterStatus, "semesterStatus must be not null");
        Assert.notNull(startDateTime, "finishDateTime must be not null");
        Assert.notNull(finishDateTime, "finishDateTime must be not null");

        this.id = id;
        this.name = name;
        this.semesterStatus = semesterStatus;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SemesterStatus getSemesterStatus() {
        return semesterStatus;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getFinishDateTime() {
        return finishDateTime;
    }
}
