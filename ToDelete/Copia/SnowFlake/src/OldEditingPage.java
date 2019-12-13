
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * Classe che disegna la pagina di editing del triangolo.
 *
 * @author Nicholas Pigni
 */
public class OldEditingPage extends JPanel implements MouseListener, MouseMotionListener {

    /**
     * Il file del salvataggio dei punti di taglio del progetto corrente.
     */
    private File currentFile = null;

    /**
     * Il triangolo da ritagliare.
     */
    private Triangle triangle;

    /**
     * Lista dei punti di taglio.
     */
    private ArrayList<Point> dots = new ArrayList<Point>();

    /**
     * Il Raggio dei punti di taglio.
     */
    private int RADIUS = 10;

    /**
     * Il poligono formato con i punti di taglio.
     */
    private Polygon cutPoly = new Polygon();

    /**
     * Determina se bisogna generare o meno il poligono.
     */
    private boolean fillPoly = false;

    /**
     * Determina se bisogna disegnare o meno i punti di taglio.
     */
    private boolean drawDots = true;

    /**
     * La larghezza della pagina.
     */
    private int width;

    /**
     * L'altezza della pagina
     */
    private int height;

    /**
     * Bottone di aggiunta dei punti.
     */
    private Rectangle addButton = new Rectangle(10, 95, 150, 30);

    /**
     * Bottone di rimozione dei punti.
     */
    private Rectangle removeButton = new Rectangle(10, 130, 150, 30);

    /**
     * Determina se la modalità di inserimento è add o remove (true o false).
     */
    private boolean isAdd = true;

    /**
     * Bottone di aggiunta dei punti.
     */
    private Rectangle resetButton = new Rectangle(10, 210, 150, 30);

    /**
     * Bottone di aggiunta dei punti.
     */
    private Rectangle fillPolyButton = new Rectangle(10, 280, 150, 30);

    /**
     * Bottone di annullamento dell'ultimo dei punti di taglio.
     */
    private Rectangle undoButton = new Rectangle(10, 170, 150, 30);

    /**
     * Bottone di aggiunta dei punti.
     */
    private Rectangle saveButton = new Rectangle(10, 420, 150, 30);

    /**
     * Bottone di aggiunta dei punti.
     */
    private Rectangle openButton = new Rectangle(10, 375, 150, 30);

    public OldEditingPage() {
        triangle = new Triangle(new Point(1024 / 3, 768 / 4), 1024 / 4);
        dots = new ArrayList<Point>();
    }

    /**
     * Metodo che disegna sullo schermo.
     *
     * @param g Il contesto grafico.
     */
    public void paintComponent(Graphics g) {
        g.setColor(new Color(200, 230, 255));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(100, 150, 170));
        triangle.paint(g);
        //triangle.setA(new Point(this.width/3, this.height/4));
        triangle.update(new Point(this.width / 3, this.height / 4), width / 4);

