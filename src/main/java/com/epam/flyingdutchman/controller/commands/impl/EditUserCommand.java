package com.epam.flyingdutchman.controller.commands.impl;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.util.PasswordEncryptor;
import com.epam.flyingdutchman.entity.User;
import com.epam.flyingdutchman.exception.ServiceException;
import com.epam.flyingdutchman.model.service.UserService;
import com.epam.flyingdutchman.model.service.impl.UserServiceImpl;
import com.epam.flyingdutchman.model.validation.UserValidator;
import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import com.epam.flyingdutchman.util.resources.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.flyingdutchman.util.constants.Context.*;
/**
 * The class represents command of edit user
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class EditUserCommand implements Command {
    private final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();
    private final UserValidator validator = new UserValidator();

    @Override
    public String execute(HttpServletRequest request) {

        String userNameOld = request.getParameter(REQUEST_USERNAME);
        User user;
        String password = request.getParameter(REQUEST_PASSWORD);
        String firstName = request.getParameter(REQUEST_FIRST_NAME);
        String lastName = request.getParameter(REQUEST_LAST_NAME);
        String phoneNumber = request.getParameter(REQUEST_PHONE);
        String eMail = request.getParameter(REQUEST_EMAIL);
        boolean active = Boolean.parseBoolean(request.getParameter(REQUEST_USER_ACTIVE));
        StringBuilder validationStatus = new StringBuilder();
        if (validationUserData(password, firstName, lastName, phoneNumber, eMail, validationStatus)) {
            try {
                user = userService.findUserByUsername(userNameOld);
                if (!password.equals("")) {
                    String encryptedPassword = PasswordEncryptor.encryptPassword(password);
                    user.setPassword(encryptedPassword);
                }
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhoneNumber(phoneNumber);
                user.seteMail(eMail);
                user.setActive(active);
                if (userService.updateUser(user)) {
                    validationStatus.append(MessageManager.getMessage("msg.updateSuccess"));
                    logger.info("The user updated success");
                } else {
                    validationStatus.append(MessageManager.getMessage("msg.notRegistered"));
                    logger.info("Tne user didn't update success");
                }
            } catch (ServiceException e) {
                logger.error("Mistake in updating", e);
            }
        } else {
            validationStatus.append(MessageManager.getMessage("msg.notRegistered"));
        }
        String registrationStatus = validationStatus.toString();
        request.getSession().setAttribute(REQUEST_REGISTRATION_STATUS, registrationStatus);
        return ConfigurationManager.getProperty("page.editUserRedirect");
    }

    private boolean validationUserData(String password, String firstName, String lastName,
                                       String phoneNumber, String eMail, StringBuilder status) {
        if (!password.equals("") && !validator.isValidPassword(password)) {
            status.append(MessageManager.getMessage("msg.notValidPassword"));
            return false;
        }
        if (!validator.isValidName(firstName) && !validator.isValidName(lastName)) {
            status.append(MessageManager.getMessage("msg.notValidName"));
            return false;
        }
        if (!validator.isValidPhone(phoneNumber)) {
            status.append(MessageManager.getMessage("msg.notValidPhone"));
            return false;
        }
        if (!validator.isValidEmail(eMail)) {
            status.append(MessageManager.getMessage("msg.notValidEmail"));
            return false;
        }
        return true;
    }
}
