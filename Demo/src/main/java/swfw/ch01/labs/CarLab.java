package swfw.ch01.labs;

/**
 * 練習 1.2.1：汽車加速與剎車控制 (Car)
 * 
 * 要求：
 * 1. 屬性 model (String) 與 speed (int) 需封裝。
 * 2. 實作 accelerate(int increment) 與 brake(int decrement)。
 * 3. 確保 speed 永遠不會低於 0。
 */
class Car {
    private String model;
    private int speed;

    public Car(String model) {
        this.model = model;
        this.speed = 0;
    }

    public void accelerate(int increment) {
        if (increment > 0) {
            speed += increment;
            System.out.println(model + " 加速中... 目前時速: " + speed);
        }
    }

    public void brake(int decrement) {
        if (decrement > 0) {
            speed -= decrement;
            if (speed < 0)
                speed = 0;
            System.out.println(model + " 減速中... 目前時速: " + speed);
        }
    }

    public void displayStatus() {
        System.out.println("車型: " + model + ", 目前時速: " + speed);
    }
}

public class CarLab {
    public static void main(String[] args) {
        Car myCar = new Car("Toyota GR86");
        myCar.displayStatus();

        System.out.println("模擬加速...");
        myCar.accelerate(50);
        myCar.accelerate(30);

        System.out.println("模擬剎車...");
        myCar.brake(40);
        myCar.brake(60); // 測試不能低於 0

        myCar.displayStatus();
    }
}
