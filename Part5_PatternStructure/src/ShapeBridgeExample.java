public class ShapeBridgeExample {
    public static void main(String args[]) {
        System.out.println("Rectangle, 2D");
        Shape r1 = new Rectangle(1, 1, 2, 3, new Draw2D());
        r1.draw();
        
        System.out.println("Rectangle, 3D");
        Shape r2 = new Rectangle(1, 1, 2, 3, new Draw3D());
        r2.draw();
            
        System.out.println("Triangle, 3D");
        Shape s1 = new Triangle(1, 1, 2, 3, 5, 10, new Draw3D());
        s1.draw();

        System.out.println("Triangle, 2D");            
        Shape s2 = new Triangle(1, 1, 2, 3, 5, 10, new Draw2D());
        s2.draw();
    }
}

// Abstraction
abstract class Shape {
    protected ShapeImp impl;
    
    public Shape(ShapeImp impl) {
        this.impl = impl;
    }
    
    abstract void draw();
    
    public void drawLine(int x1, int y1, int x2, int y2) {
        impl.drawLine(x1, y1, x2, y2);
    }    
}    

// Refined Abstraction 1
class Rectangle extends Shape { 
    int x, y, w, t;
    
    public Rectangle(int a, int b, int w, int t, ShapeImp imp) {
        super(imp);
        x = a; y = b; this.w = w; this.t = t;        
    }

    @Override
    public void draw() { 
        drawLine(x, y, x + w, y);
        drawLine(x + w, y, x + w, y + t);
        drawLine(x + w, y + t, x, y + t);
        drawLine(x, y + t, x, y);
    }
}

// Refined Abstraction 2
class Triangle extends Shape {
    int x1, x2, x3, y1, y2, y3;
    
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, ShapeImp imp) {
        super(imp);
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }    
    
    @Override
    public void draw() { 
        drawLine(x1, y1, x2, y2);
        drawLine(x2, y2, x3, y3);
        drawLine(x3, y3, x1, y1);        
    } 
}

// Implementor Interface
interface ShapeImp {
    public void drawLine(int x1, int y1, int x2, int y2);
}

// Concrete Implementors
class Draw2D implements ShapeImp {
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
       System.out.println("2D " + x1 + "," + y1 + "," + x2 + "," + y2);
    }
}

class Draw3D implements ShapeImp {
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
       System.out.println("3D " + x1 + "," + y1 + "," + x2 + "," + y2);
    }    
}
