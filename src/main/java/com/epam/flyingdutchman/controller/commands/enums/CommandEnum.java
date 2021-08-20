package com.epam.flyingdutchman.controller.commands.enums;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.impl.*;

/**
 * The enum which define all commands in project.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public enum CommandEnum {
    LANGUAGE(new LanguageCommand()),
    HOME(new HomePageCommand()),
    REDIRECT(new RedirectToGitHub()),
    SEARCH(new SearchCommand()),
    ADD_PRODUCT(new AddProductCommand()),
    CART(new CartCommand()),
    REMOVE_PRODUCT(new RemoveProductCommand()),
    MAKE_ORDER(new MakeOrderCommand()),
    REGISTER_USER_PAGE(new RegisterUserPageCommand()),
    REGISTER_USER(new RegisterUserCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    ORDERS(new OrdersCommand()),
    REMOVE_ORDER(new RemoveOrderCommand()),
    PROFILE(new ProfileCommand()),
    PRODUCT_MANAGEMENT(new ProductManagementCommand()),
    ADD_NEW_PRODUCT_PAGE(new AddNewProductPageCommand()),
    ADD_NEW_PRODUCT(new AddNewProductCommand()),
    DELETE_PRODUCT(new DeleteProductCommand()),
    EDIT_PRODUCT_PAGE(new EditProductPageCommand()),
    ORDER_MANAGEMENT(new OrderManagementCommand()),
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand()),
    EDIT_PRODUCT(new EditProductCommand()),
    USER_MANAGEMENT(new UserManagementCommand()),
    DELETE_USER(new UserDeleteCommand()),
    CHANGE_USER(new ChangeUserRoleCommand()),
    EDIT_USER_PAGE(new EditUserPageCommand()),
    EDIT_USER(new EditUserCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
