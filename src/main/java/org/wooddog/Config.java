package org.wooddog;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Config {
    DB_DRIVER,
    DB_URL,
    DB_USERNAME,
    DB_PASSWORD,
    DB_SCHEMA_DEFINITION,
    FEEDANALYSER_SCORE_RATOR_IMPL;

    private static final Properties VALUES = new Properties();

    public static String get(Config key) {
        return VALUES.getProperty(key.name());
    }

    public static void set(Config key, String value) {
        VALUES.setProperty(key.name(), value);
    }

    public static Properties getProperties() {
        return VALUES;
    }

    public static void load(String resource) {
        InputStream stream;

        stream = Config.class.getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new RuntimeException("resource " + resource + " not found");
        }

        try {
            VALUES.load(stream);
        } catch (IOException x) {
            throw new RuntimeException("failed loading resource " + resource);
        }

        set(DB_DRIVER, VALUES.getProperty("database.driver"));
        set(DB_URL, VALUES.getProperty("database.url"));
        set(DB_USERNAME, VALUES.getProperty("database.username"));
        set(DB_PASSWORD, VALUES.getProperty("database.password"));
        set(DB_SCHEMA_DEFINITION, VALUES.getProperty("database.schema-definition"));
        set(FEEDANALYSER_SCORE_RATOR_IMPL, VALUES.getProperty("feedanalyser.score-rator-impl"));
    }
}
