import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 30;
    private Color backgroundColor = Color.WHITE;
    private boolean isGlass = false;
    
    private Color borderColor = null;
    private int borderThickness = 0;

    public RoundedPanel(int radius, Color bgColor, boolean isGlass) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        this.isGlass = isGlass;
        setOpaque(false); 
    }

    public void setCustomBorder(Color color, int thickness) {
        this.borderColor = color;
        this.borderThickness = thickness;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isGlass) {
            g2.setColor(new Color(0, 0, 0, 70)); 
        } else {
            g2.setColor(backgroundColor);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        if (borderColor != null && borderThickness > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));
            
            int offset = borderThickness / 2;
            g2.drawRoundRect(offset, offset, getWidth() - borderThickness, getHeight() - borderThickness, cornerRadius, cornerRadius);
        }
        
        g2.dispose();
    }
}