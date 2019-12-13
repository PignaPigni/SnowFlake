
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Nicholas Pigni
 */
public class SavingPage implements MouseListener, MouseMotionListener{
    /**
     * La larghezza della pagina.
     */
    private int width;

    /**
     * L'altezza della pagina
     */
    private int height;

    /**
     * Metodo costruttore della classe SavingPage.
     */
    public SavingPage(){
        
    }
    
    public void paint(Graphics g){
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
   }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }    
    
    public void update(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
