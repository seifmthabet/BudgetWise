package com.budgetwise.budgetwise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:budgetwise.sqlite";
    private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            Class.forName(SQLITE_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            enableForeignKeys();
            initializeSchema();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "SQLite JDBC driver not found. Make sure the SQLite JDBC dependency is included in the project.",
                    e
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(SQLITE_DRIVER);
                connection = DriverManager.getConnection(DB_URL);
                enableForeignKeys();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "SQLite JDBC driver not found. Make sure the SQLite JDBC dependency is included in the project.",
                    e
            );
        } catch (SQLException e) {
            throw new RuntimeException("Database connection lost", e);
        }
        return connection;
    }

    private void enableForeignKeys() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
    }

    private void initializeSchema() {
        try (InputStream is = getClass().getResourceAsStream("/schema.sql")) {
            if (is == null) {
                throw new RuntimeException("Schema file not found");
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            for (String statement : sql.split(";")) {
                String trimmedStatement = statement.trim();
                if (!trimmedStatement.isEmpty()) {
                    try (Statement stmt = connection.createStatement()) {
                        stmt.execute(trimmedStatement);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Schema initialization failed", e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
