package swfw.ch02.ex2_1;

public class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %d", name, age);
    }

    public static void main(String[] args) {
        Person p = new Person("Alice", 25);
        System.out.println(p);  
    }
}
