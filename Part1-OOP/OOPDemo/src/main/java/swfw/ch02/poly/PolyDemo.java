package swfw.ch02.poly;

/**
 * 展示多型 (Polymorphism) 與 動態綁定 (Dynamic Binding)。
 */
abstract class Shape {
    public abstract void draw();
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("畫一個圓形 ◯");
    }
}

class Square extends Shape {
    @Override
    public void draw() {
        System.out.println("畫一個正方形 □");
    }
}

public class PolyDemo {
    public static void main(String[] args) {
        // 編譯時型態是 Shape, 執行時型態是 Circle/Square
        Shape s1 = new Circle();
        Shape s2 = new Square();

        System.out.print("s1.draw(): ");
        s1.draw(); // 動態綁定到 Circle.draw()

        System.out.print("s2.draw(): ");
        s2.draw(); // 動態綁定到 Square.draw()

        // 多型陣列應用
        Shape[] shapes = { s1, s2, new Circle() };
        System.out.println("\n批次繪製圖形:");
        for (Shape s : shapes) {
            s.draw();
        }
    }
}
