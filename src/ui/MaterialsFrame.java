package ui;

import dao.MaterialDao;
import model.Material;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MaterialsFrame extends JFrame {

    // DAO Instance
    private final MaterialDao materialDao;

    // UI Components
    private JTable tableMaterials;
    private DefaultTableModel tableModel;

    private JTextField txtMaterialId; // Hidden or Read-only for tracking selection
    private JTextField txtMaterialName;
    private JTextField txtDescription;
    private JTextField txtQuantity;
    private JTextField txtReorderLevel;
    private JTextField txtUnit;
    private JTextField txtSupplierId;
    private JTextField txtSearch;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;
    private JButton btnRefresh;

    public MaterialsFrame() {
        this.materialDao = new MaterialDao();
        
        initComponents();
        initTable();
        loadMaterials();
        checkLowStockAlert();
    }

    private void initComponents() {
        setTitle("Materials Management - Cleaning Inventory System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Doesn't terminate main app when closed
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: Search & Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search Material:"));
        txtSearch = new JTextField(20);
        topPanel.add(txtSearch);

        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh Table");
        topPanel.add(btnSearch);
        topPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: Table ---
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Description", "Quantity", "Reorder Level", "Unit", "Supplier ID"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent direct cell editing
            }
        };

        tableMaterials = new JTable(tableModel);
        tableMaterials.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Populate inputs on row selection
        tableMaterials.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelectedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableMaterials);
        add(scrollPane, BorderLayout.CENTER);

        // --- SOUTH PANEL: Input Form & Buttons ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        // Use GridBagLayout for a clean 2-column label + field alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Material Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaterialId = new JTextField();
        txtMaterialId.setVisible(false); // ID is managed internally

        txtMaterialName = new JTextField(15);
        txtDescription = new JTextField(15);
        txtQuantity = new JTextField(15);
        txtReorderLevel = new JTextField(15);
        txtUnit = new JTextField(15);
        txtSupplierId = new JTextField(15);

        // ROW 0: Material Name & Unit
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Material Name:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtMaterialName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Unit (e.g., Bottle, Each, Pack):*"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtUnit, gbc);

        // ROW 1: Description & Supplier ID
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtDescription, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Supplier ID:*"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtSupplierId, gbc);

        // ROW 2: Quantity & Reorder Level
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Quantity:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtQuantity, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Reorder Level:*"), gbc);
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtReorderLevel, gbc);

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        // Button Bar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAdd = new JButton("Add Material");
        btnUpdate = new JButton("Update Selected");
        btnDelete = new JButton("Delete Selected");
        btnClear = new JButton("Clear Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- ACTION LISTENERS ---
        btnAdd.addActionListener(e -> addMaterial());
        btnUpdate.addActionListener(e -> updateMaterial());
        btnDelete.addActionListener(e -> deleteMaterial());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> searchMaterials());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadMaterials();
        });
    }
    // --- TABLE DATA MANAGEMENT ---

    private void initTable() {
        // Custom Renderer to highlight Low-Stock items in soft red
        tableMaterials.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    try {
                        int qty = Integer.parseInt(table.getValueAt(row, 3).toString());
                        int reorder = Integer.parseInt(table.getValueAt(row, 4).toString());

                        if (qty <= reorder) {
                            c.setBackground(new Color(255, 200, 200)); // Highlight red for low stock
                            c.setForeground(Color.BLACK);
                        } else {
                            c.setBackground(Color.WHITE);
                            c.setForeground(Color.BLACK);
                        }
                    } catch (Exception ex) {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });
    }

    private void loadMaterials() {
        List<Material> materials = materialDao.getAllMaterials();
        populateTable(materials);
    }

    private void populateTable(List<Material> materials) {
        tableModel.setRowCount(0);
        for (Material m : materials) {
            tableModel.addRow(new Object[]{
                m.getMaterialId(),
                m.getMaterialName(),
                m.getDescription(),
                m.getQuantity(),
                m.getReorderLevel(),
                m.getUnit(),
                m.getSupplierId()
            });
        }
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = tableMaterials.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaterialId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtMaterialName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtDescription.setText(tableModel.getValueAt(selectedRow, 2) != null ? tableModel.getValueAt(selectedRow, 2).toString() : "");
            txtQuantity.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtReorderLevel.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtUnit.setText(tableModel.getValueAt(selectedRow, 5).toString());
            txtSupplierId.setText(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }

    // --- CRUD ACTIONS ---

    private void addMaterial() {
        if (!validateInputs()) return;

        try {
            Material material = extractMaterialFromForm();
            materialDao.addMaterial(material);
            
            JOptionPane.showMessageDialog(this, "Material added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadMaterials();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding material: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMaterial() {
        if (txtMaterialId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a material from the table to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInputs()) return;

        try {
            Material material = extractMaterialFromForm();
            material.setMaterialId(Integer.parseInt(txtMaterialId.getText().trim()));
            
            materialDao.updateMaterial(material);
            
            JOptionPane.showMessageDialog(this, "Material updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadMaterials();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating material: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMaterial() {
        int selectedRow = tableMaterials.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a material from the table to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int materialId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String materialName = tableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete material: " + materialName + " (ID: " + materialId + ")?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                materialDao.deleteMaterial(materialId);
                JOptionPane.showMessageDialog(this, "Material deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadMaterials();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting material: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchMaterials() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadMaterials();
            return;
        }

        List<Material> results = materialDao.searchMaterials(keyword);
        populateTable(results);
    }

    // --- VALIDATION & BUSINESS LOGIC ---

    private boolean validateInputs() {
        String name = txtMaterialName.getText().trim();
        String unit = txtUnit.getText().trim();
        String qtyStr = txtQuantity.getText().trim();
        String reorderStr = txtReorderLevel.getText().trim();
        String supplierStr = txtSupplierId.getText().trim();

        // 1. Required Field Checks
        if (name.isEmpty() || unit.isEmpty() || qtyStr.isEmpty() || reorderStr.isEmpty() || supplierStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (*).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 2. Numeric Parsing Checks
        int quantity;
        int reorderLevel;
        int supplierId;

        try {
            quantity = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid whole number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            reorderLevel = Integer.parseInt(reorderStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Reorder Level must be a valid whole number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            supplierId = Integer.parseInt(supplierStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Supplier ID must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 3. Non-Negative Value Checks
        if (quantity < 0) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (reorderLevel < 0) {
            JOptionPane.showMessageDialog(this, "Reorder level cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (supplierId <= 0) {
            JOptionPane.showMessageDialog(this, "Supplier ID must be a positive integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Material extractMaterialFromForm() {
        Material material = new Material();
        material.setMaterialName(txtMaterialName.getText().trim());
        material.setDescription(txtDescription.getText().trim());
        material.setQuantity(Integer.parseInt(txtQuantity.getText().trim()));
        material.setReorderLevel(Integer.parseInt(txtReorderLevel.getText().trim()));
        material.setUnit(txtUnit.getText().trim());
        material.setSupplierId(Integer.parseInt(txtSupplierId.getText().trim()));
        return material;
    }

    private void checkLowStockAlert() {
        List<Material> lowStock = materialDao.getLowStockMaterials();
        if (lowStock != null && !lowStock.isEmpty()) {
            StringBuilder warningMessage = new StringBuilder("⚠️ LOW STOCK WARNING!\nThe following materials need reordering:\n\n");
            for (Material m : lowStock) {
                warningMessage.append("• ").append(m.getMaterialName())
                              .append(" (Current: ").append(m.getQuantity())
                              .append(" | Reorder at: ").append(m.getReorderLevel())
                              .append(" ").append(m.getUnit()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, warningMessage.toString(), "Stock Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearForm() {
        txtMaterialId.setText("");
        txtMaterialName.setText("");
        txtDescription.setText("");
        txtQuantity.setText("");
        txtReorderLevel.setText("");
        txtUnit.setText("");
        txtSupplierId.setText("");
        tableMaterials.clearSelection();
    }
public static void main(String[] args) {
        // Set Nimbus Look and Feel for clean modern Swing UI
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MaterialsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Launch the frame on the Swing Event Dispatch Thread
        java.awt.EventQueue.invokeLater(() -> {
            new MaterialsFrame().setVisible(true);
        });
    }
}
