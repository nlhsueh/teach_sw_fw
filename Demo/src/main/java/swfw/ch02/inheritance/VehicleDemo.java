package swfw.ch02.inheritance;

/**
 * VehicleDemo 示範抽象類別與繼承的應用。
 */
abstract class Vehicle { // 抽象類別
    private String ID;

    public Vehicle(String ID) {
        this.ID = ID;
    }

    public abstract void turnLeft(); // 抽象方法

    public abstract void turnRight(); // 抽象方法

    public String getID() { // 具體方法
        return ID;
    }
}

class Bike extends Vehicle {
    public Bike(String ID) {
        super(ID);
    }

    @Override
    public void turnLeft() {
        System.out.println("腳踏車 (" + getID() + ") 向左轉：擺動龍頭。");
    }

    @Override
    public void turnRight() {
        System.out.println("腳踏車 (" + getID() + ") 向右轉：擺動龍頭。");
    }
}

class Car extends Vehicle {
    public Car(String ID) {
        super(ID);
    }

    @Override
    public void turnLeft() {
        System.out.println("汽車 (" + getID() + ") 向左轉：轉動方向盤。");
    }

    @Override
    public void turnRight() {
        System.out.println("汽車 (" + getID() + ") 向右轉：轉動方向盤。");
    }

    public void backward() { // new method
        System.out.println("汽車 (" + getID() + ") 正在倒車。");
    }
}

public class VehicleDemo {
    public static void main(String[] args) {
        Vehicle myBike = new Bike("B001");
        Vehicle myCar = new Car("C001");

        System.out.println("測試腳踏車：");
        myBike.turnLeft();
        myBike.turnRight();

        System.out.println("\n測試汽車：");
        myCar.turnLeft();
        myCar.turnRight();

        // 呼叫汽車特有的方法
        if (myCar instanceof Car) {
            ((Car) myCar).backward();
        }
    }
}
