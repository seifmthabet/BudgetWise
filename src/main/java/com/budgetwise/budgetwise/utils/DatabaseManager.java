package com.budgetwise.budgetwise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * DatabaseManager component.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:budgetwise.sqlite";
    private static final String SQLITE_DRIVER = "org.sqlite.JDBC";

    private static DatabaseManager instance;

    private DatabaseManager() {
        try {
            Class.forName(SQLITE_DRIVER);
            initializeSchema();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getInstance operation.
     * @return result value
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * getConnection operation.
     * @return result value
     */
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            return conn;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private void initializeSchema() {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                InputStream is = getClass().getResourceAsStream("/schema.sql")
        ) {

            if (is == null) {
                throw new RuntimeException("Schema file not found");
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            stmt.executeUpdate(sql);

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Schema initialization failed", e);
        }
    }
}
