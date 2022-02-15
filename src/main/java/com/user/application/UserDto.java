package com.user.application;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;


public class UserDto {

    private  Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String lastName;
    private String mobileNumber;
    private String emailAddress;
    private String street;
    private String buildingNumber;
    private String city;
    private String postCode;
    private String status;
    private String userRole;


    public UserDto(Long id, String username, String firstName, @Nullable String secondName, String lastName, String mobileNumber, String emailAddress, String street, String buildingNumber, String city, String postCode, String status, String userRole) {

        Assert.notNull(id, "id must be not null");
        Assert.hasText(firstName, "firstName must has text");
        Assert.hasText(lastName, "lastName must has text");

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
        this.userRole = userRole;
    }
    public UserDto(String firstName, String secondName, String lastName, String emailAddress) {

        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public UserDto(long id, String firstName, String secondName, String lastName, String emailAddress, String userRole) {

        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getUserRole() {
        return userRole;
    }
}
