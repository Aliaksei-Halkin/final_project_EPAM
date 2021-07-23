package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.model.validation.UserValidator;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.*;

public class RegisterUserCommand implements Command {
    private final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        String userName = request.getParameter(REQUEST_USERNAME);
        String password = request.getParameter(REQUEST_PASSWORD);
        String firstName = request.getParameter(REQUEST_FIRST_NAME);
        String lastName = request.getParameter(REQUEST_LAST_NAME);
        String phoneNumber = request.getParameter(REQUEST_PHONE);
        String eMail = request.getParameter(REQUEST_EMAIL);
        StringBuilder validationStatus = new StringBuilder();
        String registrationStatus;
        if (validationUserData(userName, password, firstName, lastName, phoneNumber, eMail, validationStatus)) {
            UserServiceImpl userService = new UserServiceImpl();
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            User user = new User(userName, encryptedPassword, firstName, lastName, phoneNumber, eMail);
            if (userService.registerNewUser(user)) {
                registrationStatus = MessageManager.getMessage("msg.registeredSuccess");
                logger.info(MessageManager.getMessage("log.registeredSuccess"));
            } else {
                registrationStatus = MessageManager.getMessage("msg.notRegistered");
                logger.info(MessageManager.getMessage("log.notRegistered"));
            }
        } else {
            registrationStatus = validationStatus.toString();
        }
        request.setAttribute(REQUEST_REGISTRATION_STATUS, registrationStatus);
        return ConfigurationManager.getProperty("page.registerRedirect");
    }

    private boolean validationUserData(String userName, String password, String firstName, String lastName,
                                       String phoneNumber, String eMail, StringBuilder status) {
        UserValidator validator = new UserValidator();
        UserServiceImpl userService = new UserServiceImpl();

        if (!validator.validateUserName(userName)) {
            status.append(MessageManager.getMessage("msg.notValidUsername"));
            return false;
        }
        if (!validator.isValidPassword(password)) {
            status.append(MessageManager.getMessage("msg.notValidPassword"));
            return false;
        }
        if (validator.isValidName(firstName) || validator.isValidName(lastName)) {
            status.append(MessageManager.getMessage("msg.notValidName"));
            return false;
        }
        if (validator.isValidPhone(phoneNumber)) {
            status.append(MessageManager.getMessage("msg.notValidPhone"));
            return false;
        }
        if (validator.isValidEmail(eMail)) {
            status.append(MessageManager.getMessage("msg.notValidEmail"));
            return false;
        }
        if (userService.isUsernameNotFree(userName)) {
            status.append(MessageManager.getMessage("msg.nameNotFree"));
            return false;
        }
        return true;
    }
}