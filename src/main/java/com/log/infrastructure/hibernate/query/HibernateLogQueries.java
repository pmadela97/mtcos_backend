package com.log.infrastructure.hibernate.query;

import com.common.hibernate.EntityQuery;
import com.log.application.LogDto;
import com.log.application.LogQueries;
import com.log.domain.Log;
import com.log.domain.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.common.AppConstants.DATE_TIME_DTO_PATTERN;
import static java.util.stream.Collectors.toList;

@Service
public class HibernateLogQueries extends EntityQuery<Log> implements LogQueries {


    @Autowired
    public HibernateLogQueries(EntityManager entityManager) {

        super(entityManager, Log.class);
    }

    @Override
    public List<LogDto> getAllLogDto() {

        return getAll().stream()
                .map(this::create)
                .collect(toList());
    }

    @Override
    public List<Log> findLogListByMakerId(long id) {
        return null;
    }

    @Override
    public List<Log> findLogListByConsumerId(long id) {
        return null;
    }

    @Override
    public List<Log> findLogListByLogType(LogType logType) {
        return null;
    }

    private LogDto create(Log log) {

        String fieldName;
        String previousValue;
        String nextValue;

        if (log.getChange() != null) {

            fieldName = log.getChange().getFieldName();
            previousValue = log.getChange().getPreviousValue();
            nextValue = log.getChange().getNextValue();
        }
        else {

            fieldName = "";
            previousValue = "";
            nextValue = "";
        }

        return new LogDto(
                log.getId(),
                log.getMaker().getUsername(),
                log.getConsumer().getUsername(),
                log.getLogDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_DTO_PATTERN)),
                log.getLogType().name(),
                fieldName,
                previousValue,
                nextValue);
    }
}
