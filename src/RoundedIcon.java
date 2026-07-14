import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;

public class RoundedIcon implements Icon {
    private final Image image;
    private final int width;
    private final int height;
    private final int radius;

    public RoundedIcon(Image image, int width, int height, int radius) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Turn on high-quality rendering algorithms
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Create a rounded rectangle clipping path
        g2.setClip(new RoundRectangle2D.Float(x, y, width, height, radius, radius));

        // Draw the original high-res image directly into that clipped area
        g2.drawImage(image, x, y, width, height, c);
        
        g2.dispose();
    }
}