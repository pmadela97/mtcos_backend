package com.user.interfaces.form;

import com.user.domain.UserStatus;
import org.springframework.util.Assert;

public class UserStatusForm {

    private Long id;
    private UserStatus status;


    public UserStatusForm() {
    }

    public UserStatusForm(Long id, UserStatus status) {

        Assert.notNull(id, "id must be not null");
        Assert.notNull(status, "status must be not null");

        this.id = id;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
