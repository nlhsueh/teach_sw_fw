package swfw.ch02.ex2_4;

interface Shape {
    double getArea();
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double getArea() { return Math.PI * radius * radius; }
}

class Square implements Shape {
    private double side;
    public Square(double side) { this.side = side; }
    public double getArea() { return side * side; }
}

class Rectangle implements Shape {
    private double width, height;
    public Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double getArea() { return width * height; }
}

class Triangle implements Shape {
    private double base, height;
    public Triangle(double base, double height) { this.base = base; this.height = height; }
    public double getArea() { return 0.5 * base * height; }
}

public class Exercise2_4_1 {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Square(4),
            new Rectangle(3, 6),
            new Triangle(4, 5)
        };
        for (Shape s : shapes) {
            System.out.println(s.getClass().getSimpleName() + " Area: " + s.getArea());
        }
    }
}
