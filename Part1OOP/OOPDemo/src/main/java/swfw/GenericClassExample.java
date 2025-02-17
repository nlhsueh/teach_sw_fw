package swfw;

public class GenericClassExample {
    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>(); // 指定 T 為 Integer
        intBox.setValue(100);
        System.out.println(intBox.getValue());

        Box<String> strBox = new Box<>(); // 指定 T 為 String
        strBox.setValue("Hello Generics");
        System.out.println(strBox.getValue());

        MathUtil<Integer> intMath = new MathUtil<>(10);
        System.out.println(intMath.square());
    }

}

class Box<T> {
    private T value; // value 的類型是 T

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

class MathUtil<T extends Number> {
    private T number;

    public MathUtil(T number) {
        this.number = number;
    }

    public double square() {
        return number.doubleValue() * number.doubleValue();
    }
}


