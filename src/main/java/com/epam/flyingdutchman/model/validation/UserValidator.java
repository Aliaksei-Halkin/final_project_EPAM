package com.epam.flyingdutchman.model.validation;

public class UserValidator {
    private static final String USERNAME_REGEX = "[a-zA-Z0-9_\\-.]{4,30}";
    private static final String PASSWORD_REGEX = "[a-zA-Zа-яА-я0-9_\\-\\p{Punct}]{8,40}";
    private static final String PERSONAL_NAME = "[a-zA-Zа-яА-Я-]{2,50}";
    private static final String PHONE_NUMBER_REGEX = "\\+[0-9]{5,21}";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public boolean validateUserName(String userName) {
                return userName.matches(USERNAME_REGEX);
    }
    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
    public boolean isValidName(String personalName) {
        return personalName.matches(PERSONAL_NAME);
    }
    public boolean isValidPhone(String phone){
        return phone.matches(PHONE_NUMBER_REGEX);
    }
    public boolean isValidEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

}

