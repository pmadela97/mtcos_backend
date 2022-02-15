package com.log.application;

import com.log.domain.Log;
import com.log.domain.LogType;

import java.util.List;
import java.util.Optional;

public interface LogQueries {

    List<Log> getAll();

    List<LogDto> getAllLogDto();

    Optional<Log> findEntityById(long id);

    List<Log> findLogListByMakerId(long id);

    List<Log> findLogListByConsumerId(long id);

    List<Log> findLogListByLogType(LogType logType);

}

