package com.field.application;

import com.faculty.application.FacultyQueries;
import com.faculty.domain.Faculty;
import com.field.domain.Degree;
import com.field.domain.Field;
import com.field.domain.FieldRepository;
import com.field.domain.Type;
import com.field.interfaces.FieldForm;
import com.log.application.LogApplicationService;
import com.log.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import static com.field.interfaces.FieldForm.*;
import static com.log.application.LogApplicationService.createLogChange;
import static com.log.domain.LogType.CREATE;
import static com.log.domain.LogType.EDIT;

@Service
public class FieldApplicationService {

    private final FieldQueries fieldQueries;
    private final FacultyQueries facultyQueries;
    private final FieldRepository fieldRepository;
    private final LogApplicationService logApplicationService;


    @Autowired
    public FieldApplicationService(FieldQueries fieldQueries, FieldRepository fieldRepository, FacultyQueries facultyQueries, LogApplicationService logApplicationService) {

        Assert.notNull(fieldQueries, "fieldQueries must be not null");
        Assert.notNull(fieldRepository, "fieldRepository must be not null");
        Assert.notNull(facultyQueries, "facultyQueries must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.fieldQueries = fieldQueries;
        this.facultyQueries = facultyQueries;
        this.fieldRepository = fieldRepository;
        this.logApplicationService = logApplicationService;
    }

    @Transactional
    public void addNewField(FieldForm fieldForm) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Assert.notNull(fieldForm, "fieldForm must be not null");

        Faculty faculty = facultyQueries.findEntityById(fieldForm.getFaculty())
                .orElseThrow(EntityNotFoundException::new);

        Field field = new Field(fieldForm.getName(), faculty, Degree.valueOf(fieldForm.getDegree()), Type.valueOf(fieldForm.getType()));

        fieldRepository.save(field);
        logApplicationService.addLog(currentPrincipalName, currentPrincipalName, CREATE, createLogChange("FIELD", "", field.getName()));
    }

    @Transactional
    public void editField(FieldForm fieldForm) {

        Assert.notNull(fieldForm, "fieldForm must be not null");

        Field field = fieldQueries.findEntityById(fieldForm.getId())
                .orElseThrow(EntityNotFoundException::new);

        List<Log> logList = setValues(fieldForm, field);
        fieldRepository.update(field);
        logApplicationService.addLogList(logList);
    }

    private List<Log> setValues(FieldForm fieldForm, Field field) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Log> logList = new LinkedList<>();


        if (!fieldForm.getName().equals(field.getName())) {

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT, F_NAME, String.valueOf(field.getName()), fieldForm.getName()));
            field.setName(fieldForm.getName());
        }

        if (fieldForm.getFaculty() != field.getFaculty().getId()) {

            facultyQueries.findEntityById(fieldForm.getFaculty()).ifPresent(faculty -> {

                logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT, F_FACULTY, String.valueOf(field.getFaculty().getId()), String.valueOf(fieldForm.getFaculty())));
                field.setFaculty(faculty);
            });
        }

        if (Degree.valueOf(fieldForm.getDegree()) != field.getDegree()) {

            String prev = field.getDegree().name();
            String next = fieldForm.getDegree();
            Degree value = Degree.valueOf(next);

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT,F_DEGREE, prev, next));
            field.setDegree(value);
        }

        if (Type.valueOf(fieldForm.getType()) != field.getType()) {

            String prev = field.getType().name();
            String next = fieldForm.getType();
            Type value = Type.valueOf(next);

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT,F_TYPE, prev, next));
            field.setType(value);
        }
        return logList;
    }
}