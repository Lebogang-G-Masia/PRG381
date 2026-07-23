package ui;

import dao.UserDao;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsFrame extends JFrame {

    private final User currentUser;
    private final UserDao userDao;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    
    private JPasswordField txtOldPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;

    public SettingsFrame(User currentUser) {
        this.currentUser = currentUser;
        this.userDao = new UserDao();
        
        initComponents();
    }

    private void initComponents() {
        setTitle("Settings");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // --- Profile Update Section ---
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBorder(BorderFactory.createTitledBorder("Update Profile"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFirstName = new JTextField(currentUser != null ? currentUser.getFirstName() : "", 20);
        txtLastName = new JTextField(currentUser != null ? currentUser.getLastName() : "", 20);
        txtEmail = new JTextField(currentUser != null ? currentUser.getEmail() : "", 20);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        profilePanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        profilePanel.add(txtFirstName, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        profilePanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        profilePanel.add(txtLastName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        profilePanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        profilePanel.add(txtEmail, gbc);

        JButton btnUpdateProfile = new JButton("Save Profile");
        btnUpdateProfile.addActionListener(this::updateProfile);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        profilePanel.add(btnUpdateProfile, gbc);

        // --- Password Change Section ---
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Change Password"));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtOldPassword = new JPasswordField(20);
        txtNewPassword = new JPasswordField(20);
        txtConfirmPassword = new JPasswordField(20);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        passwordPanel.add(new JLabel("Old Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        passwordPanel.add(txtOldPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        passwordPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        passwordPanel.add(txtNewPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        passwordPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        passwordPanel.add(txtConfirmPassword, gbc);

        JButton btnChangePassword = new JButton("Change Password");
        btnChangePassword.addActionListener(this::changePassword);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        passwordPanel.add(btnChangePassword, gbc);

        mainPanel.add(profilePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(passwordPanel);

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    private void updateProfile(ActionEvent e) {
        if (currentUser == null) return;
        
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);

        if (userDao.updateProfile(currentUser)) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changePassword(ActionEvent e) {
        if (currentUser == null) return;

        String oldPass = new String(txtOldPassword.getPassword());
        String newPass = new String(txtNewPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDao.changePassword(currentUser.getUserId(), oldPass, newPass)) {
            JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            txtOldPassword.setText("");
            txtNewPassword.setText("");
            txtConfirmPassword.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change password. Please check your old password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
