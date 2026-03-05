package swfw.ch02.labs;

/**
 * 練習 2.2.1：不同族群的健康檢查 (People BMI)
 * 
 * 要求：
 * 1. 定義抽象類別 People，包含 height, weight。
 * 2. 定義抽象方法 isOverweight(double bmi)。
 * 3. Student (BMI > 24 為過重), Athlete (BMI > 22 為過重)。
 */
abstract class People {
    protected String name;
    protected double height; // meter
    protected double weight; // kg

    public People(String name, double height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public double calculateBMI() {
        return weight / (height * height);
    }

    public abstract boolean isOverweight();

    public void checkHealth() {
        double bmi = calculateBMI();
        System.out.printf("[%s] BMI: %.2f, 狀態: %s\n",
                name, bmi, isOverweight() ? "過重" : "正常");
    }
}

class Student extends People {
    public Student(String name, double h, double w) {
        super(name, h, w);
    }

    @Override
    public boolean isOverweight() {
        return calculateBMI() > 24;
    }
}

class Athlete extends People {
    public Athlete(String name, double h, double w) {
        super(name, h, w);
    }

    @Override
    public boolean isOverweight() {
        return calculateBMI() > 22;
    }
}

public class PeopleBMILab {
    public static void main(String[] args) {
        People p1 = new Student("小明", 1.75, 75);
        People p2 = new Athlete("阿強", 1.80, 75);

        System.out.println("--- BMI 健康檢查 (多型演示) ---");
        p1.checkHealth();
        p2.checkHealth();
    }
}
