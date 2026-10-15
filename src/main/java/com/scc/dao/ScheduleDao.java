
package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScheduleDao {

    public void addSchedule(
            String code,
            String subject,
            String instructor,
            String day,
            String start,
            String end,
            String room,
            String section) {

        String sql = """
            INSERT INTO class_schedules
            (subject_code, subject_name, instructor, day,
             start_time, end_time, room, section)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            pstmt.setString(2, subject);
            pstmt.setString(3, instructor);
            pstmt.setString(4, day);
            pstmt.setString(5, start);
            pstmt.setString(6, end);
            pstmt.setString(7, room);
            pstmt.setString(8, section);

            pstmt.executeUpdate();
            System.out.println("Class schedule added successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displaySchedules() {

        String sql = """
            SELECT * FROM class_schedules
            ORDER BY
                CASE day
                    WHEN 'Monday' THEN 1
                    WHEN 'Tuesday' THEN 2
                    WHEN 'Wednesday' THEN 3
                    WHEN 'Thursday' THEN 4
                    WHEN 'Friday' THEN 5
                    WHEN 'Saturday' THEN 6
                    WHEN 'Sunday' THEN 7
                    ELSE 8
                END,
                start_time
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n================ CLASS SCHEDULES ================");
            System.out.printf(
                    "%-5s | %-10s | %-22s | %-18s | %-10s | %-13s | %-10s | %s%n",
                    "ID", "Code", "Subject", "Instructor",
                    "Day", "Time", "Room", "Section");

            System.out.println("-".repeat(120));

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.printf(
                        "%-5d | %-10s | %-22s | %-18s | %-10s | %-13s | %-10s | %s%n",
                        rs.getInt("schedule_id"),
                        rs.getString("subject_code"),
                        rs.getString("subject_name"),
                        rs.getString("instructor"),
                        rs.getString("day"),
                        rs.getString("start_time") + "-" +
                                rs.getString("end_time"),
                        rs.getString("room"),
                        rs.getString("section")
                );
            }

            if (!found) {
                System.out.println("No class schedules available.");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying schedules: "
                    + e.getMessage());
        }
    }
}
