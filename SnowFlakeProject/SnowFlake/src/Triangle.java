
import java.awt.Graphics;
import java.awt.Point;

/**
 * Questa classe descrive un modello di Triangolo.
 *
 * @author Nicholas Pigni
 */
public class Triangle {

    /**
     * Coordinate X del triangolo.
     */
    private int[] x = new int[3];

    /**
     * Coordinate Y del triangolo.
     */
    private int[] y = new int[3];

    /**
     * Punto a del triangolo.
     */
    private Point a = new Point();

    /**
     * Punto b del triangolo.
     */
    private Point b = new Point();

    /**
     * Punto c del triangolo.
     */
    private Point c = new Point();

    /**
     * Costruttore della classe Triangle.
     *
     * @param a Il punto di congiunzione tra il cateto minore e l'ipotenusa.
     * @param width La lunghezza del cateto minore.
     */
    public Triangle(Point a, int width) {
        this.a = a;
        this.b = new Point((int) a.getX() + width, (int) a.getY());
        this.c = new Point(
                (int) b.getX(),
                (int) b.getY() + (int) Math.sqrt(
                Math.pow(width * 2, 2) - Math.pow(width, 2)
        ));
        int[] y = {(int) a.getY(), (int) b.getY(), (int) c.getY()};
        int[] x = {(int) a.getX(), (int) b.getX(), (int) c.getX()};
        this.y = y;
        this.x = x;
    }
/*
    public Triangle() {
        this.b = new Point((int) a.getX() + width, (int) a.getY());
        this.c = new Point(
                (int) b.getX(),
                (int) b.getY() + (int) Math.sqrt(
                Math.pow(width * 2, 2) - Math.pow(width, 2)
        ));
        int[] y = {(int) a.getY(), (int) b.getY(), (int) c.getY()};
        int[] x = {(int) a.getX(), (int) b.getX(), (int) c.getX()};
        this.y = y;
        this.x = x;
    }*/

    public void paint(Graphics g) {
        g.fillPolygon(x, y, 3);
    }

    /**
     * Metodo getter delle coordinate X del triangolo.
     *
     * @return x Array contenente le coordinate X del triangolo.
     */
    public int[] getX() {
        return this.x;
    }

    /**
     * Metodo getter delle coordinate Y del triangolo.
     *
     * @return y Array contenente le coordinate Y del triangolo.
     */
    public int[] getY() {
        return this.y;
    }

    /**
     * Metodo getter delle coordinate del punto a del triangolo.
     *
     * @return y Array contenente le coordinate del punto a del triangolo.
     */
    public Point getA() {
        return this.a;
    }

    /**
     * Metodo getter delle coordinate del punto b del triangolo.
     *
     * @return y Array contenente le coordinate del punto b del triangolo.
     */
    public Point getB() {
        return this.b;
    }

    /**
     * Metodo getter delle coordinate del punto c del triangolo.
     *
     * @return y Array contenente le coordinate del punto c del triangolo.
     */
    public Point getC() {
        return this.c;
    }

    /**
     * Metodo setter delle coordinate del punto c del triangolo.
     *
     * @param a Il punto da settare.
     */
    public void setA(Point a) {
        this.a = a;
        //Triangle();
    }

    /**
     * Metodo setter delle coordinate del punto c del triangolo.
     *
     * @param b Il punto da settare.
     */
    public void setB(Point b) {
        this.b = b;
    }

    /**
     * Metodo setter delle coordinate del punto c del triangolo.
     *
     * @param c Il punto da settare.
     */
    public void setC(Point c) {
        this.c = c;
    }
}
