
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Nicholas Pigni
 */
public class MainFrame extends JFrame implements EditingPageListener {

    /**
     * La pagina di Editing.
     */
    private EditingPage ep;

    /**
     * Metodo costruttore del MainFrame.
     */
    public MainFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1024, 768);
        this.setMinimumSize(new Dimension(1024, 768));

        ep = new EditingPage();
        this.addMouseListener(ep);
        this.addMouseMotionListener(ep);
        ep.addEditingPageListener(this);
        this.setLayout(null);
    }

    public void paint(Graphics g) {
        super.paint(g);
        ep.update(this.getWidth(), this.getHeight());
        ep.paint(g);
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
    }

    @Override
    public void update() {
        this.repaint();
        System.out.println("skuuuu");
    }
}
