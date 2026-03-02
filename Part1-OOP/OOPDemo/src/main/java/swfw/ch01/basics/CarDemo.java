package swfw.ch01.basics;

import java.util.ArrayList;
import java.util.List;

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
        // 1. 建立多個物件並加入 List
        List<Car> carList = new ArrayList<>();

        carList.add(new Car("Toyota", "紅色"));
        carList.add(new Car("BMW", "黑色"));
        carList.add(new Car("Tesla", "白色"));

        // 2. 使用傳統 for 迴圈走訪
        System.out.println("--- 使用傳統 for 迴圈 ---");
        for (int i = 0; i < carList.size(); i++) {
            carList.get(i).displayInfo();
        }

        // 3. 使用增強型 for 迴圈 (for-each) 走訪
        System.out.println("\n--- 使用增強型 for 迴圈 ---");
        for (Car car : carList) {
            car.displayInfo();
        }
    }
}
