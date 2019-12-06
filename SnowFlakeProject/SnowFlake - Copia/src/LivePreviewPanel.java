
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nicho
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
     * Le coordinate di punti y del triangolo modello.
     */
    private int[] xPoints;
    
    /**
     * Le coordinate di punti y del triangolo modello.
     */
    private int[] yPoints;

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isLivePreview) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.translate(MARGIN, 0);
            g2.fill(this.cutArea);
            //g.fillRect(465 - 100, 22, 35, 60);
            //for(int i = 0; i < 6; i++){
            //System.out.println("cutArea" + cutArea.getBounds());
            g2.setColor(Color.RED);
            //System.out.println(getFlippedArea(cutArea).getBounds());
            g2.fill(getFlippedArea(cutArea));
            g2.setColor(Color.GREEN);
            //System.out.println(getRotateArea(90, cutArea).getBounds());
            g2.fill(getRotateArea(60, cutArea));
            //Area tempArea = (Area)getFlippedArea(cutArea);
            g2.fill(getRotateArea(90, getFlippedArea(cutArea)));
            
        }
    }

    private Shape getRotateArea(int angle, Shape shape) {
        AffineTransform af = new AffineTransform();
        af.rotate(Math.toRadians(angle), (shape.getBounds().x+shape.getBounds().width), (shape.getBounds().x+shape.getBounds().width));
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
        toCenter.translate(-(area.getBounds().x+area.getBounds().width) * 2, 0);
        AffineTransform tot = new AffineTransform();
        tot.concatenate(first);
        tot.concatenate(toCenter);
        return tot.createTransformedShape(area);
    }

    public void setPoints(int[] xPoints, int[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }
    
    public void setMargin() {
        this.MARGIN = MARGIN;
    }

    public void updateFromArea(Area cutArea) {
        this.cutArea = cutArea;
        System.out.println("generate");
        repaint();

    }

    /**
     * Creates new form LivePreviewPanel
     */
    public LivePreviewPanel() {
        initComponents();
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
}
