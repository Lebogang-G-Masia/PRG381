package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Supplier;

public class SupplierDao {

    // Get All Suppliers
    public ArrayList<Supplier> getAllSuppliers() {

        ArrayList<Supplier> suppliers = new ArrayList<>();

        String sql = "SELECT * FROM suppliers ORDER BY company_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Supplier supplier = new Supplier();

                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setSupplierName(rs.getString("company_name"));
                supplier.setContactPerson(rs.getString("contact_person"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setAddress(rs.getString("address"));

                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            System.err.println("Error loading suppliers: " + e.getMessage());
        }

        return suppliers;
    }

    // Add Supplier
    public boolean addSupplier(Supplier supplier) {

        String sql = "INSERT INTO suppliers (company_name, contact_person, phone, email, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getContactPerson());
            pst.setString(3, supplier.getPhone());
            pst.setString(4, supplier.getEmail());
            pst.setString(5, supplier.getAddress());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding supplier: " + e.getMessage());
            return false;
        }
    }

    // Update Supplier
    public boolean updateSupplier(Supplier supplier) {

        String sql = "UPDATE suppliers SET company_name=?, contact_person=?, phone=?, email=?, address=? WHERE supplier_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getContactPerson());
            pst.setString(3, supplier.getPhone());
            pst.setString(4, supplier.getEmail());
            pst.setString(5, supplier.getAddress());
            pst.setInt(6, supplier.getSupplierId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating supplier: " + e.getMessage());
            return false;
        }
    }

    // Delete Supplier
    public boolean deleteSupplier(int supplierId) {

        String sql = "DELETE FROM suppliers WHERE supplier_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, supplierId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting supplier: " + e.getMessage());
            return false;
        }
    }
}