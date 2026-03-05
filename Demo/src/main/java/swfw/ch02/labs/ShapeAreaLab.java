package swfw.ch02.labs;

/**
 * 練習 2.1.1：圖形面積計算 (多型應用)
 * 
 * 要求：
 * 1. 建立抽象類別 Shape，定義抽象方法 getArea()。
 * 2. 建立 Circle 與 Rectangle 繼承 Shape 並實作 getArea()。
 * 3. 在 main 中使用向上轉型 (Upcasting) 處理不同圖形。
 */
abstract class LabShape {
    public abstract double getArea();
}

class LabCircle extends LabShape {
    private double radius;

    public LabCircle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

class LabRectangle extends LabShape {
    private double width, height;

    public LabRectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }
}

public class ShapeAreaLab {
    public static void main(String[] args) {
        LabShape[] shapes = {
                new LabCircle(5),
                new LabRectangle(4, 10),
                new LabCircle(2)
        };

        System.out.println("--- 圖形面積計算演示 ---");
        for (LabShape s : shapes) {
            System.out.printf("圖形面積: %.2f\n", s.getArea());
        }
    }
}
