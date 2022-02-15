package com.message.domain;

import com.common.hibernate.AppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import com.user.domain.User;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Table(name = "messages")
@Entity
public class Message implements AppEntity {

    public final static String D_SENDER_ID = "sender_id";

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User recipient;

    @JoinColumn(nullable = false)
    LocalDateTime postLocalDateTime;

    String subject;

    @JoinColumn(nullable = false)
    String messageContent;

    @JoinColumn(nullable = false)
    MessageStatus status;

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }


    @Autowired
    public Message(User sender, User recipient, LocalDateTime postLocalDateTime, String subject, String messageContent, MessageStatus status) {

        Assert.notNull(sender, "sender must be not null");
        Assert.notNull(recipient, "recipient must be not null");
        Assert.notNull(postLocalDateTime, "postLocalDateTime must be not null");
        Assert.notNull(subject, "subject must be not null");
        Assert.notNull(messageContent, "messageContent must be not null");
        Assert.notNull(status, "status must be not null");


        this.sender = sender;
        this.recipient = recipient;
        this.postLocalDateTime = postLocalDateTime;
        this.subject = subject;
        this.messageContent = messageContent;
        this.status = status;
    }

    public Message() {
    }


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getPostLocalDateTime() {
        return postLocalDateTime;
    }

    public void setPostLocalDateTime(LocalDateTime postLocalDateTime) {
        this.postLocalDateTime = postLocalDateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
