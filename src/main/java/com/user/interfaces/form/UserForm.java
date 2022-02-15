package com.user.interfaces.form;

import com.user.domain.User;
import com.user.domain.UserStatus;
import org.springframework.util.Assert;

public class UserForm {


    public final static String F_ID = "id";
    public final static String F_USERNAME = "username";
    public final static String F_FIRST_NAME = "firstName";
    public final static String F_SECOND_NAME= "secondName";
    public final static String F_LAST_NAME = "lastName";
    public final static String F_MOBILE_NUMBER = "mobileNumber";
    public final static String F_STREET = "street";
    public final static String F_BUILDING_NUMBER = "buildingNumber";
    public final static String F_CITY = "city";
    public final static String F_POST_CODE = "postCode";
    public final static String F_EMAIL_ADDRESS = "emailAddress";
    public final static String F_PASSWORD = "password";

    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String lastName;
    private String mobileNumber;
    private String street;
    private String buildingNumber;
    private String city;
    private String postCode;
    private String emailAddress;
    private String password;
    private UserStatus status;


    public UserForm(Long id, String username, String firstName, String secondName, String lastName, String mobileNumber, String street, String buildingNumber, String city, String postCode, String emailAddress, String password, UserStatus status) {

        Assert.notNull(id, "id must be not null");
        Assert.notNull(username, "username must be not null");
        Assert.notNull(firstName, "firstName must be not null");
        Assert.notNull(secondName, "secondName must be not null");
        Assert.notNull(lastName, "lastName must be not null");
        Assert.notNull(mobileNumber, "mobileNumber must be not null");
        Assert.notNull(street, "street must be not null");
        Assert.notNull(buildingNumber, "buildingNumber must be not null");
        Assert.notNull(city, "city must be not null");
        Assert.notNull(postCode, "postCode must be not null");
        Assert.notNull(emailAddress, "emailAddress must be not null");
        Assert.notNull(password, "password must be not null");

        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.city = city;
        this.postCode = postCode;
        this.emailAddress = emailAddress;
        this.status = status;
        this.password =password;
    }

    public UserForm() {

    }

    public UserForm(User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.lastName = user.getLastName();
        this.mobileNumber = user.getMobileNumber();
        this.street = user.getAddress().getStreet();
        this.buildingNumber = user.getAddress().getBuildingNumber();
        this.city = user.getAddress().getCity();
        this.postCode = user.getAddress().getPostCode();
        this.emailAddress = user.getEmailAddress();
        this.status = user.getStatus();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }
}
