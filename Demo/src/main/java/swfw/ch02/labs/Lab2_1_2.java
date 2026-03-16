package swfw.ch02.labs;

public class Lab2_1_2 {
    String name;
    int age;

    public Lab2_1_2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %d", name, age);
    }

    public static void main(String[] args) {
        Lab2_1_2 p = new Lab2_1_2("Alice", 25);
        System.out.println(p);  
    }
}
