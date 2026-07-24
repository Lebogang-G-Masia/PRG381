/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

/**
 *
 * @author sirna
 */
public class ReportsFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReportsFrame.class.getName());

    public ReportsFrame() {
        initComponents();
        buildUI();
    }

    private void buildUI() {
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 25));
        panel.setBackground(new java.awt.Color(248, 249, 251));

        javax.swing.JButton btnInv = createReportButton("Inventory Report");
        btnInv.addActionListener(e -> new InventoryReportFrame().setVisible(true));

        javax.swing.JButton btnHist = createReportButton("Issuance History");
        btnHist.addActionListener(e -> new IssuanceHistoryReportFrame().setVisible(true));

        javax.swing.JButton btnLow = createReportButton("Low Stock Report");
        btnLow.addActionListener(e -> new LowStockReportFrame().setVisible(true));

        javax.swing.JButton btnUsage = createReportButton("Material Usage");
        btnUsage.addActionListener(e -> new MaterialUsageReportFrame().setVisible(true));

        panel.add(btnInv);
        panel.add(btnHist);
        panel.add(btnLow);
        panel.add(btnUsage);

        getContentPane().removeAll();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(panel, java.awt.BorderLayout.CENTER);
        
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private javax.swing.JButton createReportButton(String text) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setPreferredSize(new java.awt.Dimension(220, 120));
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBackground(java.awt.Color.WHITE);
        btn.setForeground(new java.awt.Color(40, 45, 55));
        
        try {
            btn.putClientProperty("FlatLaf.style", "arc: 20; border: 1,1,1,1, #E2E5EA;");
        } catch (Exception ex) {
            // Ignore if FlatLaf isn't available
        }
        
        return btn;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reports");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new ReportsFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
