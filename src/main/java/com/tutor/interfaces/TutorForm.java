package com.tutor.interfaces;

import com.tutor.domain.Tutor;
import com.user.domain.UserStatus;
import org.springframework.util.Assert;

public class TutorForm {

    public final static String F_TITLE = "title";
    public final static String F_USERNAME = "username";
    public final static String F_FIRST_NAME = "firstName";
    public final static String F_SECOND_NAME = "secondName";
    public final static String F_LAST_NAME = "lastName";
    public final static String F_MOBILE_NUMBER = "mobileNumber";
    public final static String F_STREET = "street";
    public final static String F_BUILDING_NUMBER = "buildingNumber";
    public final static String F_CITY = "city";
    public final static String F_POST_CODE = "postCode";
    public final static String F_EMAIL_ADDRESS = "emailAddress";
    public final static String F_PASSWORD = "password";

    private Long id;
    private String title;
    private Long userId;
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

    public TutorForm(Long id, String title, Long userId, String username, String firstName, String secondName, String lastName, String mobileNumber, String street, String buildingNumber, String city, String postCode, String emailAddress, String password, UserStatus status) {

        Assert.notNull(userId, "id must be not null");
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
        Assert.notNull(title, "title must be not null");

        this.id = id;
        this.userId = userId;
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
        this.password = password;
        this.title = title;
    }

    public TutorForm() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TutorForm(Tutor tutor) {

        this.id = tutor.getId();
        this.title = tutor.getTitle();
        this.userId = tutor.getUser().getId();
        this.username = tutor.getUser().getUsername();
        this.firstName = tutor.getUser().getFirstName();
        this.secondName = tutor.getUser().getSecondName();
        this.lastName = tutor.getUser().getLastName();
        this.mobileNumber = tutor.getUser().getMobileNumber();
        this.street = tutor.getUser().getAddress().getStreet();
        this.buildingNumber = tutor.getUser().getAddress().getBuildingNumber();
        this.city = tutor.getUser().getAddress().getCity();
        this.postCode = tutor.getUser().getAddress().getPostCode();
        this.emailAddress = tutor.getUser().getEmailAddress();
        this.status = tutor.getUser().getStatus();
    }
}
