
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
     * La x da cui deve partire per disegnare i punti.
     */
    public int s_x;

    /**
     * La y da cui deve partire per disegnare i punti.
     */
    public int s_y;

    /**
     * Il Raggio dei punti di taglio.
     */
    public int RADIUS = 10;

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
        s_x = MARGIN + (maxWidth - f_w) / 2;
        s_y = MARGIN;

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

        int x = (dot.x - triangle.xpoints[0]) * T_WIDTH / triangle.getBounds().width;
        int y = (dot.y - triangle.ypoints[0]) * T_HEIGHT / triangle.getBounds().height;

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

    public int[][] getCoords() {
        int[][] coords = {};
        for(int i = 0; i < dots.size(); i++){
            coords[0][i] = (int)dots.get(i).x;
            coords[1][i] = (int)dots.get(i).y;
        }
        return coords;
    }

    /**
     * Ritorna i punti di taglio in base al triangolo da stampare.
     *
     * @return ArrayList<Point> Lista con i punti di taglio del triangolo.
     */
    public ArrayList<Point> getDots(int width, int height) {
        this.calculateTriangleByPanelSize(width, height);
        ArrayList<Point> newDots = new ArrayList<Point>();
        Point resDot = new Point();
        for (Point dot : this.dots) {
            resDot.x = triangle.getBounds().x + (int) (dot.getX() * triangle.getBounds().getWidth() / (double) T_WIDTH);
            resDot.y = triangle.getBounds().y + (int) (dot.getY() * triangle.getBounds().getHeight() / (double) T_HEIGHT);
            newDots.add(resDot);
            resDot = new Point();
            /*
            dot.x = (dot.x - triangle.xpoints[0]) * T_WIDTH / triangle.getBounds().width;
            dot.y = (dot.y - triangle.ypoints[0]) * T_HEIGHT / triangle.getBounds().height;
            /*dot.x = s_x + dot.x * triangle.getBounds().width / T_WIDTH;
            dot.y = s_y + dot.y * triangle.getBounds().height / T_HEIGHT;*/
        }
        return newDots;
    }

}
