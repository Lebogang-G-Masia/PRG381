package database;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {
        System.out.println("Testing PostgreSQL Database Connection...");

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception caught:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General Exception caught:");
            e.printStackTrace();
        }
    }
}