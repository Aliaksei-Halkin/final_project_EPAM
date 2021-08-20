package com.epam.flyingdutchman.util.resources;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * The class represents ResourceBundle for message values
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class MessageManager {
    protected static final String BUNDLE_NAME = "messages";

    private MessageManager() {
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);


    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public static void setLocale(String language) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language));
    }
}
