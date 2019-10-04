
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author Nicholas Pigni
 */
public class EditingPage extends JFrame{
    
    /**
     * Il triangolo da ritagliare.
     */
    private Triangle triangle;
    private JButton b;

    /**
     * Lista dei punti di taglio.
     */
    private List<Point> dots;
    
    public EditingPage(){
        triangle = new Triangle(new Point(1024/3, 768/4), 1024/4);
        b = new JButton();
        b.setBounds(100, 100, 100, 40);
        this.add(b);
        this.setLayout(null);
        
    }
    
    /**
     * Metodo che disegna sullo schermo.
     * @param g Il contesto grafico.
     */
    public void paint(Graphics g){
        triangle.paint(g);
        System.out.println("sku");
        b.paint(g);
        triangle.setA(new Point(this.getWidth()/3, this.getHeight()/4));
    }
}
