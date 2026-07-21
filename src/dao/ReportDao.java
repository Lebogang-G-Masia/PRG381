package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportDao {


    // Inventory Report
    public ArrayList<String[]> getInventoryReport() {

        ArrayList<String[]> report = new ArrayList<>();

        String sql =
                "SELECT m.material_id, " +
                "m.material_name, " +
                "m.quantity, " +
                "m.reorder_level, " +
                "s.company_name " +
                "FROM materials m " +
                "LEFT JOIN suppliers s " +
                "ON m.supplier_id = s.supplier_id " +
                "ORDER BY m.material_name";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {

                String[] row = {

                    String.valueOf(rs.getInt("material_id")),
                    rs.getString("material_name"),
                    String.valueOf(rs.getInt("quantity")),
                    String.valueOf(rs.getInt("reorder_level")),
                    rs.getString("company_name")

                };

                report.add(row);

            }


        } catch (SQLException e) {

            System.err.println(
                    "Error generating inventory report: "
                    + e.getMessage()
            );

        }


        return report;

    }



    // Low Stock Report
    public ArrayList<String[]> getLowStockReport() {


        ArrayList<String[]> report = new ArrayList<>();


        String sql =
                "SELECT material_id, material_name, quantity, reorder_level " +
                "FROM materials " +
                "WHERE quantity <= reorder_level " +
                "ORDER BY quantity";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {

                String[] row = {

                    String.valueOf(rs.getInt("material_id")),
                    rs.getString("material_name"),
                    String.valueOf(rs.getInt("quantity")),
                    String.valueOf(rs.getInt("reorder_level"))

                };


                report.add(row);

            }


        } catch (SQLException e) {

            System.err.println(
                    "Error generating low stock report: "
                    + e.getMessage()
            );

        }


        return report;

    }



    // Issuance History Report
    public ArrayList<String[]> getIssuanceHistory() {


        ArrayList<String[]> report = new ArrayList<>();


        String sql =
                "SELECT si.issuance_id, " +
                "c.first_name, " +
                "c.last_name, " +
                "si.issue_date, " +
                "m.material_name, " +
                "sid.quantity " +
                "FROM stock_issuance si " +
                "JOIN cleaners c ON si.cleaner_id = c.cleaner_id " +
                "JOIN stock_issuance_details sid ON si.issuance_id = sid.issuance_id " +
                "JOIN materials m ON sid.material_id = m.material_id " +
                "ORDER BY si.issue_date DESC";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {


                String[] row = {

                    String.valueOf(rs.getInt("issuance_id")),
                    rs.getString("first_name") + " " +
                    rs.getString("last_name"),
                    rs.getString("material_name"),
                    String.valueOf(rs.getInt("quantity")),
                    rs.getString("issue_date")

                };


                report.add(row);

            }


        } catch (SQLException e) {

            System.err.println(
                    "Error generating issuance report: "
                    + e.getMessage()
            );

        }


        return report;

    }




    // Material Usage Report
    public ArrayList<String[]> getMaterialUsageReport() {


        ArrayList<String[]> report = new ArrayList<>();


        String sql =
                "SELECT m.material_name, " +
                "SUM(sid.quantity) AS total_used " +
                "FROM stock_issuance_details sid " +
                "JOIN materials m " +
                "ON sid.material_id = m.material_id " +
                "GROUP BY m.material_name " +
                "ORDER BY total_used DESC";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {


                String[] row = {

                    rs.getString("material_name"),
                    String.valueOf(rs.getInt("total_used"))

                };


                report.add(row);

            }


        } catch (SQLException e) {

            System.err.println(
                    "Error generating usage report: "
                    + e.getMessage()
            );

        }


        return report;

    }

}