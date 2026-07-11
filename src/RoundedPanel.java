import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private int cornerRadius;
    private Color backgroundColor;
    private boolean isGlass;

    public RoundedPanel(int radius, Color bgColor, boolean isGlass) {
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        this.isGlass = isGlass;
        setOpaque(false); // Crucial: Allows the background behind the corners to show
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Turn on Anti-aliasing for smooth, pixel-perfect curved edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isGlass) {
            // Glassmorphism: Semi-transparent white
            g2.setColor(new Color(255, 255, 255, 60)); 
        } else {
            // Solid color
            g2.setColor(backgroundColor);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
    }
}