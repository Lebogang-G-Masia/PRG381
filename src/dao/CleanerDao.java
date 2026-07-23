package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cleaner;

public class CleanerDao {

    // Get All Cleaners
    public ArrayList<Cleaner> getAllCleaners() {

        ArrayList<Cleaner> cleaners = new ArrayList<>();

        String sql = "SELECT * FROM cleaners ORDER BY first_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Cleaner cleaner = new Cleaner();

                cleaner.setCleanerId(rs.getInt("cleaner_id"));
                cleaner.setFirstName(rs.getString("first_name"));
                cleaner.setLastName(rs.getString("last_name"));
                cleaner.setPhone(rs.getString("phone"));
                cleaner.setEmail(rs.getString("email"));

                cleaners.add(cleaner);
            }

        } catch (SQLException e) {
            System.err.println("Error loading cleaners: " + e.getMessage());
        }

        return cleaners;
    }


    // Add Cleaner
    public boolean addCleaner(Cleaner cleaner) {

        String sql = "INSERT INTO cleaners (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, cleaner.getFirstName());
            pst.setString(2, cleaner.getLastName());
            pst.setString(3, cleaner.getPhone());
            pst.setString(4, cleaner.getEmail());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding cleaner: " + e.getMessage());
            return false;
        }
    }


    // Update Cleaner
    public boolean updateCleaner(Cleaner cleaner) {

        String sql = "UPDATE cleaners SET first_name=?, last_name=?, phone=?, email=? WHERE cleaner_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, cleaner.getFirstName());
            pst.setString(2, cleaner.getLastName());
            pst.setString(3, cleaner.getPhone());
            pst.setString(4, cleaner.getEmail());
            pst.setInt(5, cleaner.getCleanerId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating cleaner: " + e.getMessage());
            return false;
        }
    }


    // Delete Cleaner
    public boolean deleteCleaner(int cleanerId) {

        String sql = "DELETE FROM cleaners WHERE cleaner_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, cleanerId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting cleaner: " + e.getMessage());
            return false;
        }
    }

    // Search Cleaners
    public ArrayList<Cleaner> searchCleaners(String keyword) {
        ArrayList<Cleaner> cleaners = new ArrayList<>();
        String sql = "SELECT * FROM cleaners WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ? ORDER BY first_name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Cleaner cleaner = new Cleaner();
                    cleaner.setCleanerId(rs.getInt("cleaner_id"));
                    cleaner.setFirstName(rs.getString("first_name"));
                    cleaner.setLastName(rs.getString("last_name"));
                    cleaner.setPhone(rs.getString("phone"));
                    cleaner.setEmail(rs.getString("email"));
                    cleaners.add(cleaner);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching cleaners: " + e.getMessage());
        }
        return cleaners;
    }
}