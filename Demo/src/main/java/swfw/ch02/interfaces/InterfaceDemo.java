package swfw.ch02.interfaces;

/**
 * 展示介面 (Interface) 的定義與實作，包含 Java 8+ 的 default 方法。
 */
interface Flyable {
    void fly(); // 抽象方法

    // Java 8+ 的預設方法
    default void takeoff() {
        System.out.println("正在起飛...");
    }
}

class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("小鳥拍動翅膀在飛翔 ^o^");
    }
}

class Airplane implements Flyable {
    @Override
    public void fly() {
        System.out.println("噴射引擎運轉中，飛機正在巡航 ✈");
    }

    @Override
    public void takeoff() {
        System.out.println("跑道滑行加速中... 飛機升空！");
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        Flyable bird = new Bird();
        Flyable plane = new Airplane();

        bird.takeoff();
        bird.fly();

        System.out.println("---");

        plane.takeoff();
        plane.fly();
    }
}
