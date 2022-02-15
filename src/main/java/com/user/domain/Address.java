package com.user.domain;

import org.springframework.util.Assert;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class Address {

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String buildingNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postCode;


    Address() {

    }

    public Address(String street, String buildingNumber, String city, String postCode) {

        Assert.notNull(street, "street must be not null");
        Assert.notNull(buildingNumber, "buildingNumber must be not null");
        Assert.notNull(city, "city must be not null");
        Assert.notNull(postCode, "postCode must be not null");

        this.street = street;
        this.buildingNumber = buildingNumber;
        this.city = city;
        this.postCode = postCode;
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
}
