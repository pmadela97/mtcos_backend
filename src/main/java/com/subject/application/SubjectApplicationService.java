package com.subject.application;

import com.log.application.LogApplicationService;
import com.log.domain.Log;
import com.semester.application.SemesterQueries;
import com.semester.domain.Semester;
import com.subject.domain.Subject;
import com.subject.domain.SubjectRepository;
import com.subject.interfaces.SubjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

import static com.log.application.LogApplicationService.createLogChange;
import static com.log.domain.LogType.CREATE;
import static com.log.domain.LogType.EDIT;
import static com.subject.domain.SubjectStatus.*;
import static com.subject.interfaces.SubjectForm.*;

@Service
public class SubjectApplicationService{

    private final SubjectQueries subjectQueries;
    private final SemesterQueries semesterQueries;
    private final SubjectRepository subjectRepository;
    private final LogApplicationService logApplicationService;


    @Autowired
    public SubjectApplicationService(SubjectQueries subjectQueries, SemesterQueries semesterQueries,SubjectRepository subjectRepository, LogApplicationService logApplicationService) {

        Assert.notNull(subjectQueries, "subjectQueries must be not null");
        Assert.notNull(semesterQueries, "semesterQueries must be not null");
        Assert.notNull(subjectRepository, "subjectRepository must be not null");
        Assert.notNull(logApplicationService, "logApplicationService must be not null");

        this.subjectQueries = subjectQueries;
        this.semesterQueries = semesterQueries;
        this.subjectRepository = subjectRepository;
        this.logApplicationService = logApplicationService;
    }

    public void addNewSubject(SubjectForm subjectForm) {

        Assert.notNull(subjectForm, "SubjectForm must be not null");

        Subject subject = create(subjectForm);

        subjectRepository.save(subject);
        logApplicationService.addLog(getCurrentPrincipalName(), getCurrentPrincipalName(), CREATE, createLogChange("SUBJECT", "", subjectForm.getCode()));
    }

    public void editSubject(SubjectForm subjectForm) {

        Assert.notNull(subjectForm, "subjectForm must be not null");

        subjectQueries.findSubjectByCode(subjectForm.getCode()).ifPresent( subject -> {

            List<Log> logList = setValues(subjectForm, subject);
            subjectRepository.update(subject);
            logApplicationService.addLogList(logList);
        });
    }

    private Subject create(SubjectForm subjectForm) {

        Assert.notNull(subjectForm, "userForm must be not null");

        Semester semester = semesterQueries.findEntityById(subjectForm.getSemester()).get();

        return new Subject(
                subjectForm.getCode(),
                subjectForm.getName(),
                subjectForm.isExam(),
                subjectForm.getEcts(),
                semester,
                valueOf(subjectForm.getSubjectStatus()));
    }

    private String getCurrentPrincipalName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private List<Log> setValues(SubjectForm form, Subject subject) {

        String currentPrincipalName = getCurrentPrincipalName();
        Semester semester = semesterQueries.findEntityById(form.getSemester()).get();

        List<Log> logList = new LinkedList<>();


        if (!form.getName().equals(subject.getName())) {

            String prev = subject.getName();
            String next = form.getName();
            String value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    subject.getCode(),
                    EDIT,
                    F_NAME,
                    prev,
                    next
            );

            subject.setName(value);
            logList.add(log);
        }

        if (!form.getEcts().equals(subject.getEcts())) {

            Short prev = subject.getEcts();
            Short next = form.getEcts();

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    subject.getCode(),
                    EDIT,
                    F_ECTS,
                    prev.toString(),
                    next.toString()
            );

            subject.setEcts(next);
            logList.add(log);
        }

        if (form.isExam() != subject.isExam()) {

            boolean prev = subject.isExam();
            boolean next = form.isExam();
            boolean value = next;

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    subject.getCode(),
                    EDIT,
                    F_IS_EXAM,
                    String.valueOf(prev),
                    String.valueOf(next)
            );

            subject.setExam(value);
            logList.add(log);
        }

        if (!form.getSemester().equals(subject.getSemester().getId())) {

            Long prev = subject.getSemester().getId();
            Long next = form.getSemester();

            Log log = logApplicationService.createLog(
                    currentPrincipalName,
                    subject.getCode(),
                    EDIT,
                    F_SEMESTER_ID,
                    prev.toString(),
                    next.toString()
            );

            subject.setSemester(semester);
            logList.add(log);
        }

        return logList;
    }
}