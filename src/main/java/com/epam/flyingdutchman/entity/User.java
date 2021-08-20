package com.epam.flyingdutchman.entity;

import java.util.Objects;

/**
 * The entity class represents a user
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class User extends Entity {
    /**
     * The unique identification {@code String} value for user's account, also used during authorization
     * process.
     */
    private String userName;
    /**
     * The secret {@code String} value that is determined by user and used during process of
     * authorization. Represents encrypted value of original password.
     */
    private String password;
    /**
     * The first name of the user.
     */
    private String firstName;
    /**
     * The last name of the user.
     */
    private String lastName;
    /**
     * The user's phone number.
     */
    private String phoneNumber;
    /**
     * The user's email
     */
    private String eMail;
    /**
     * {@code int} value represents user's role and defines user's account privileges.
     * {@code 1}-the role an administrator
     * {@code 2}-the role a manager
     * {@code 3}-the role a registered user
     * {@code 4}-the role a cook
     */
    private int userRole;
    /**
     * {@code boolean} value represents status of the user account. {@code true} - for active
     * account, {@code false} - for deactivated account.
     */
    private boolean active;

    /**
     * Explicit default constructor.
     */
    public User() {
    }

    /**
     * Constructor with all parameters, used to create instance of {@code User}
     *
     * @param userName    {@code String} represents the login of user
     * @param password    {@code String} represents the password of user
     * @param firstName   {@code String} represents the first name of user
     * @param lastName    {@code String} represents the last name of user
     * @param phoneNumber {@code String} represents the phone number of user
     * @param eMail       {@code String} represents the email of user
     * @param userRole    {@code int} represents the role of user
     * @param active      {@code boolean} represents the status of  user
     */
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

    /**
     * The constructor with some parameters, other fields of the User create database by default
     *
     * @param userName    {@code String} represents the login of user
     * @param password    {@code String} represents the password of user
     * @param firstName   {@code String} represents the first name of user
     * @param lastName    {@code String} represents the last name of user
     * @param phoneNumber {@code String} represents the phone number of user
     * @param eMail       {@code String} represents the email of user
     */
    public User(String userName, String password, String firstName, String lastName, String phoneNumber, String eMail) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} username that represents unique identification name for the user's
     * account.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param userName {@code String} value represents unique identification name for the user's
     *                 account.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} value that represents encrypted value of the secret user's password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param password {@code String} value that represents encrypted value of the secret user's
     *                 password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} that represents the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param firstName {@code String} represents first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} that represents the last name of the user
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param lastName {@code String} represents last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} that represents the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param phoneNumber {@code String} represents phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code String} that represents the email of the user
     */
    public String geteMail() {
        return eMail;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param eMail {@code String} represents email of the user
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code int} value represents user's role and defines user's account privileges
     */
    public int getUserRole() {
        return userRole;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param userRole {@code int} value represents user's role and defines user's account privileges
     */
    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
    /**
     * Standard getter method to access private class member.
     *
     * @return {@code boolean} value that represents status of the user account. {@code true} - for
     * active account, {@code false} - for deactivated accounts
     */
    public boolean getActive() {
        return active;
    }
    /**
     * Standard setter method to access private class member.
     *
     * @param active {@code boolean} value represents status of the user account. {@code true} - for
     *               active account, {@code false} - for deactivated accounts
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * The implementation of the equals method. Compare this instance of user to another
     * object.
     *
     * @param o {@code Object} to be compared with this {@code User}
     * @return {@code true} is object to compare is instance of {@code User} and it has the same
     * value of all class members
     */
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
    /**
     * The implementation of the hashCode() method. Uses method hash() of the {@code Objects} class get
     * the hash value of the class members.
     *
     * @return {@code int} value of the hash value fo the all class members
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, password, firstName, lastName, phoneNumber, eMail, userRole, active);
    }
    /**
     * The standard method which represents the User in the string value
     *
     * @return {@code String} the User
     */
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
