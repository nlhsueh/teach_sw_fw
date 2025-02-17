package swfw;

public class GenericMethodExample {
    // 定義一個泛型方法
    public static <T> void printArray(T[] array) {
        for (T item : array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"A", "B", "C"};

        printArray(intArray);
        printArray(strArray);

        GenericMethodExample gme = new GenericMethodExample();
        System.out.println(gme.toString(1));
    }

    public double toString(int x) {
        return (double)x;
    }
}
