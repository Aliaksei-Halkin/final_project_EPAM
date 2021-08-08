package com.epam.flyingdutchman.controller.commands.enums;

import com.epam.flyingdutchman.controller.commands.Command;
import com.epam.flyingdutchman.controller.commands.impl.*;

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
    DELETE_PRODUCT(new DeleteProductCommand())

    ;

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
