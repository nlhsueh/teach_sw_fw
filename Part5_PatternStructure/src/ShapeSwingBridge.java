import javax.swing.*;
import java.awt.*;

public class ShapeSwingBridge extends JFrame {
    private Shape square;

    public ShapeSwingBridge() {
        super("Bridge Pattern in Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        // Bridge connection: Abstraction (Square) + Implementor (LineDrawingShapeImpl)
        ShapeImpl lineDrawer = new LineDrawingShapeImpl();
        square = new Square(lineDrawer, 50, 50, 100); 

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                square.draw(g);
            }
        };
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ShapeSwingBridge();
    }
}

// Abstraction
abstract class Shape {
    protected ShapeImpl impl;
    public Shape(ShapeImpl impl) {
        this.impl = impl;
    }
    abstract void draw(Graphics g);
}

// Refined Abstraction
class Square extends Shape {
    private int x, y, size;
    public Square(ShapeImpl impl, int x, int y, int size) {
        super(impl);
        this.x = x; this.y = y; this.size = size;
    }
    @Override
    void draw(Graphics g) {
        impl.drawLine(g, x, y, x + size, y);
        impl.drawLine(g, x + size, y, x + size, y + size);
        impl.drawLine(g, x + size, y + size, x, y + size);
        impl.drawLine(g, x, y + size, x, y);
    }
}

// Implementor
interface ShapeImpl {
    void drawLine(Graphics g, int x1, int y1, int x2, int y2);
}

// Concrete Implementor 1: Solid Line
class LineDrawingShapeImpl implements ShapeImpl {
    @Override
    public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }
}

// Concrete Implementor 2: Dashed Line
class DashedLineShapeImpl implements ShapeImpl {
    private static final float[] DASH_PATTERN = {5.0f, 5.0f}; 
    private static final BasicStroke DASHED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_PATTERN, 0.0f);

    @Override
    public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g.create(); 
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(DASHED_STROKE);
        g2d.drawLine(x1, y1, x2, y2);
        g2d.setStroke(originalStroke); 
        g2d.dispose(); 
    }
}
