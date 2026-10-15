
package com.scc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    private static final String DB_URL =
            "jdbc:sqlite:ClassScheduleDatabase.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        initializeDatabase(conn);
        return conn;
    }

    private static void initializeDatabase(Connection conn)
            throws SQLException {

        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    roles TEXT NOT NULL,
                    status TEXT NOT NULL DEFAULT 'PENDING'
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS class_schedules (
                    schedule_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    subject_code TEXT NOT NULL,
                    subject_name TEXT NOT NULL,
                    instructor TEXT NOT NULL,
                    day TEXT NOT NULL,
                    start_time TEXT NOT NULL,
                    end_time TEXT NOT NULL,
                    room TEXT NOT NULL,
                    section TEXT NOT NULL
                )
            """);
        }
    }
}
