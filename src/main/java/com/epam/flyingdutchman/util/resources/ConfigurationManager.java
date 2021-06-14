package com.epam.flyingdutchman.util.resources;

import java.util.ResourceBundle;

public class ConfigurationManager {
    protected static final String BUNDLE_NAME = "config";

    private ConfigurationManager() {
    }

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
