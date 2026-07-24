import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;

public class RegistrationFrame extends JFrame {
    
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnBack;

    public RegistrationFrame() {
        initComponents();
        setupBaseLayout();
    }
    
    private JPanel createIconTextField(String iconPath, String placeholderText, JTextField textField) {
        RoundedPanel wrapper = new RoundedPanel(15, Color.WHITE, false);
        wrapper.setLayout(new BorderLayout(10, 0)); 
        wrapper.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        wrapper.setCustomBorder(new Color(210, 210, 210), 1);
        wrapper.setPreferredSize(new Dimension(300, 45));

        JLabel iconLabel = new JLabel();
        URL imgUrl = getClass().getResource(iconPath);
        if (imgUrl != null) {
            Image originalImg = new ImageIcon(imgUrl).getImage();
            Image scaledImg = originalImg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        }
        wrapper.add(iconLabel, BorderLayout.WEST);
        
        textField.setBorder(null);
        textField.setOpaque(false);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.putClientProperty("JTextField.placeholderText", placeholderText);
        
        wrapper.add(textField, BorderLayout.CENTER);

        return wrapper;
    }
    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    private void setupBaseLayout() {
        setTitle("University Cleaning Inventory System - Registration");
        setSize(1100, 700);
        setLocationRelativeTo(null);         
        getContentPane().setLayout(new GridLayout(1, 2));
        
        // Left Panel
        ImagePanel leftPanel = new ImagePanel("/assets/bg_gradient.jpeg");
        leftPanel.setLayout(new GridBagLayout());
        RoundedPanel glassCard = new RoundedPanel(30, Color.WHITE, true);
        glassCard.setPreferredSize(new Dimension(400, 400));
        glassCard.setLayout(new GridBagLayout()); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(15, 0, 30, 0);

        JLabel leafIcon = new JLabel();
        URL leafUrl = getClass().getResource("/assets/icon_leaf.png");
        if (leafUrl != null) {
            Image originalLeaf = new ImageIcon(leafUrl).getImage();
            leafIcon.setIcon(new RoundedIcon(originalLeaf, 100, 100, 15));
        }
        glassCard.add(leafIcon, gbc);

        JLabel headingLabel = new JLabel("Join the System");
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headingLabel.setForeground(Color.WHITE);
        glassCard.add(headingLabel, gbc);

        JLabel subLabel1 = new JLabel("Register to manage inventory efficiently");
        subLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subLabel1.setForeground(new Color(230, 230, 230));
        glassCard.add(subLabel1, gbc);
        
        leftPanel.add(glassCard); 

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(250, 250, 251));
        rightPanel.setLayout(new GridBagLayout()); 
        
        RoundedPanel regCard = new RoundedPanel(20, Color.WHITE, false);
        regCard.setPreferredSize(new Dimension(450, 620));
        regCard.setBorder(BorderFactory.createEmptyBorder()); 
        regCard.setCustomBorder(new Color(230, 230, 230), 1);
        rightPanel.add(regCard);
        
        regCard.setLayout(new GridBagLayout());
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridwidth = GridBagConstraints.REMAINDER;
        cardGbc.anchor = GridBagConstraints.CENTER;
        
        // Header
        cardGbc.insets = new Insets(20, 0, 10, 0);
        JLabel welcomeLabel = new JLabel("Create an Account");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(40, 40, 40));
        regCard.add(welcomeLabel, cardGbc);
        
        cardGbc.anchor = GridBagConstraints.WEST;
        
        // First Name
        cardGbc.insets = new Insets(0, 50, 2, 50);
        JLabel fNameLabel = new JLabel("First Name");
        fNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fNameLabel.setForeground(new Color(80, 80, 80));
        regCard.add(fNameLabel, cardGbc);

        cardGbc.insets = new Insets(0, 50, 10, 50); 
        txtFirstName = new JTextField();
        regCard.add(createIconTextField("/assets/icon_user.png", "Enter first name", txtFirstName), cardGbc);
        
        // Last Name
        cardGbc.insets = new Insets(0, 50, 2, 50);
        JLabel lNameLabel = new JLabel("Last Name");
        lNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lNameLabel.setForeground(new Color(80, 80, 80));
        regCard.add(lNameLabel, cardGbc);

        cardGbc.insets = new Insets(0, 50, 10, 50); 
        txtLastName = new JTextField();
        regCard.add(createIconTextField("/assets/icon_user.png", "Enter last name", txtLastName), cardGbc);
        
        // Username
        cardGbc.insets = new Insets(0, 50, 2, 50);
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(new Color(80, 80, 80));
        regCard.add(userLabel, cardGbc);

        cardGbc.insets = new Insets(0, 50, 10, 50); 
        txtUsername = new JTextField();
        regCard.add(createIconTextField("/assets/icon_user.png", "Enter username", txtUsername), cardGbc);
        
        // Email
        cardGbc.insets = new Insets(0, 50, 2, 50);
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.setForeground(new Color(80, 80, 80));
        regCard.add(emailLabel, cardGbc);

        cardGbc.insets = new Insets(0, 50, 10, 50); 
        txtEmail = new JTextField();
        regCard.add(createIconTextField("/assets/icon_box.png", "Enter email address", txtEmail), cardGbc);

        // Password
        cardGbc.insets = new Insets(0, 50, 2, 50);
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passLabel.setForeground(new Color(80, 80, 80));
        regCard.add(passLabel, cardGbc);

        cardGbc.insets = new Insets(0, 50, 15, 50);
        txtPassword = new JPasswordField();
        regCard.add(createIconTextField("/assets/icon_lock.png", "••••••••", txtPassword), cardGbc);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.setPreferredSize(new Dimension(300, 45));
        
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setForeground(new Color(80, 80, 80));
        btnBack.setBackground(new Color(230, 230, 230));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(40, 150, 255));
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        btnPanel.add(btnBack);
        btnPanel.add(btnRegister);

        cardGbc.insets = new Insets(10, 50, 20, 50);
        regCard.add(btnPanel, cardGbc);
        
        // Actions
        btnBack.addActionListener(e -> {
            RegistrationFrame.this.dispose();
            new LoginFrame().setVisible(true);
        });
        
        btnRegister.addActionListener(e -> {
            String fName = txtFirstName.getText().trim();
            String lName = txtLastName.getText().trim();
            String uName = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();
            
            if (fName.isEmpty() || lName.isEmpty() || uName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(RegistrationFrame.this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            model.User newUser = new model.User();
            newUser.setFirstName(fName);
            newUser.setLastName(lName);
            newUser.setUsername(uName);
            newUser.setEmail(email);
            newUser.setPassword(pass);
            newUser.setRole("Staff");
            
            dao.UserDao userDao = new dao.UserDao();
            boolean success = userDao.register(newUser);
            
            if (success) {
                JOptionPane.showMessageDialog(RegistrationFrame.this, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                RegistrationFrame.this.dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(RegistrationFrame.this, "Registration failed. Username might be taken or database error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        getContentPane().add(leftPanel);
        getContentPane().add(rightPanel);
    }
}
