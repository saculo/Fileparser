package com.saculo.fileparser.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sampledb?serverTimezone=UTC";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName(DATABASE_DRIVER).newInstance();
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

            return connection;
        }
        catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException exc) {
            throw new RuntimeException(exc);
        }
    }
}
