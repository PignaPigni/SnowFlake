
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 *
 * @author Nicholas Pigni
 */
public class TriangleModel {

    /**
     * Il triangolo generato.
     */
    public Polygon triangle;

    /**
     * La larghezza del triangolo modello.
     */
    private int T_WIDTH = 100;

    /**
     * L'ipotenusa del triangolo modello.
     */
    private int T_I;

    /**
     * L'altezza del triangolo modello.
     */
    private int T_HEIGHT;
    
    /**
     * Il margine minimo che deve tenere il triangolo.
     */
    private final int MARGIN = 20;

    /**
     * Lista dei punti di taglio del triangolo generato.
     */
    public ArrayList<Point> dots = new ArrayList<Point>();

    /**
     * Costruttore della classe TriangleModel.
     */
    public TriangleModel() {
    }

    public void calculateTriangleByPanelSize(int width, int height) {
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        int maxWidth = width - MARGIN * 2;
        int maxHeight = height - MARGIN * 2;

        T_HEIGHT = (int) Math.sqrt((T_WIDTH * 2) * (T_WIDTH * 2) - (T_WIDTH * T_WIDTH));
        int c1 = T_HEIGHT * maxWidth / T_WIDTH;
        int f_w = T_WIDTH * maxHeight / T_HEIGHT;
        int f_h = maxHeight;
        int s_x = MARGIN + (maxWidth - f_w) / 2;
        int s_y = MARGIN;

        if (f_w > maxWidth) {
            f_w = maxWidth;
            f_h = T_HEIGHT * maxWidth / T_WIDTH;
            s_x = MARGIN;
            s_y = MARGIN + (maxHeight - f_h) / 2;
        }

        xPoints[0] = s_x;
        yPoints[0] = s_y;
        xPoints[1] = s_x + f_w;
        yPoints[1] = s_y;
        xPoints[2] = s_x + f_w;
        yPoints[2] = s_y + f_h;

        this.triangle = new Polygon(xPoints, yPoints, 3);
    }

    public void addDotToModel(Point dot, int width, int height) {
        this.calculateTriangleByPanelSize(width, height);
        
        int x = dot.x * T_WIDTH / width;
        int y = dot.y * T_HEIGHT / height;

        dots.add(new Point(x, y));
    }

    /**
     * Ritorna il poligono del triangolo da stampare.
     *
     * @return Polygon Il triangolo da stampare.
     */
    public Polygon getTriangle() {
        return this.triangle;
    }

    /**
     * Ritorna i punti di taglio in base al triangolo da stampare.
     *
     * @return ArrayList<Point> Lista con i punti di taglio del triangolo.
     */
    public ArrayList<Point> getDots() {
        return this.dots;
    }

}
