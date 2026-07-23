import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import model.User;

public class DashboardFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private JLabel lblPageTitle;
    private User currentUser;
    
    private List<JButton> sidebarButtons = new ArrayList<>();

    public DashboardFrame(User user) {
        this.currentUser = user;
        
        setTitle("Cleaning Inventory System");
        setSize(1200, 800);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Setup UI
        buildSidebar();
        buildMainArea();
        
        // Start on Home
        if (!sidebarButtons.isEmpty()) {
            sidebarButtons.get(0).doClick();
        }
    }

    private void buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(30, 37, 48)); // Sleek dark navy
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBorder(new EmptyBorder(35, 0, 20, 0));

        // Logo/Title Area
        JLabel lblTitle = new JLabel("Clarity", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Inventory System", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(160, 165, 176));
        lblSubtitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(lblSubtitle);
        
        sidebar.add(Box.createRigidArea(new Dimension(0, 50))); // Spacing

        // Navigation Buttons
        JButton btnHome = createSidebarButton("Home", "Home");
        JButton btnMaterials = createSidebarButton("Materials", "Materials");
        JButton btnCleaners = createSidebarButton("Cleaners", "Cleaners");
        JButton btnSuppliers = createSidebarButton("Suppliers", "Suppliers");
        JButton btnIssueStock = createSidebarButton("Issue Stock", "Issue Stock");
        JButton btnReports = createSidebarButton("Reports", "Reports");
        JButton btnSettings = createSidebarButton("Settings", "Settings");
        
        sidebar.add(btnHome);
        sidebar.add(btnMaterials);
        sidebar.add(btnCleaners);
        sidebar.add(btnSuppliers);
        sidebar.add(btnIssueStock);
        sidebar.add(btnReports);
        sidebar.add(btnSettings);
        
        sidebar.add(Box.createVerticalGlue()); // Push logout to bottom
        
        JButton btnLogout = createSidebarButton("Logout", null);
        sidebar.add(btnLogout);
        
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
        
        add(sidebar, BorderLayout.WEST);
    }
    
    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton("   " + text); // Indent slightly
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(new Color(160, 165, 176));
        btn.setBackground(new Color(30, 37, 48));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setPreferredSize(new Dimension(250, 50));
        
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Remove focus ring natively via FlatLaf properties
        btn.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0;");
        btn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!btn.isSelected()) {
                    btn.setBackground(new Color(45, 55, 72));
                    btn.setForeground(Color.WHITE);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!btn.isSelected()) {
                    btn.setBackground(new Color(30, 37, 48));
                    btn.setForeground(new Color(160, 165, 176));
                }
            }
        });
        
        if (cardName != null) {
            sidebarButtons.add(btn);
            btn.addActionListener(e -> {
                selectSidebarButton(btn);
                cardLayout.show(mainContentPanel, cardName);
                lblPageTitle.setText(text);
            });
        }
        
        return btn;
    }
    
    private void selectSidebarButton(JButton selectedBtn) {
        for (JButton btn : sidebarButtons) {
            btn.setSelected(false);
            btn.setBackground(new Color(30, 37, 48));
            btn.setForeground(new Color(160, 165, 176));
            btn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        }
        selectedBtn.setSelected(true);
        selectedBtn.setBackground(new Color(45, 55, 72));
        selectedBtn.setForeground(Color.WHITE);
        // Active indicator on the left
        selectedBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(40, 150, 255)),
            BorderFactory.createEmptyBorder(0, 6, 0, 0)
        ));
    }

    private void buildMainArea() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(248, 249, 251));

        // Top Header Bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(getWidth(), 75));
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 229, 234)),
                new EmptyBorder(0, 35, 0, 35)
        ));
        
        lblPageTitle = new JLabel("Home");
        lblPageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPageTitle.setForeground(new Color(40, 40, 40));
        header.add(lblPageTitle, BorderLayout.WEST);
        
        String welcomeText = currentUser != null ? "Welcome, " + currentUser.getFirstName() : "Welcome, User";
        JLabel lblWelcome = new JLabel(welcomeText);
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblWelcome.setForeground(new Color(110, 115, 125));
        header.add(lblWelcome, BorderLayout.EAST);
        
        centerPanel.add(header, BorderLayout.NORTH);

        // Main Card Container
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setOpaque(false); // Let the light gray background show through
        mainContentPanel.setBorder(new EmptyBorder(35, 35, 35, 35));

        // 1. Home View Card
        mainContentPanel.add(buildHomeView(), "Home");

        // 2. Add New Views (Extracting their content panes to embed natively)
        mainContentPanel.add(new ui.MaterialsFrame().getContentPane(), "Materials");
        mainContentPanel.add(new ui.CleanersFrame().getContentPane(), "Cleaners");
        mainContentPanel.add(new ui.SuppliersFrame().getContentPane(), "Suppliers");
        mainContentPanel.add(new ui.ReportsFrame().getContentPane(), "Reports");

        // 3. Issue Stock View Card
        try {
            StockIssuanceService service = new StockIssuanceService(new InMemoryIssuanceRepository());
            StockIssuanceFrame stockFrame = new StockIssuanceFrame(service);
            JPanel stockPanel = new JPanel(new BorderLayout());
            stockPanel.setOpaque(false);
            stockPanel.add(stockFrame.getContentPane(), BorderLayout.CENTER);
            mainContentPanel.add(stockPanel, "Issue Stock");
        } catch (Exception e) {
            JPanel errorPanel = new JPanel();
            errorPanel.add(new JLabel("Error loading Stock Issuance view."));
            mainContentPanel.add(errorPanel, "Issue Stock");
        }

        // 4. Settings View Card
        try {
            ui.SettingsFrame settingsFrame = new ui.SettingsFrame(currentUser);
            JPanel settingsPanel = new JPanel(new BorderLayout());
            settingsPanel.setOpaque(false);
            settingsPanel.add(settingsFrame.getContentPane(), BorderLayout.CENTER);
            mainContentPanel.add(settingsPanel, "Settings");
        } catch (Exception e) {
            JPanel errorPanel = new JPanel();
            errorPanel.add(new JLabel("Error loading Settings view."));
            mainContentPanel.add(errorPanel, "Settings");
        }

        centerPanel.add(mainContentPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JPanel buildHomeView() {
        // FlowLayout with left alignment prevents stretching
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        homePanel.setOpaque(false);
        
        // Fetch dynamic statistics from DAOs
        dao.MaterialDao materialDao = new dao.MaterialDao();
        dao.CleanerDao cleanerDao = new dao.CleanerDao();
        
        int totalMaterials = materialDao.getAllMaterials().size();
        int totalCleaners = cleanerDao.getAllCleaners().size();
        int lowStock = materialDao.getLowStockMaterials().size();
        
        homePanel.add(createSummaryCard("Total Materials", String.valueOf(totalMaterials)));
        homePanel.add(createSummaryCard("Total Cleaners", String.valueOf(totalCleaners)));
        homePanel.add(createSummaryCard("Low Stock Alerts", String.valueOf(lowStock)));
        
        return homePanel;
    }
    
    private JPanel createSummaryCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setPreferredSize(new Dimension(240, 130));
        
        // FlatLaf exact styling: White bg, rounded corners, subtle 1px border
        card.putClientProperty(FlatClientProperties.STYLE, 
                "arc: 20; background: #FFFFFF; border: 1,1,1,1, #E2E5EA;");
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTitle.setForeground(new Color(130, 135, 145));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(new Color(40, 45, 55));
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
}
