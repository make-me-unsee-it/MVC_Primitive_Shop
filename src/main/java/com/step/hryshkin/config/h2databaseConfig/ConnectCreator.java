package com.step.hryshkin.config.h2databaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author dshargaev
 */

public final class ConnectCreator {
    private static final String URL = ContextInitializer.getStringProperties("db.url");
    private static final String PASSWORD = ContextInitializer.getStringProperties("db.password");
    private static final String USER = ContextInitializer.getStringProperties("db.user");
    private static final String INIT_URL = ContextInitializer.getStringProperties("db.init");

    private ConnectCreator() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getInit() throws SQLException {
        return DriverManager.getConnection(INIT_URL, USER, PASSWORD);
    }
}
