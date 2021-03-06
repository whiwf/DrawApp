/**
 * PaintPanel
 */
package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.SwingUtilities;
import model.NewShape;

public class Paint extends javax.swing.JPanel implements MouseMotionListener, MouseListener {

    /**
     * Creates new form Paint
     */
    private Color fillColor, currentColor;
    private NewShape s;
    private int inkPanelWidth;
    private int inkPanelHeight;
    private Main frame;
    private Stack<NewShape> shapes;
    private Stack<NewShape> preview;
    private int x1, x2, y1, y2;
    private BasicStroke stroke = new BasicStroke((float) 1);
    private Graphics2D graphics2D;
    BufferedImage canvas;
    private boolean dragged = false;
    private boolean transparent;
    private int activeTool = 0;

    private final int PENCIL_TOOL = 0;
    private final int LINE_TOOL = 1;
    private final int RECTANGLE_TOOL = 2;
    private final int CIRCLE_TOOL = 3;
    private final int ERASE_TOOL = 4;
    private final int CLEAR_TOOL = 5;

    private final int LINE = 1;
    private final int RECTANGLE = 2;
    private final int CIRCLE = 3;

    public Paint(Main frame) {
        initComponents();
        setBackground(Color.WHITE);
        setLocation(10, 10);
        currentColor = Color.BLACK;
        this.fillColor = Color.white;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        inkPanelWidth = dim.width - 150;
        inkPanelHeight = dim.height - 160;
        addMouseMotionListener(this);
        this.frame = frame;
        this.shapes = new Stack<NewShape>();
        this.preview = new Stack<NewShape>();

        printPaintPanelSize(inkPanelWidth, inkPanelHeight);
    }

    public void printCoords(MouseEvent e) {
        String posX = String.valueOf((int) e.getPoint().getX());
        String posY = String.valueOf((int) e.getPoint().getY());
        frame.getCoordinateBar().getCoordinates().setText(posX + ",  " + posY + " px");
    }

