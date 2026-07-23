package ui;

import dao.CleanerDao;
import model.Cleaner;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CleanersFrame extends JFrame {

    // DAO Instance
    private final CleanerDao cleanerDao;

    // UI Components
    private JTable tableCleaners;
    private DefaultTableModel tableModel;

    private JTextField txtCleanerId; // Hidden or Read-only for tracking selection
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtSearch;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;
    private JButton btnRefresh;

    public CleanersFrame() {
        this.cleanerDao = new CleanerDao();
        
        initComponents();
        initTable();
        loadCleaners();
    }

    private void initComponents() {
        setTitle("Cleaners Management - Cleaning Inventory System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Doesn't terminate main app when closed
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: Search & Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search Cleaner:"));
        txtSearch = new JTextField(20);
        topPanel.add(txtSearch);

        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh Table");
        topPanel.add(btnSearch);
        topPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: Table ---
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "First Name", "Last Name", "Phone", "Email"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent direct cell editing
            }
        };

        tableCleaners = new JTable(tableModel);
        tableCleaners.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Populate inputs on row selection
        tableCleaners.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelectedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableCleaners);
        add(scrollPane, BorderLayout.CENTER);

        // --- SOUTH PANEL: Input Form & Buttons ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        // Use GridBagLayout for a clean 2-column label + field alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cleaner Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtCleanerId = new JTextField();
        txtCleanerId.setVisible(false); // ID is managed internally

        txtFirstName = new JTextField(15);
        txtLastName = new JTextField(15);
        txtPhone = new JTextField(15);
        txtEmail = new JTextField(15);

        // ROW 0: First Name & Last Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("First Name:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtFirstName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Last Name:*"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtLastName, gbc);

        // ROW 1: Phone & Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Phone:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtPhone, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Email:*"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtEmail, gbc);

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        // Button Bar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAdd = new JButton("Add Cleaner");
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
        btnAdd.addActionListener(e -> addCleaner());
        btnUpdate.addActionListener(e -> updateCleaner());
        btnDelete.addActionListener(e -> deleteCleaner());
        btnClear.addActionListener(e -> clearForm());
        btnSearch.addActionListener(e -> searchCleaners());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadCleaners();
        });
    }

    // --- TABLE DATA MANAGEMENT ---
    private void initTable() {
        tableCleaners.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

    private void loadCleaners() {
        List<Cleaner> cleaners = cleanerDao.getAllCleaners();
        populateTable(cleaners);
    }

    private void populateTable(List<Cleaner> cleaners) {
        tableModel.setRowCount(0);
        for (Cleaner c : cleaners) {
            tableModel.addRow(new Object[]{
                c.getCleanerId(),
                c.getFirstName(),
                c.getLastName(),
                c.getPhone(),
                c.getEmail()
            });
        }
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = tableCleaners.getSelectedRow();
        if (selectedRow >= 0) {
            txtCleanerId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtFirstName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtLastName.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtPhone.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    // --- CRUD ACTIONS ---
    private void addCleaner() {
        if (!validateInputs()) return;

        try {
            Cleaner cleaner = extractCleanerFromForm();
            cleanerDao.addCleaner(cleaner);
            
            JOptionPane.showMessageDialog(this, "Cleaner added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadCleaners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding cleaner: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCleaner() {
        if (txtCleanerId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a cleaner from the table to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInputs()) return;

        try {
            Cleaner cleaner = extractCleanerFromForm();
            cleaner.setCleanerId(Integer.parseInt(txtCleanerId.getText().trim()));
            
            cleanerDao.updateCleaner(cleaner);
            
            JOptionPane.showMessageDialog(this, "Cleaner updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadCleaners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating cleaner: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCleaner() {
        int selectedRow = tableCleaners.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a cleaner from the table to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cleanerId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String name = tableModel.getValueAt(selectedRow, 1).toString() + " " + tableModel.getValueAt(selectedRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete cleaner: " + name + " (ID: " + cleanerId + ")?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                cleanerDao.deleteCleaner(cleanerId);
                JOptionPane.showMessageDialog(this, "Cleaner deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadCleaners();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting cleaner: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchCleaners() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadCleaners();
            return;
        }

        List<Cleaner> results = cleanerDao.searchCleaners(keyword);
        populateTable(results);
    }

    // --- VALIDATION & BUSINESS LOGIC ---
    private boolean validateInputs() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        // Required Field Checks
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (*).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Cleaner extractCleanerFromForm() {
        Cleaner cleaner = new Cleaner();
        cleaner.setFirstName(txtFirstName.getText().trim());
        cleaner.setLastName(txtLastName.getText().trim());
        cleaner.setPhone(txtPhone.getText().trim());
        cleaner.setEmail(txtEmail.getText().trim());
        return cleaner;
    }

    private void clearForm() {
        txtCleanerId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        tableCleaners.clearSelection();
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
            java.util.logging.Logger.getLogger(CleanersFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CleanersFrame().setVisible(true);
        });
    }
}
