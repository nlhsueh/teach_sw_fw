package swfw.ch02.ex2_2;

abstract class People {
    protected double height; // meters
    protected double weight; // kg

    public People(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }
    
    public double getBmi() {
        return weight / (height * height);
    }

    public abstract boolean overWeight();
}

class Student extends People {
    public Student(double height, double weight) {
        super(height, weight);
    }

    @Override
    public boolean overWeight() {
        return getBmi() > 24;
    }
}

class Athlete extends People {
    public Athlete(double height, double weight) {
        super(height, weight);
    }

    @Override
    public boolean overWeight() {
        return getBmi() > 22;
    }
}

public class Exercise2_2_1 {
    public static void main(String[] args) {
        People student = new Student(1.75, 75); // BMI = 75 / (1.75^2) = 24.48 > 24 => true
        People athlete = new Athlete(1.80, 70); // BMI = 70 / (1.8^2) = 21.60 < 22 => false

        System.out.println("Student overweight: " + student.overWeight());
        System.out.println("Athlete overweight: " + athlete.overWeight());
    }
}
