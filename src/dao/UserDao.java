package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class UserDao {

    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));

                return user;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return null;

    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getUsername());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getPassword());
            pst.setString(6, user.getRole() != null && !user.getRole().isEmpty() ? user.getRole() : "Staff");
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateProfile(User user) {
        String sql = "UPDATE users SET first_name=?, last_name=?, email=? WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setInt(4, user.getUserId());
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // First check if old password matches
        String checkSql = "SELECT password FROM users WHERE user_id = ?";
        String updateSql = "UPDATE users SET password = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement checkPst = conn.prepareStatement(checkSql)) {
                checkPst.setInt(1, userId);
                try (ResultSet rs = checkPst.executeQuery()) {
                    if (rs.next()) {
                        if (!rs.getString("password").equals(oldPassword)) {
                            return false; // Old password doesn't match
                        }
                    } else {
                        return false; // User not found
                    }
                }
            }
            
            try (PreparedStatement updatePst = conn.prepareStatement(updateSql)) {
                updatePst.setString(1, newPassword);
                updatePst.setInt(2, userId);
                return updatePst.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}