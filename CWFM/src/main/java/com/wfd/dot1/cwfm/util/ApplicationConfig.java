package com.wfd.dot1.cwfm.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ApplicationConfig {

    private static final Path CONFIG_FILE =
            Paths.get("D:/wfd_cwfm/config/application.properties");

    private ApplicationConfig() {
    }

    public static String getProperty(String key) {

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to load application configuration", e);
        }

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing application property: " + key);
        }

        return value.trim();
    }
}