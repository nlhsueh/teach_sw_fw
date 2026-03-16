package swfw.ch02.ex2_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FruitParser extends StringTokenizer {
    String[] fruit_set = { "apple", "avocado", "banana", "cherry", "coconut", "jujube", "durian", "grape", "grapefruit",
            "guava", "lemon", "lichee", "orange", "kiwi" };

    public FruitParser(String str) {
        super(str, " ,.");
    }

    public String[] getFruits() {
        ArrayList<String> foundFruits = new ArrayList<>();
        while (hasMoreTokens()) {
            String token = nextToken();
            if (Arrays.asList(fruit_set).contains(token)) {
                foundFruits.add(token);
            }
        }
        return foundFruits.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String s = "I like apple, banana, and orange. Marry like kiwi";
        FruitParser f = new FruitParser(s);
        String[] fruits = f.getFruits();
        System.out.println(Arrays.toString(fruits));
    }
}
