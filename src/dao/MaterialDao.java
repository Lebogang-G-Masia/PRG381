package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Material;

public class MaterialDao {

    // Get All Materials
    public ArrayList<Material> getAllMaterials() {

        ArrayList<Material> materials = new ArrayList<>();

        String sql = "SELECT * FROM materials ORDER BY material_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Material material = new Material();

                material.setMaterialId(rs.getInt("material_id"));
                material.setMaterialName(rs.getString("material_name"));
                material.setDescription(rs.getString("description"));
                material.setQuantity(rs.getInt("quantity"));
                material.setReorderLevel(rs.getInt("reorder_level"));
                material.setUnit(rs.getString("unit"));
                material.setSupplierId(rs.getInt("supplier_id"));

                materials.add(material);
            }

        } catch (SQLException e) {
            System.err.println("Error loading materials: " + e.getMessage());
        }

        return materials;
    }

    // Add Material
    public boolean addMaterial(Material material) {

        String sql = "INSERT INTO materials (material_name, description, quantity, reorder_level, unit, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, material.getMaterialName());
            pst.setString(2, material.getDescription());
            pst.setInt(3, material.getQuantity());
            pst.setInt(4, material.getReorderLevel());
            pst.setString(5, material.getUnit());
            pst.setInt(6, material.getSupplierId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding material: " + e.getMessage());
            return false;
        }
    }

    // Update Material
    public boolean updateMaterial(Material material) {

        String sql = "UPDATE materials SET material_name=?, description=?, quantity=?, reorder_level=?, unit=?, supplier_id=? WHERE material_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, material.getMaterialName());
            pst.setString(2, material.getDescription());
            pst.setInt(3, material.getQuantity());
            pst.setInt(4, material.getReorderLevel());
            pst.setString(5, material.getUnit());
            pst.setInt(6, material.getSupplierId());
            pst.setInt(7, material.getMaterialId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating material: " + e.getMessage());
            return false;
        }
    }

    // Delete Material
    public boolean deleteMaterial(int materialId) {

        String sql = "DELETE FROM materials WHERE material_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, materialId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting material: " + e.getMessage());
            return false;
        }
    }

    // Search Materials
    public ArrayList<Material> searchMaterials(String keyword) {

        ArrayList<Material> materials = new ArrayList<>();

        String sql = "SELECT * FROM materials WHERE material_name ILIKE ? ORDER BY material_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Material material = new Material();

                material.setMaterialId(rs.getInt("material_id"));
                material.setMaterialName(rs.getString("material_name"));
                material.setDescription(rs.getString("description"));
                material.setQuantity(rs.getInt("quantity"));
                material.setReorderLevel(rs.getInt("reorder_level"));
                material.setUnit(rs.getString("unit"));
                material.setSupplierId(rs.getInt("supplier_id"));

                materials.add(material);
            }

        } catch (SQLException e) {
            System.err.println("Error searching materials: " + e.getMessage());
        }

        return materials;
    }

    // Low Stock Materials
    public ArrayList<Material> getLowStockMaterials() {

        ArrayList<Material> materials = new ArrayList<>();

        String sql = "SELECT * FROM materials WHERE quantity <= reorder_level ORDER BY material_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Material material = new Material();

                material.setMaterialId(rs.getInt("material_id"));
                material.setMaterialName(rs.getString("material_name"));
                material.setDescription(rs.getString("description"));
                material.setQuantity(rs.getInt("quantity"));
                material.setReorderLevel(rs.getInt("reorder_level"));
                material.setUnit(rs.getString("unit"));
                material.setSupplierId(rs.getInt("supplier_id"));

                materials.add(material);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving low stock materials: " + e.getMessage());
        }

        return materials;
    }
}