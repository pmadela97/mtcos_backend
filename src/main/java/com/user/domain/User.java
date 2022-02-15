package com.user.domain;

import com.common.hibernate.AppEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static com.user.domain.UserStatus.*;

@Entity
@Table(name = "users")
public class User implements AppEntity, UserDetails {

    public final static String D_ID = "id";
    public final static String D_USER_ROLE = "userRole";

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Enumerated(STRING)
    private UserRole userRole;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 128)
    private String username;

    @Column(nullable = false)
    private String firstName;

    private String secondName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String mobileNumber;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Enumerated(STRING)
    private UserStatus status;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 128)
    private String emailAddress;

    @Column(nullable = false)
    private char[] password;

    public User() {
    }


    public User(UserRole userRole, String username, String firstName, String secondName, String lastName, String mobileNumber, Address address, UserStatus status, String emailAddress, char[] password) {

        Assert.notNull(userRole, "userRole must be not null");
        Assert.notNull(username, "username must be not null");
        Assert.notNull(secondName, "secondName must be not null");
        Assert.notNull(lastName, "lastName must be not null");
        Assert.notNull(mobileNumber, "mobileNumber must be not null");
        Assert.notNull(address, "address must be not null");
        Assert.notNull(status, "status must be not null");
        Assert.notNull(emailAddress, "emailAddress must be not null");
        Assert.notNull(password, "password must be not null");

        this.userRole = userRole;
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.status = status;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void modify(String firstName, String secondName, String lastName, String mobileNumber, Address address, String emailAddress) {

        Assert.notNull(secondName, "secondName must be not null");
        Assert.notNull(lastName, "lastName must be not null");
        Assert.notNull(mobileNumber, "mobileNumber must be not null");
        Assert.notNull(address, "address must be not null");
        Assert.notNull(emailAddress, "emailAddress must be not null");

        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.emailAddress = emailAddress;
    }

    public void changePassword(String password) {

        Assert.hasText(password, "password must has text");

        this.password = password.toCharArray();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public boolean isAccountNonExpired() {

        if (status != ARCHIVED) return true;

        return false;
    }

    @Override
    public boolean isAccountNonLocked() {

        if (status != BLOCKED && status != null) return true;

        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        if (status != ARCHIVED && status != null) return true;

        return false;
    }

    @Override
    public boolean isEnabled() {

        if (status == ACTIVE && status != null) return true;

        return false;
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

    public Address getAddress() {
        return address;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getPassword() {
        return new String(password);
    }

    public boolean checkIfPasswordMatches(String password) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, getPassword());
    }
    @Override
    public String getUsername() {
        return username;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setEmailAddress(String newEmailAddress) {
        this.emailAddress = newEmailAddress;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}