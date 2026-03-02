package swfw.ch01.basics;

/**
 * 演示 類別（Class）與 物件（Object）的基本定義與建立。
 */
class Car {
    // 屬性（Attributes）
    String brand; // 汽車品牌
    String color; // 顏色

    // 建構子（Constructor）
    Car(String brand, String color) {
        this.brand = brand;
        this.color = color;
    }

    // 方法（Methods）
    void displayInfo() {
        System.out.println("這是一台 " + color + " 的 " + brand);
    }
}

public class CarDemo {
    public static void main(String[] args) {
        // 建立物件
        Car myCar = new Car("Toyota", "紅色");

        // 呼叫物件的方法
        myCar.displayInfo();
    }
}
