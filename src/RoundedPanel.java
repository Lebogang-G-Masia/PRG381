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
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isGlass) {
            g2.setColor(new Color(255, 255, 255, 60)); 
        } else {
            g2.setColor(backgroundColor);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
    }
}