        //DISEGNO I BOTTONI ADD E REMOVE----------------------------------------
        g.setColor(Color.GRAY);
        g.fillRect(addButton.x, addButton.y, addButton.width, addButton.height);
        g.fillRect(removeButton.x, removeButton.y, removeButton.width, removeButton.height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.BOLD, addButton.height));
        g.drawString("Add", addButton.x, addButton.y + addButton.height - 3);
        g.drawString("Remove", removeButton.x, removeButton.y + removeButton.height - 3);
        if (isAdd) {
            g.setColor(Color.RED);
            g.drawRect(removeButton.x, removeButton.y, removeButton.width, removeButton.height);
            g.setColor(Color.GREEN);
            g.drawRect(addButton.x, addButton.y - 1, addButton.width, addButton.height);
        } else {
            g.setColor(Color.RED);
            g.drawRect(addButton.x, addButton.y, addButton.width, addButton.height);
            g.setColor(Color.GREEN);
            g.drawRect(removeButton.x, removeButton.y, removeButton.width, removeButton.height);
        }
        //----------------------------------------------------------------------
        //DISEGNO GLI ALTRI BOTTONI---------------------------------------------

        g.setColor(Color.GRAY);
        g.fillRect(undoButton.x, undoButton.y, undoButton.width, undoButton.height);
        g.setColor(Color.BLACK);
        g.drawString("Undo", undoButton.x, undoButton.y + undoButton.height - 3);

        g.setColor(Color.GRAY);
        g.fillRect(resetButton.x, resetButton.y, resetButton.width, resetButton.height);
        g.setColor(Color.BLACK);
        g.drawString("Reset", resetButton.x, resetButton.y + resetButton.height - 3);

        g.setColor(Color.GRAY);
        g.fillRect(fillPolyButton.x, fillPolyButton.y, fillPolyButton.width, fillPolyButton.height);
        g.setColor(Color.BLACK);
        g.drawString("Preview", fillPolyButton.x, fillPolyButton.y + fillPolyButton.height - 3);

        g.setColor(Color.GRAY);
        g.fillRect(openButton.x, openButton.y, openButton.width, openButton.height + 5);
        g.setColor(Color.BLACK);
        g.drawString("Open", openButton.x, openButton.y - 2 + openButton.height - 3);

        g.setColor(Color.GRAY);
        g.fillRect(saveButton.x, saveButton.y, saveButton.width, saveButton.height);
        g.setColor(Color.BLACK);
        g.drawString("Save", saveButton.x, saveButton.y + saveButton.height - 3);

        g.setColor(Color.BLACK);
        if (currentFile != null) {
            g.drawString("Selected File: " + currentFile.getName(), 10, 60);
        }
        //----------------------------------------------------------------------
        //DISEGNO IL POLIGONO TAGLIATO------------------------------------------
        g.setColor(new Color(200, 230, 255));
        if (fillPoly) {
            g.fillPolygon(cutPoly);
        } else {
            g.drawPolygon(cutPoly);
        }
        //----------------------------------------------------------------------
        //DISEGNO I PUNTI DI TAGLIO---------------------------------------------
        if (drawDots) {
            g.setColor(Color.RED);
        } else {
            g.setColor(new Color(0, 0, 0, 0));
        }

        for (Point dot : dots) {
            g.fillOval(dot.x - RADIUS, dot.y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
        //----------------------------------------------------------------------
        System.out.println(this.getWidth() + "  " + this.getHeight());
    }

    public boolean contains(Point p, Rectangle rect) {
        if ((p.x > rect.x && p.x < rect.x + rect.width)
                && (p.y > rect.y && p.y < rect.y + rect.height)) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (contains(e.getPoint(), resetButton)) {
            cutPoly = new Polygon();
            dots = new ArrayList<Point>();
        } else if (contains(e.getPoint(), addButton)) {
            isAdd = true;
        } else if (contains(e.getPoint(), removeButton)) {
            isAdd = false;
        } else if (contains(e.getPoint(), fillPolyButton)) {
            fillPoly = true;
            drawDots = false;
        } else if (contains(e.getPoint(), undoButton)) {
            if (dots.size() > 0) {
                dots.remove(dots.size() - 1);
            }
        } else if (contains(e.getPoint(), saveButton)) {
            this.saveDots();
        } else if (contains(e.getPoint(), openButton)) {
            JFileChooser fc = new JFileChooser();
            MainFrame mf = new MainFrame();
            int returnVal = fc.showOpenDialog(mf);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                currentFile = fc.getSelectedFile();
                System.out.println("name:\t" + currentFile.getName());
            } else {
                System.out.println("File doesn't exists.");
                currentFile = null;
            }
            this.loadDots();
        } else {
            if (isAdd) {
                dots.add(e.getPoint());
            } else {
                for (Point dot : dots) {
                    if (e.getX() > dot.x - RADIUS
                            && e.getX() < dot.getX() + RADIUS * 2
                            && e.getY() > dot.y - RADIUS
                            && e.getY() < dot.getY() + RADIUS * 2) {
                        dots.remove(dot);
                        break;
                    }
                }
            }
            drawDots = true;
            fillPoly = false;
        }
        if (dots.size() >= 2) {
            int[] xPoints = new int[dots.size()];
            int[] yPoints = new int[dots.size()];
            for (int i = 0; i < dots.size(); i++) {
                xPoints[i] = dots.get(i).x;
                yPoints[i] = dots.get(i).y;
            }
            cutPoly = new Polygon(xPoints, yPoints, dots.size());
        }
        this.repaint();
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
        for (Point dot : dots) {
            if (e.getX() > dot.x - RADIUS
                    && e.getX() < dot.getX() + RADIUS * 2
                    && e.getY() > dot.y - RADIUS
                    && e.getY() < dot.getY() + RADIUS * 2) {
                dot.move(e.getX(), e.getY());
                break;
            }
        }
        if (dots.size() >= 2) {
            int[] xPoints = new int[dots.size()];
            int[] yPoints = new int[dots.size()];
            for (int i = 0; i < dots.size(); i++) {
                xPoints[i] = dots.get(i).x;
                yPoints[i] = dots.get(i).y;
            }
            cutPoly = new Polygon(xPoints, yPoints, dots.size());
        }
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void saveDots() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < dots.size(); i++) {
            data.append(dots.get(i).x + ", " + dots.get(i).y + "\n");
        }
        if (currentFile != null) {
            try {
                currentFile.createNewFile();
                Files.write(currentFile.toPath(), data.toString().getBytes());
            } catch (IOException ex) {
                System.out.println("Errore di scrittura del File");
            }
        }
        loadDots();
    }

    public void loadDots() {
        if (currentFile != null) {
            ArrayList csvDots = new ArrayList<>();
            try {
                csvDots = (ArrayList) Files.readAllLines(currentFile.toPath());
            } catch (IOException ex) {
                System.out.println("Errore di lettura del File");
            }
            String[] data = {"", ""};
            dots = new ArrayList<Point>();
            for (Object dot : csvDots) {
                System.out.println("dot: " + dot);
                data = dot.toString().split(", ");
                dots.add(new Point(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            }
            for (int i = 0; i < data.length; i++) {
                System.out.println("data" + data[i]);
            }
            if (dots.size() >= 2) {
                int[] xPoints = new int[dots.size()];
                int[] yPoints = new int[dots.size()];
                for (int i = 0; i < dots.size(); i++) {
                    xPoints[i] = dots.get(i).x;
                    yPoints[i] = dots.get(i).y;
                }
                cutPoly = new Polygon(xPoints, yPoints, dots.size());
            }
        }
        this.repaint();
    }
}
