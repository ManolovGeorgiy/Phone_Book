package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties properties;

    public Config() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config.properties", e);
        }
    }

    public String getProjectUrl() {
        return properties.getProperty("project.url");
    }

    public String getSelenoidUrl() {
        return properties.getProperty("selenoid.url");
    }

    public Boolean getSelenoidState() {
        return Boolean.parseBoolean(properties.getProperty("selenoid.state"));
    }

    public Integer getWindowWight() {
        return Integer.parseInt(properties.getProperty("window.width"));
    }

    public Integer getWindowHeight() {
        return Integer.parseInt(properties.getProperty("window.height"));
    }
}
