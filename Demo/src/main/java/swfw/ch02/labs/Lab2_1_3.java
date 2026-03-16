package swfw.ch02.labs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab2_1_3 {
    public static List<String> parseFruits(String input) {
        String[] fruits = input.split(",");
        return new ArrayList<>(Arrays.asList(fruits));
    }

    public static void main(String[] args) {
        String input = "Apple,Banana,Cherry,Date";
        List<String> fruitList = parseFruits(input);
        System.out.println("Parsed fruits: " + fruitList);
    }
}
