package com.user.interfaces.form;

import org.springframework.util.Assert;

public class UserPasswordForm {

    public static final String F_OLD_PASSWORD = "oldPassword";
    public static final String F_NEW_PASSWORD = "newPassword";

    private Long id;
    private String oldPassword;
    private String newPassword;


    public UserPasswordForm() {
    }

    public UserPasswordForm(Long id, String oldPassword, String newPassword) {

        Assert.notNull(id, "id must be not null");
        Assert.notNull(oldPassword, "oldPassword must be not null");
        Assert.notNull(newPassword, newPassword);

        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
