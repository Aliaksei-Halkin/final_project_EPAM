package com.epam.flyingdutchman.controller.filters;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class RestrictedActions {
    private static final Set<String> ADMIN_ONLY_ACTIONS = new TreeSet<>(Arrays.asList(
            "add_new_product_page", "edit_product_page", "product_management", "edit_user", "edit_product"
            , "add_new_product", "delete_product", "user_management", "delete_user", "change_user", "edit_user_page"
    ));
    private static final Set<String> MANAGER_ONLY_ACTIONS = new TreeSet<>(Arrays.asList(
            "order_management", "change_order_status"
    ));
    private static final Set<String> COOK_ONLY_ACTIONS = new TreeSet<>(Arrays.asList(
            "order_management", "change_order_status"
    ));
    private static final Set<String> AUTHENTICATED_USER_ONLY_ACTIONS = new TreeSet<>();

    static {
        AUTHENTICATED_USER_ONLY_ACTIONS.addAll(Arrays.asList(
                "orders",
                "remove_order",
                "profile"));
        AUTHENTICATED_USER_ONLY_ACTIONS.addAll(ADMIN_ONLY_ACTIONS);
        AUTHENTICATED_USER_ONLY_ACTIONS.addAll(MANAGER_ONLY_ACTIONS);
        AUTHENTICATED_USER_ONLY_ACTIONS.addAll(COOK_ONLY_ACTIONS);
    }

    private RestrictedActions() {
    }

    public static Set<String> getAdminOnlyActions() {
        return ADMIN_ONLY_ACTIONS;
    }

    public static Set<String> getManagerOnlyActions() {
        return MANAGER_ONLY_ACTIONS;
    }

    public static Set<String> getCookOnlyActions() {
        return COOK_ONLY_ACTIONS;
    }

    public static Set<String> getAuthenticatedUserOnlyActions() {
        return AUTHENTICATED_USER_ONLY_ACTIONS;
    }
}
