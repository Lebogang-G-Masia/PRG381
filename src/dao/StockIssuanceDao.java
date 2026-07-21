package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockIssuanceDao {


    public boolean issueMaterial(int cleanerId, int userId, int materialId, int quantity) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();

            conn.setAutoCommit(false);


            // Check stock availability
            String checkStock = 
                    "SELECT quantity FROM materials WHERE material_id=?";


            PreparedStatement stockStmt = conn.prepareStatement(checkStock);

            stockStmt.setInt(1, materialId);

            ResultSet rs = stockStmt.executeQuery();


            if (!rs.next()) {

                System.out.println("Material does not exist.");
                conn.rollback();
                return false;

            }


            int availableStock = rs.getInt("quantity");


            if (availableStock < quantity) {

                System.out.println("Not enough stock available.");
                conn.rollback();
                return false;

            }



            // Create issuance record
            String issuanceSQL =
                    "INSERT INTO stock_issuance (cleaner_id, issued_by) VALUES (?, ?) RETURNING issuance_id";


            PreparedStatement issuanceStmt =
                    conn.prepareStatement(issuanceSQL);


            issuanceStmt.setInt(1, cleanerId);
            issuanceStmt.setInt(2, userId);


            ResultSet issuanceResult =
                    issuanceStmt.executeQuery();


            issuanceResult.next();

            int issuanceId =
                    issuanceResult.getInt("issuance_id");



            // Insert details
            String detailSQL =
                    "INSERT INTO stock_issuance_details (issuance_id, material_id, quantity) VALUES (?, ?, ?)";


            PreparedStatement detailStmt =
                    conn.prepareStatement(detailSQL);


            detailStmt.setInt(1, issuanceId);
            detailStmt.setInt(2, materialId);
            detailStmt.setInt(3, quantity);


            detailStmt.executeUpdate();



            // Reduce stock
            String updateStock =
                    "UPDATE materials SET quantity = quantity - ? WHERE material_id=?";


            PreparedStatement updateStmt =
                    conn.prepareStatement(updateStock);


            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, materialId);


            updateStmt.executeUpdate();



            conn.commit();

            return true;



        } catch (SQLException e) {


            try {

                if (conn != null) {
                    conn.rollback();
                }

            } catch (SQLException rollbackError) {

                System.err.println(rollbackError.getMessage());

            }


            System.err.println(
                    "Error issuing stock: "
                    + e.getMessage()
            );


            return false;


        } finally {


            try {

                if (conn != null) {

                    conn.setAutoCommit(true);
                    conn.close();

                }

            } catch (SQLException e) {

                System.err.println(e.getMessage());

            }

        }

    }

}