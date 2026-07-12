import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class StockIssuanceFrame extends JFrame {
    private final StockIssuanceService issuanceService;
    private final JComboBox<MaterialStock> materialCombo = new JComboBox<>();
    private final JComboBox<CleanerRecord> cleanerCombo = new JComboBox<>();
    private final JSpinner quantitySpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 100000, 1));
    private final JLabel availableLabel = new JLabel("Available: 0");
    private final DefaultTableModel historyModel = new DefaultTableModel(
            new String[]{"ID", "Material", "Cleaner", "Quantity", "Issued At"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public StockIssuanceFrame(StockIssuanceService issuanceService) {
        this.issuanceService = issuanceService;
        setupFrame();
        buildScreen();
        loadData();
    }

    private void setupFrame() {
        setTitle("Stock Issuance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
    }

    private void buildScreen() {
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(new Color(248, 249, 251));
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("Issue Materials to Cleaners");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 25f));
        title.setForeground(new Color(35, 45, 65));
        content.add(title, BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 20));
        centre.setOpaque(false);
        centre.add(createIssuanceForm(), BorderLayout.NORTH);
        centre.add(createHistoryTable(), BorderLayout.CENTER);
        content.add(centre, BorderLayout.CENTER);

        setContentPane(content);
    }

    private JPanel createIssuanceForm() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 234)),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 12);
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(card, gbc, 0, "Material", materialCombo);
        addFormRow(card, gbc, 1, "Cleaner", cleanerCombo);
        addFormRow(card, gbc, 2, "Quantity", quantitySpinner);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        availableLabel.setFont(availableLabel.getFont().deriveFont(Font.BOLD, 14f));
        availableLabel.setForeground(new Color(30, 105, 80));
        card.add(availableLabel, gbc);

        JButton issueButton = new JButton("Issue Material");
        issueButton.addActionListener(event -> issueSelectedMaterial());
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(issueButton, gbc);

        materialCombo.addActionListener(event -> updateAvailableQuantity());
        return card;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row,
            String labelText, java.awt.Component input) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel(labelText + ":"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        input.setPreferredSize(new Dimension(280, 32));
        panel.add(input, gbc);
    }

    private JPanel createHistoryTable() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JLabel heading = new JLabel("Issuance History");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 18f));
        panel.add(heading, BorderLayout.NORTH);

        JTable historyTable = new JTable(historyModel);
        historyTable.setRowHeight(28);
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getColumnModel().getColumn(0).setMaxWidth(55);
        historyTable.getColumnModel().getColumn(3).setMaxWidth(90);
        historyTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(225, 228, 234)));
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel note = new JLabel("New issuances appear at the top of the table.");
        note.setForeground(new Color(105, 110, 120));
        note.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(note, BorderLayout.SOUTH);
        return panel;
    }

    private void loadData() {
        materialCombo.removeAllItems();
        for (MaterialStock material : issuanceService.getMaterials()) {
            materialCombo.addItem(material);
        }

        cleanerCombo.removeAllItems();
        for (CleanerRecord cleaner : issuanceService.getCleaners()) {
            cleanerCombo.addItem(cleaner);
        }

        refreshHistory();
        updateAvailableQuantity();
    }

    private void issueSelectedMaterial() {
        MaterialStock material = (MaterialStock) materialCombo.getSelectedItem();
        CleanerRecord cleaner = (CleanerRecord) cleanerCombo.getSelectedItem();
        int quantity = (int) quantitySpinner.getValue();

        try {
            issuanceService.issueMaterial(material, cleaner, quantity);
            loadData();
            quantitySpinner.setValue(1);
            JOptionPane.showMessageDialog(this,
                    "Material issued successfully.",
                    "Stock Issuance",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IssuanceException exception) {
            JOptionPane.showMessageDialog(this,
                    exception.getMessage(),
                    "Cannot Issue Material",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateAvailableQuantity() {
        MaterialStock material = (MaterialStock) materialCombo.getSelectedItem();
        int available = material == null ? 0 : material.getAvailableQuantity();
        availableLabel.setText("Available: " + available);
    }

    private void refreshHistory() {
        historyModel.setRowCount(0);
        List<StockIssuance> history = issuanceService.getIssuanceHistory();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (int index = history.size() - 1; index >= 0; index--) {
            StockIssuance issuance = history.get(index);
            historyModel.addRow(new Object[]{
                issuance.getId(),
                issuance.getMaterialName(),
                issuance.getCleanerName(),
                issuance.getQuantity(),
                issuance.getIssuedAt().format(formatter)
            });
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            IssuanceRepository repository = new InMemoryIssuanceRepository();
            StockIssuanceService service = new StockIssuanceService(repository);
            new StockIssuanceFrame(service).setVisible(true);
        });
    }
}