    public void printPaintPanelSize(int width, int height) {
        this.frame.getCoordinateBar().getFrameSize().setText(width + ",  " + height + " px");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        Color primary = currentColor;
        Color secondary = fillColor;
        if (SwingUtilities.isRightMouseButton(evt)) {
            primary = secondary;
            secondary = currentColor;
        }

        if (activeTool == LINE_TOOL && dragged) {
            shapes.push(new NewShape(x1, y1, x2, y2, primary, stroke, LINE_TOOL, fillColor, transparent));
            repaint();
            //graphics2D.drawLine(x1, y1, x2, y2);
        } else if (activeTool == RECTANGLE_TOOL && dragged) {

            if (x1 < x2 && y1 < y2) {
                shapes.push(new NewShape(x1, y1, x2 - x1, y2 - y1, primary, stroke, RECTANGLE_TOOL, secondary, transparent));
                //graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
            } else if (x2 < x1 && y1 < y2) {
                shapes.push(new NewShape(x2, y1, x1 - x2, y2 - y1, primary, stroke, RECTANGLE_TOOL, secondary, transparent));
                //	graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
            } else if (x1 < x2 && y2 < y1) {
                shapes.push(new NewShape(x1, y2, x2 - x1, y1 - y2, primary, stroke, RECTANGLE_TOOL, secondary, transparent));
                //graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
            } else if (x2 < x1 && y2 < y1) {
                shapes.push(new NewShape(x2, y2, x1 - x2, y1 - y2, primary, stroke, RECTANGLE_TOOL, secondary, transparent));
                //	graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
            }

        } else if (activeTool == CIRCLE_TOOL && dragged) {
            if (x1 < x2 && y1 < y2) {
                shapes.push(new NewShape(x1, y1, x2 - x1, y2 - y1, primary, stroke, CIRCLE_TOOL, secondary, transparent));
                //	graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
            } else if (x2 < x1 && y1 < y2) {
                shapes.push(new NewShape(x2, y1, x1 - x2, y2 - y1, primary, stroke, CIRCLE_TOOL, secondary, transparent));
                //graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
            } else if (x1 < x2 && y2 < y1) {
                shapes.push(new NewShape(x1, y2, x2 - x1, y1 - y2, primary, stroke, CIRCLE_TOOL, secondary, transparent));
                //graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
            } else if (x2 < x1 && y2 < y1) {
                shapes.push(new NewShape(x2, y2, x1 - x2, y1 - y2, primary, stroke, CIRCLE_TOOL, secondary, transparent));
                //	graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
            }
        }
        dragged = false;
        repaint();
    }//GEN-LAST:event_formMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (canvas == null) {
            canvas = new BufferedImage(inkPanelWidth, inkPanelHeight, BufferedImage.TYPE_INT_ARGB);
            graphics2D = canvas.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(canvas, 0, 0, null);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (NewShape s : shapes) {

            g2.setColor(s.getColor());
            g2.setStroke(s.getStroke());

            if (s.getShape() == LINE) {
                g2.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
            } else if (s.getShape() == RECTANGLE) {

                g2.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                if (s.transparent == false) {
                    g2.setColor(s.getfillColor());
                    g2.fillRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                }
            } else if (s.getShape() == CIRCLE) {
                g2.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                if (s.transparent == false) {
                    g2.setColor(s.getfillColor());
                    g2.fillOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                }

            }
        }
        if (preview.size() > 0) {
            NewShape s = preview.pop();
            g2.setColor(s.getColor());
            g2.setStroke(s.getStroke());
            if (s.getShape() == LINE) {
                g2.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());

            } else if (s.getShape() == RECTANGLE) {

                g2.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                if (s.transparent == false) {
                    g2.setColor(s.getfillColor());
                    g2.fillRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                }
            } else if (s.getShape() == CIRCLE) {
                g2.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                if (s.transparent == false) {
                    g2.setColor(s.getfillColor());
                    g2.fillOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                }
            }

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Color primary = currentColor;
        Color secondary = fillColor;
        if (SwingUtilities.isRightMouseButton(e)) {
            primary = secondary;
            secondary = currentColor;
        }
        printCoords(e);
        x2 = e.getX();
        y2 = e.getY();
        dragged = true;
        if (activeTool == ERASE_TOOL) {
            shapes.push(new NewShape(x1, y1, x2, y2, Color.white, stroke, 1));
            repaint();
            x1 = x2;
            y1 = y2;
        }
        if (activeTool == PENCIL_TOOL) {
            shapes.push(new NewShape(x1, y1, x2, y2, primary, stroke, 1));
            repaint();
            x1 = x2;
            y1 = y2;
        } else if (activeTool == LINE_TOOL) {
            preview.push(new NewShape(x1, y1, x2, y2, primary, stroke, 1, secondary, transparent));
            repaint();
        } else if (activeTool == RECTANGLE_TOOL) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new NewShape(x1, y1, x2 - x1, y2 - y1, primary, stroke, 2, secondary, transparent));
                //graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new NewShape(x2, y1, x1 - x2, y2 - y1, primary, stroke, 2, secondary, transparent));
                //graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new NewShape(x1, y2, x2 - x1, y1 - y2, primary, stroke, 2, secondary, transparent));
                //graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new NewShape(x2, y2, x1 - x2, y1 - y2, primary, stroke, 2, secondary, transparent));
                //graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
            }
            repaint();
        } else if (activeTool == CIRCLE_TOOL) {
            if (x1 < x2 && y1 < y2) {
                preview.push(new NewShape(x1, y1, x2 - x1, y2 - y1, primary, stroke, 3, secondary, transparent));
                //graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
            } else if (x2 < x1 && y1 < y2) {
                preview.push(new NewShape(x2, y1, x1 - x2, y2 - y1, primary, stroke, 3, secondary, transparent));
                //graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
            } else if (x1 < x2 && y2 < y1) {
                preview.push(new NewShape(x1, y2, x2 - x1, y1 - y2, primary, stroke, 3, secondary, transparent));
                //graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
            } else if (x2 < x1 && y2 < y1) {
                preview.push(new NewShape(x2, y2, x1 - x2, y1 - y2, primary, stroke, 3, secondary, transparent));
                //graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
            }
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        printCoords(e);
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("press");
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

    public void setTool(int tool) {
        this.activeTool = tool;
    }

    void setThickness(float f) {
        stroke = new BasicStroke(f);
        graphics2D.setStroke(stroke);
    }

    public void setImage(BufferedImage image) {
        graphics2D.dispose();
        this.setInkPanel(image.getWidth(), image.getHeight());
        canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void setInkPanel(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.printPaintPanelSize(width, height);
        this.setSize(width - 3, height - 3);
        this.setPreferredSize(new Dimension(width - 3, height - 3));
        clear();
    }

    public void setColor(Color c) {
        currentColor = c;
        graphics2D.setColor(c);

    }

    public void setTransparency(Boolean b) {
        this.transparent = b;
    }

    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        shapes.removeAllElements();
        repaint();
        graphics2D.setColor(currentColor);
    }

    void setFillColor(Color background) {
        this.fillColor = background;
    }

    private void floodFill(Point2D.Double point, Color fillColor) {
        Color targetColor = new Color(canvas.getRGB((int) point.getX(), (int) point.getY()));
        Queue<Point2D.Double> queue = new LinkedList<Point2D.Double>();
        queue.add(point);
        if (!targetColor.equals(fillColor));
        while (!queue.isEmpty()) {
            Point2D.Double p = queue.remove();

            if ((int) p.getX() >= 0 && (int) p.getX() < canvas.getWidth()
                    && (int) p.getY() >= 0 && (int) p.getY() < canvas.getHeight()) {
                if (canvas.getRGB((int) p.getX(), (int) p.getY()) == targetColor.getRGB()) {
                    canvas.setRGB((int) p.getX(), (int) p.getY(), fillColor.getRGB());
                    queue.add(new Point2D.Double(p.getX() - 1, p.getY()));
                    queue.add(new Point2D.Double(p.getX() + 1, p.getY()));
                    queue.add(new Point2D.Double(p.getX(), p.getY() - 1));
                    queue.add(new Point2D.Double(p.getX(), p.getY() + 1));
                    //System.out.println("0");
                }
            }
        }
    }
}
