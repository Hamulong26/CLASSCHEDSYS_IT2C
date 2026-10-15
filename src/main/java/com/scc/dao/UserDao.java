
package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public boolean registerUser(
            String username, String password, String role) {

        String sql = """
            INSERT INTO users(username, password, roles, status)
            VALUES(?, ?, ?, 'ACTIVE')
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.toUpperCase());

            pstmt.executeUpdate();

            System.out.println("Registration successful!");
            return true;

        } catch (SQLException e) {
            System.out.println(
                    "Registration failed: " + e.getMessage());
            return false;
        }
    }

    public String loginUser(String username, String password) {

        String sql = """
            SELECT roles, status FROM users
            WHERE username = ? AND password = ?
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    if (!"ACTIVE".equalsIgnoreCase(
                            rs.getString("status"))) {
                        System.out.println(
                                "Your account is not active.");
                        return null;
                    }

                    return rs.getString("roles");
                }
            }

        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
        return null;
    }
}
