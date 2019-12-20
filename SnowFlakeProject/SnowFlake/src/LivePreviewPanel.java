
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

/**
 *
 * @author Nicholas Pigni
 */
public class LivePreviewPanel extends javax.swing.JPanel {

    /**
     * Indica se deve fare la live preview o meno.
     */
    private boolean isLivePreview = false;

    /**
     * L'area di taglio da cui costruire il fiocco di neve.
     */
    private Area cutArea = new Area();

    /**
     * Il margine da mantenere
     */
    private int MARGIN = 0;

    /**
     * Il file di salvataggio corrente dell'immagine png.
     */
    public File currentPngFile = null;

    /**
     * Il file di salvataggio corrente dell'immagine Svg.
     */
    public File currentSvgFile = null;

    public boolean isMinimumResolution = true;

    private Graphics2D g2;

    public ArrayList<Area> getRenderAreas() {
        return null;
    }

    /**
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isLivePreview) {

            g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.CYAN);
            g2.translate(MARGIN, 0);
            g2.translate(20, 0);

            //SETTORE 1
            Area sector1 = new Area(getFlippedArea(cutArea));
            sector1.add(cutArea);
            g2.fill(sector1);
            //--------------------

            //SETTORE 2
            Area sector2 = new Area(getRotateArea(60, sector1));
            g2.translate(sector1.getBounds().width, 0);
            g2.fill(sector2);
            //--------------------
            //SETTORE 3
            Area sector3 = new Area(getRotateArea(120, sector1));
            g2.translate(sector1.getBounds().width / 2, sector2.getBounds().height);
            g2.fill(sector3);
            //--------------------

            //SETTORE 4
            Area sector4 = new Area(getRotateArea(180, sector1));
            g2.translate(-sector1.getBounds().width / 2, sector1.getBounds().height);
            g2.fill(sector4);
            //--------------------

            //SETTORE 5
            Area sector5 = new Area(getRotateArea(240, sector1));
            g2.translate(-sector1.getBounds().width, 0);
            g2.fill(sector5);
            //--------------------

            //SETTORE 6
            Area sector6 = new Area(getRotateArea(300, sector1));
            g2.translate(-sector1.getBounds().width / 2, -sector1.getBounds().height);
            g2.fill(sector6);
            //--------------------
            /*
            g2.setColor(Color.RED);
            g2.fill(cutArea);
            for (int i = 0; i < 6 ; i++) {
                g2.fill(getRotateArea((i-1)*60, getFlippedArea(cutArea)));
                g2.fill(getRotateArea(i*60, cutArea));
            }*/
        }
    }

    public void generate() {
        JFrame generateFrame = new JFrame();
        generateFrame.add(this);
        generateFrame.setSize(500, 500);
        generateFrame.setVisible(true);
    }

    /**
     * Salva un file Svg contenente l'immagine generata del fiocco di neve.
     */
    public void saveSVG() {
        if (this.currentSvgFile != null) {
            DOMImplementation domImpl
                    = GenericDOMImplementation.getDOMImplementation();
            String svgNS = "http://www.w3.org/2000/svg";
            Document document = domImpl.createDocument(svgNS, "svg", null);
            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
            this.paintComponent(svgGenerator);
            try {
                svgGenerator.stream(this.currentSvgFile.toPath().toString());
            } catch (SVGGraphics2DIOException e) {

            }
        } else {
            JOptionPane jop = new JOptionPane();

            jop.showMessageDialog(this, "No file selected!",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            repaint();
        }
    }

    /**
     * Salva un file Png contenente l'immagine generata del fiocco di neve.
     */
    public void savePNG() {
        if (this.currentPngFile != null) {
            JFrame savePngFrame = new JFrame("Png Saved!");
            savePngFrame.add(this);
            if (isMinimumResolution) {
                savePngFrame.setSize(500, 500);
            } else {
                savePngFrame.setSize(1000, 1000);
            }
            savePngFrame.setVisible(true);
            try {
                BufferedImage image = new BufferedImage(this.getWidth() + 7, 
                        this.getHeight() + 30, 
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics2d = image.createGraphics();
                savePngFrame.paint(graphics2d);
                ImageIO.write(image, "png", new File(this.currentPngFile.getPath()));
            } catch (Exception exception) {}
        } else {
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(this, "No file selected!",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            repaint();
        }
    }

    private Shape getRotateArea(int angle, Shape shape) {
        AffineTransform af = new AffineTransform();
        af.rotate(Math.toRadians(angle), shape.getBounds().x, shape.getBounds().y);
        return af.createTransformedShape(shape);
    }

    /**
     * Permette di avere l'area specchiata.
     *
     * @return Il triangolo specchiato.
     */
    private Shape getFlippedArea(Shape area) {
        AffineTransform first = new AffineTransform();
        first.scale(-1, 1);
        AffineTransform toCenter = new AffineTransform();
        toCenter.translate(-(area.getBounds().x + area.getBounds().width) * 2, 0);
        AffineTransform tot = new AffineTransform();
        tot.concatenate(first);
        tot.concatenate(toCenter);
        return tot.createTransformedShape(area);
    }

    public void setMargin(int margin) {
        this.MARGIN = margin;
    }

    public void updateFromArea(Area cutArea) {
        this.cutArea = cutArea;
        repaint();
    }

    /**
     * Creates new form LivePreviewPanel
     */
    public LivePreviewPanel() {
        initComponents();
        cutArea = new Area();
    }

    public boolean getLivePreview() {
        return isLivePreview;
    }

    public void setLivePreview(boolean value) {
        this.isLivePreview = value;
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        livePreview = new javax.swing.JCheckBox();

        livePreview.setText("Live Preview");
        livePreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                livePreviewActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(100, 100, 100));
        setPreferredSize(new java.awt.Dimension(400, 200));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void livePreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_livePreviewActionPerformed
        this.isLivePreview = !this.isLivePreview;
    }//GEN-LAST:event_livePreviewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox livePreview;
    // End of variables declaration//GEN-END:variables

    public String selectPngFile() {
        JFileChooser fc = new JFileChooser();
        MainFrame mf = new MainFrame();
        int returnVal = fc.showSaveDialog(mf);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.currentPngFile = fc.getSelectedFile();
            System.out.println("name:\t" + currentPngFile.getName());
            return currentPngFile.getName() + "";
        } else {
            System.out.println("File doesn't exists.");
            this.currentPngFile = null;
            return null;
        }

    }

    public String selectSvgFile() {
        JFileChooser fc = new JFileChooser();
        MainFrame mf = new MainFrame();
        int returnVal = fc.showSaveDialog(mf);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.currentSvgFile = fc.getSelectedFile();
            System.out.println("name:\t" + currentSvgFile.getName());
            return currentSvgFile.getName();
        } else {
            System.out.println("File doesn't exists.");
            this.currentPngFile = null;
            return null;
        }
    }
}
