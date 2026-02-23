package swfw.ch01.basics;

/**
 * 展示封裝 (Encapsulation) 與 基本物件操作。
 */
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter 包含邏輯驗證
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.out.println("年齡不能為負數！");
        }
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person[name=" + name + ", age=" + age + "]";
    }
}

public class EncapsulationDemo {
    public static void main(String[] args) {
        Person p = new Person("Alice", 20);
        System.out.println("初始狀態: " + p);

        p.setAge(25);
        System.out.println("修改後狀態: " + p);

        p.setAge(-5); // 觸發邏輯驗證
    }
}
