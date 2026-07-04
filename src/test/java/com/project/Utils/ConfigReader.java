package com.project.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    private static final String CONFIG_PATH =
            "src/test/resources/config.properties";

    /**
     * Loads the properties file only once
     */
    private static void loadProperties() {

        if (properties == null) {

            properties = new Properties();

            try (FileInputStream input =
                         new FileInputStream(CONFIG_PATH)) {

                properties.load(input);

                System.out.println("config.properties loaded successfully.");

            } catch (IOException e) {

                throw new RuntimeException(
                        "Failed to load config.properties : "
                                + e.getMessage());
            }
        }
    }

    /**
     * Fetches value against a given key
     */
    public static String getProperty(String key) {

        loadProperties();

        String value = properties.getProperty(key);

        if (value == null) {
            throw new RuntimeException(
                    "Property '" + key + "' not found in config.properties");
        }

        return value.trim();
    }

    // Convenience getters
    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getHomePageUrl() {
        return getProperty("homepage.url");
    }

    public static String getStudyChairsUrl() {
        return getProperty("studychairs.url");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }
}