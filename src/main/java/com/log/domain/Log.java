package com.log.domain;

import com.common.hibernate.AppEntity;
import com.user.domain.User;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "logs")
public class Log implements AppEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User maker;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User consumer;

    @Column(nullable = false)
    private LogType logType;

    @Column(nullable = false)
    private LocalDateTime logDateTime;

    @Embedded
    LogChange change;

    public Log() {

    }

    public Log(User maker, User consumer, LogType logType, @Nullable LogChange change) {

        Assert.notNull(maker, "maker must be not null");
        Assert.notNull(consumer, "consumer must be not null");
        Assert.notNull(logType, "logType must be not null");

        this.maker = maker;
        this.consumer = consumer;
        this.logType = logType;
        this.logDateTime = LocalDateTime.now();
        this.change = change;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public LocalDateTime getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(LocalDateTime logDateTime) {
        this.logDateTime = logDateTime;
    }

    public LogChange getChange() {
        return change;
    }

    public void setChange(LogChange change) {
        this.change = change;
    }
}
