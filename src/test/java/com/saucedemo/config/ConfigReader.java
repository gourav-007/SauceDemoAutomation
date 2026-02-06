package com.saucedemo.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Application
    public String getAppUrl() {
        return getProperty("app.url");
    }

    public String getAppTitle() {
        return getProperty("app.title");
    }

    // Browser
    public String getBrowser() {
        return System.getProperty("browser", getProperty("browser", "chrome"));
    }

    public boolean isHeadless() {
        String headlessProperty = System.getProperty("headless");
        if (headlessProperty != null) {
            return Boolean.parseBoolean(headlessProperty);
        }
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    // Users
    public String getStandardUsername() {
        return getProperty("user.standard.username");
    }

    public String getStandardPassword() {
        return getProperty("user.standard.password");
    }

    public String getLockedUsername() {
        return getProperty("user.locked.username");
    }

    public String getLockedPassword() {
        return getProperty("user.locked.password");
    }

    public String getProblemUsername() {
        return getProperty("user.problem.username");
    }

    public String getProblemPassword() {
        return getProperty("user.problem.password");
    }

    // Screenshots
    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public String getScreenshotDirectory() {
        return getProperty("screenshot.directory", "target/screenshots");
    }
}
