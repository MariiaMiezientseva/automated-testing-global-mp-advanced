package com.epam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyHolderService {
    public static final Logger LOGGER = LogManager.getLogger(PropertyHolderService.class);
    private static final String FILE = "reportportal.properties";
    private final Properties properties;

    public PropertyHolderService() {
        this.properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(FILE);
        try {
            properties.load(stream);
        } catch (IOException e) {
            LOGGER.error("Reading value from resource failed!", e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getOrSystem(String key, String systemPropertyName) {
        String value = get(key);
        if (Strings.isEmpty(value)) {
            return System.getenv(systemPropertyName);
        } else {
            return value;
        }
    }
}
