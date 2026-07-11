import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        // Load the image safely from your assets package
        java.net.URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            backgroundImage = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Could not find image: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // This ensures the gradient resizes dynamically if the window is stretched
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}