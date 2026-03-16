package swfw.ch02.ex2_1;

class Animal {
    public void speak() {
        System.out.println("Animal speaks");
    }
}

class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    public void speak() {
        System.out.println("Cat meows");
    }
}

public class Exercise2_1_1 {
    public static void testAnimal(Animal a) {
        if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.speak();
        } else {
            System.out.println("Not a Dog");
        }
    }

    public static void main(String[] args) {
        Animal a = new Dog();
        a.speak(); // "Dog barks"
        
        System.out.println("--- testAnimal(Dog) ---");
        testAnimal(a);
        
        System.out.println("--- testAnimal(Cat) ---");
        Animal c = new Cat();
        testAnimal(c);
    }
}
