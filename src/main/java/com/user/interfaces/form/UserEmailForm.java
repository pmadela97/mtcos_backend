package com.user.interfaces.form;

import org.springframework.util.Assert;

public class UserEmailForm {

    public static final String F_OLD_EMAIL_ADDRESS = "oldEmailAddress";
    public static final String F_NEW_EMAIL_ADDRESS = "newEmailAddress";

    private Long id;
    private String oldEmailAddress;
    private String newEmailAddress;


    public UserEmailForm() {
    }


    public UserEmailForm(Long id, String oldEmailAddress, String newEmailAddress) {

        Assert.notNull(id, "id must be not null");
        Assert.notNull(oldEmailAddress, "oldEmailAddress must be not null");
        Assert.notNull(newEmailAddress, "newEmailAddress must be not null");

        this.id = id;
        this.oldEmailAddress = oldEmailAddress;
        this.newEmailAddress = newEmailAddress;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldEmailAddress() {
        return oldEmailAddress;
    }

    public void setOldEmailAddress(String oldEmailAddress) {
        this.oldEmailAddress = oldEmailAddress;
    }

    public String getNewEmailAddress() {
        return newEmailAddress;
    }

    public void setNewEmailAddress(String newEmailAddress) {
        this.newEmailAddress = newEmailAddress;
    }
}
