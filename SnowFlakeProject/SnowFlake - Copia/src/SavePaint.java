
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SavePaint extends JPanel {

    public SavePaint() {
        JFrame frame = new JFrame("TheFrame");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);

        try {
            BufferedImage image = new BufferedImage(this.getWidth() + 7, this.getHeight() + 30, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            frame.paint(graphics2D);
            ImageIO.write(image, "png", new File("C:\\Users\\nicho\\Desktop\\Saves\\SavePaint.png"));

            // Capture screen from the top left in 200 by 200 pixel size.
        } catch (Exception exception) {
            //code
        }
        //frame.setLocation(0, 0);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(50, 50, 50, 50);
        g.setColor(Color.RED);
        g.drawRect(0, 0, this.getWidth()-5, this.getHeight()-1);
    }

}
