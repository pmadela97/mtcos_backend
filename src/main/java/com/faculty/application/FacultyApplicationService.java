package com.faculty.application;

import com.faculty.domain.Faculty;
import com.faculty.domain.FacultyRepository;
import com.log.application.LogApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;

import static com.log.application.LogApplicationService.createLogChange;
import static com.log.domain.LogType.CREATE;
import static com.log.domain.LogType.EDIT;

@Service
public class FacultyApplicationService {

    private final FacultyQueries facultyQueries;
    private final FacultyRepository facultyRepository;
    private final LogApplicationService logApplicationService;


    public FacultyApplicationService(FacultyQueries facultyQueries, FacultyRepository facultyRepository, LogApplicationService logApplicationService) {

        Assert.notNull(facultyQueries, "userQueries must be not null");
        Assert.notNull(facultyRepository, "facultyRepository must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.facultyQueries = facultyQueries;
        this.facultyRepository = facultyRepository;
        this.logApplicationService = logApplicationService;
    }

    public void addNewFaculty(String name, String makerUsername) {

        Assert.notNull(name, "name must be not null");
        Assert.notNull(makerUsername, "makerUsername must be not null");

        Faculty faculty = new Faculty(name);

        logApplicationService.addLog(makerUsername, "FACULTIES", CREATE, createLogChange("FACULTY","", name));
        facultyRepository.save(faculty);
    }

    public void editFaculty(long id, String name, String makerUsername) {

        Assert.notNull(id, "id must be not null");
        Assert.notNull(name, "name must be not null");
        Assert.notNull(makerUsername, "makerUsername must be not null");

        Faculty faculty = facultyQueries.findEntityById(id)
                .orElseThrow(EntityNotFoundException::new);

        String prev = faculty.getName();
        faculty.setName(name);
        String next = faculty.getName();

        logApplicationService.addLog(makerUsername, "FACULTIES", EDIT, createLogChange("name",prev, next));
        facultyRepository.update(faculty);
    }
}