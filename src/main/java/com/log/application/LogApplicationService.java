package com.log.application;

import com.log.domain.Log;
import com.log.domain.LogChange;
import com.log.domain.LogRepository;
import com.log.domain.LogType;
import com.user.application.UserQueries;
import com.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LogApplicationService {

    private final UserQueries userQueries;
    private final LogRepository logRepository;

    @Autowired
    public LogApplicationService(UserQueries userQueries, LogRepository logRepository) {

        Assert.notNull(userQueries, "userQueries must be not null");
        Assert.notNull(logRepository, "logRepository must be not null");

        this.userQueries = userQueries;
        this.logRepository = logRepository;
    }

    public void addLog(String makerUsername, String consumerUsername, LogType logType, @Nullable LogChange logChange) {

        Assert.notNull(makerUsername, "makerUsername must be not null");
        Assert.notNull(consumerUsername, "consumerUsername must be not null");
        Assert.notNull(logType, "logType must be not null");

        User maker = userQueries.findUserByUsername(makerUsername).orElseThrow(EntityNotFoundException::new);
        User consumer = userQueries.findUserByUsername(consumerUsername).orElse(maker);

        logRepository.save(new Log(maker, consumer, logType, logChange));
    }

    public void addLogList(List<Log> logList) {

        logList.stream().forEach(logRepository::save);
    }


    public static LogChange createLogChange(String fieldName, String previousValue, String nextValue) {

        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(previousValue, "previousValue must be not null");
        Assert.notNull(nextValue, "nextValue must be not null");

        return new LogChange(fieldName,previousValue,nextValue);
    }

    public Log createLog(String makerUsername, String consumerUsername, LogType logType, String fieldName, String prev, String next) {

        Assert.notNull(makerUsername, "makerUsername must be not null");
        Assert.notNull(consumerUsername, "consumerUsername must be not null");
        Assert.notNull(logType, "logType must be not null");
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(prev, "prev must be not null");
        Assert.notNull(next, "next must be not null");

        User maker = userQueries.findUserByUsername(makerUsername).orElseThrow(EntityNotFoundException::new);
        User consumer = userQueries.findUserByUsername(consumerUsername).orElse(maker);

        LogChange logChange = createLogChange(fieldName, prev, next);

        return new Log(maker, consumer, logType, logChange);
    }
}
