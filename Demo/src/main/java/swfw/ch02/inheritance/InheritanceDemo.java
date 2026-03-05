package swfw.ch02.inheritance;

/**
 * 展示繼承 (Inheritance) 與 方法覆寫 (Overriding)。
 */
class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println("某種動物在叫...");
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name); // 呼叫父類別建構子
    }

    @Override
    public void speak() {
        System.out.println(name + " 汪汪叫！");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        Animal a = new Animal("生物");
        a.speak();

        Dog d = new Dog("小黑");
        d.speak();
    }
}
