package com.step.hryshkin.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author dshargaev
 */

@WebListener
public class ContextInitializer implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextInitializer.class);

    private static final String PATH = "/dataBase/dataBase.properties";
    private static final Properties PROPERTIES = getProperties();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection connection = ConnectCreator.getInit()) {
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ContextInitializer.class.getResourceAsStream(PATH)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return properties;
    }

    public static String getStringProperties(String str) {
        return PROPERTIES.getProperty(str);
    }
}