package com.epam.flyingdutchman.entity;

import java.util.Objects;

/**
 * The type Entity.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class User extends Entity {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String eMail;
    private int userRole;
    private boolean active;

    /**
     * Explicit default constructor.
     */
    public User() {
    }

    public User(String userName, String password, String firstName, String lastName, String phoneNumber,
                String eMail, int userRole, boolean active) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.userRole = userRole;
        this.active = active;
    }

    public User(String userName, String password, String firstName, String lastName, String phoneNumber, String eMail) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userRole == user.userRole &&
                active == user.active &&
                userName.equals(user.userName) &&
                password.equals(user.password) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                phoneNumber.equals(user.phoneNumber) &&
                eMail.equals(user.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash( userName, password, firstName, lastName, phoneNumber, eMail, userRole, active);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User{");
        sb.append(", userName='").append(userName);
        sb.append(", password='").append(password);
        sb.append(", firstName='").append(firstName);
        sb.append(", lastName='").append(lastName);
        sb.append(", phoneNumber='").append(phoneNumber);
        sb.append(", eMail='").append(eMail);
        sb.append(", userRole=").append(userRole);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
