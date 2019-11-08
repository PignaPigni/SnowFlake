
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
     * Lista dei punti di taglio del triangolo generato.
     */
    public ArrayList<Point> dots = new ArrayList<Point>();

    /**
     * Costruttore della classe TriangleModel.
     */
    public TriangleModel() {
    }

    public void calculateTriangleByPanelSize(int width, int height, int margin) {
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        int maxWidth = width - margin * 2;
        int maxHeight = height - margin * 2;

        T_HEIGHT = (int) Math.sqrt((T_WIDTH * 2) * (T_WIDTH * 2) - (T_WIDTH * T_WIDTH));
        int c1 = T_HEIGHT * width / T_WIDTH;
        xPoints[0] = margin;
        yPoints[0] = margin;
        xPoints[1] = xPoints[0] + maxWidth;
        yPoints[1] = yPoints[0];
        xPoints[2] = xPoints[1];
        yPoints[2] = yPoints[1] + c1;

        int w;

        if (c1 > maxHeight) {
            c1 = maxHeight;
            w = T_WIDTH * c1 / T_HEIGHT;

            xPoints[0] = maxWidth / 2 - w / 2;
            yPoints[0] = margin;
            xPoints[1] = xPoints[0] + w;
            yPoints[1] = yPoints[0];
            xPoints[2] = xPoints[1];
            yPoints[2] = yPoints[1] + c1;
        }

        this.triangle = new Polygon(xPoints, yPoints, 3);
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
