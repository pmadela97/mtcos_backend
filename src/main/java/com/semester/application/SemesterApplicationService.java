package com.semester.application;

import com.log.application.LogApplicationService;
import com.log.domain.Log;
import com.semester.domain.Semester;
import com.semester.domain.SemesterRepository;
import com.semester.domain.SemesterStatus;
import com.semester.interfaces.SemesterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.common.AppConstants.DATE_TIME_DTO_PATTERN;
import static com.log.application.LogApplicationService.createLogChange;
import static com.log.domain.LogType.CREATE;
import static com.log.domain.LogType.EDIT;
import static com.semester.interfaces.SemesterForm.*;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class SemesterApplicationService {

    private final SemesterQueries semesterQueries;
    private final SemesterRepository semesterRepository;
    private final LogApplicationService logApplicationService;

    @Autowired
    public SemesterApplicationService(SemesterQueries semesterQueries, SemesterRepository semesterRepository, LogApplicationService logApplicationService) {

        Assert.notNull(semesterQueries, "semesterQueries must be not null");
        Assert.notNull(semesterRepository, "semesterRepository must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.semesterQueries = semesterQueries;
        this.semesterRepository = semesterRepository;
        this.logApplicationService = logApplicationService;
    }

    public void addNewSemester(SemesterForm semesterForm) {

        Assert.notNull(semesterForm, "semesterForm must be not null");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Semester semester = new Semester(semesterForm.getName(), SemesterStatus.valueOf(semesterForm.getSemesterStatus()), parse(semesterForm.getStartDateTime()),parse(semesterForm.getFinishDateTime()));

        logApplicationService.addLog(currentPrincipalName, currentPrincipalName, CREATE, createLogChange("SEMESTER", "", semester.getName()));
        semesterRepository.save(semester);

    }

    public void updateSemester(SemesterForm semesterForm) {

        Assert.notNull(semesterForm, "semesterForm must be not null");

        semesterQueries.findEntityById(semesterForm.getId()).ifPresent(semester -> {

            setValues(semesterForm, semester);
            semesterRepository.save(semester);
        });

    }

    public void removeSemester(Long id) {

        Assert.notNull(id, "id must be not null");

        semesterQueries.findEntityById(id).ifPresent(semester -> {

            semesterRepository.delete(semester);
        });
    }

    private void setValues(SemesterForm semesterForm, Semester semester) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        List<Log> logList = new LinkedList<>();

        if (!semesterForm.getName().equals(semester.getName())) {

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT, F_NAME, semester.getName(), semesterForm.getName()));
            semester.setName(semesterForm.getName());
        }

        if (!semesterForm.getSemesterStatus().equals(semester.getSemesterStatus().name()) ) {

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT, F_SEMESTER_STATUS, semester.getSemesterStatus().name(), semesterForm.getSemesterStatus()));
            semester.setSemesterStatus(SemesterStatus.valueOf(semesterForm.getSemesterStatus()));
        }

        if (parse(semesterForm.getStartDateTime()) != semester.getStartDateTime()) {

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT, F_START_DATE_TIME, semester.getStartDateTime().format(ofPattern(DATE_TIME_DTO_PATTERN)), semesterForm.getStartDateTime()));
            semester.setStartDateTime(LocalDateTime.parse(semesterForm.getStartDateTime()));
        }

        if (parse(semesterForm.getFinishDateTime()) != semester.getFinishDateTime()) {

            logList.add(logApplicationService.createLog(currentPrincipalName, currentPrincipalName, EDIT, F_FINISH_DATE_TIME, semester.getFinishDateTime().format(ofPattern(DATE_TIME_DTO_PATTERN)), semesterForm.getFinishDateTime()));
            semester.setFinishDateTime(LocalDateTime.parse(semesterForm.getFinishDateTime()));
        }

        logApplicationService.addLogList(logList);
    }


}
