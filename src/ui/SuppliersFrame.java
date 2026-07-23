package ui;

import dao.SupplierDao;
import model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SuppliersFrame extends JFrame {

    // DAO Instance
    private final SupplierDao supplierDao;

    // UI Components
    private JTable tableSuppliers;
    private DefaultTableModel tableModel;

    private JTextField txtSupplierId; // Hidden or Read-only for tracking selection
    private JTextField txtCompanyName;
    private JTextField txtContactPerson;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;
    private JTextField txtSearch;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;
    private JButton btnRefresh;

    public SuppliersFrame() {
        this.supplierDao = new SupplierDao();
        
        initComponents();
        initTable();
        loadSuppliers();
    }

    private void initComponents() {
        setTitle("Suppliers Management - Cleaning Inventory System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Doesn't terminate main app when closed
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: Search & Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search Supplier:"));
        txtSearch = new JTextField(20);
        topPanel.add(txtSearch);

        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh Table");
        topPanel.add(btnSearch);
        topPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: Table ---
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Company Name", "Contact Person", "Phone", "Email", "Address"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent direct cell editing
            }
        };

        tableSuppliers = new JTable(tableModel);
        tableSuppliers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Populate inputs on row selection
        tableSuppliers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelectedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableSuppliers);
        add(scrollPane, BorderLayout.CENTER);

        // --- SOUTH PANEL: Input Form & Buttons ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        // Use GridBagLayout for a clean 2-column label + field alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Supplier Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtSupplierId = new JTextField();
        txtSupplierId.setVisible(false); // ID is managed internally

        txtCompanyName = new JTextField(15);
        txtContactPerson = new JTextField(15);
        txtPhone = new JTextField(15);
        txtEmail = new JTextField(15);
        txtAddress = new JTextField(15);

        // ROW 0: Company Name & Contact Person
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Company Name:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtCompanyName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Contact Person:*"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtContactPerson, gbc);

        // ROW 1: Phone & Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Phone:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtPhone, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Email:*"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtEmail, gbc);

        // ROW 2: Address
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 3; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtAddress, gbc);
        gbc.gridwidth = 1; // Reset

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        // Button Bar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAdd = new JButton("Add Supplier");
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
        btnAdd.addActionListener(e -> addSupplier());
        btnUpdate.addActionListener(e -> updateSupplier());
        btnDelete.addActionListener(e -> deleteSupplier());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> searchSuppliers());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadSuppliers();
        });
    }

    // --- TABLE DATA MANAGEMENT ---
    private void initTable() {
        tableSuppliers.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    private void loadSuppliers() {
        List<Supplier> suppliers = supplierDao.getAllSuppliers();
        populateTable(suppliers);
    }

    private void populateTable(List<Supplier> suppliers) {
        tableModel.setRowCount(0);
        for (Supplier s : suppliers) {
            tableModel.addRow(new Object[]{
                s.getSupplierId(),
                s.getSupplierName(),
                s.getContactPerson(),
                s.getPhone(),
                s.getEmail(),
                s.getAddress()
            });
        }
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = tableSuppliers.getSelectedRow();
        if (selectedRow >= 0) {
            txtSupplierId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtCompanyName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtContactPerson.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhone.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtAddress.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
        }
    }

    // --- CRUD ACTIONS ---
    private void addSupplier() {
        if (!validateInputs()) return;

        try {
            Supplier supplier = extractSupplierFromForm();
            supplierDao.addSupplier(supplier);
            
            JOptionPane.showMessageDialog(this, "Supplier added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadSuppliers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding supplier: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSupplier() {
        if (txtSupplierId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a supplier from the table to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInputs()) return;

        try {
            Supplier supplier = extractSupplierFromForm();
            supplier.setSupplierId(Integer.parseInt(txtSupplierId.getText().trim()));
            
            supplierDao.updateSupplier(supplier);
            
            JOptionPane.showMessageDialog(this, "Supplier updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadSuppliers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating supplier: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSupplier() {
        int selectedRow = tableSuppliers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a supplier from the table to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int supplierId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String name = tableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete supplier: " + name + " (ID: " + supplierId + ")?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                supplierDao.deleteSupplier(supplierId);
                JOptionPane.showMessageDialog(this, "Supplier deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadSuppliers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting supplier: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchSuppliers() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadSuppliers();
            return;
        }

        List<Supplier> results = supplierDao.searchSuppliers(keyword);
        populateTable(results);
    }

    // --- VALIDATION & BUSINESS LOGIC ---
    private boolean validateInputs() {
        String companyName = txtCompanyName.getText().trim();
        String contactPerson = txtContactPerson.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        // Required Field Checks
        if (companyName.isEmpty() || contactPerson.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (*).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Supplier extractSupplierFromForm() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(txtCompanyName.getText().trim());
        supplier.setContactPerson(txtContactPerson.getText().trim());
        supplier.setPhone(txtPhone.getText().trim());
        supplier.setEmail(txtEmail.getText().trim());
        supplier.setAddress(txtAddress.getText().trim());
        return supplier;
    }

    private void clearForm() {
        txtSupplierId.setText("");
        txtCompanyName.setText("");
        txtContactPerson.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        tableSuppliers.clearSelection();
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SuppliersFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new SuppliersFrame().setVisible(true);
        });
    }
}
