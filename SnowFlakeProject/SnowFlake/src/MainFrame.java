
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
public class MainFrame extends JFrame implements MouseListener {
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
    }

    public void paint(Graphics g) {
        ep.paint(g);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
    }
}
