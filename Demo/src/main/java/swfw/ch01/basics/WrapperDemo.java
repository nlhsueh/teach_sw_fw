package swfw.ch01.basics;

import java.util.ArrayList;

/**
 * 演示 Java 包裝類別 (Wrapper Classes) 與 自動裝箱/拆箱 (Auto-boxing/Unboxing)。
 */
public class WrapperDemo {
    public static void main(String[] args) {
        // 自動裝箱：將原生 int 轉換成 Integer 物件
        Integer numObj = 100;
        // 自動拆箱：將 Integer 物件轉換回原生 int
        int num = numObj;
        System.out.println("Unboxed num: " + num);

        // 使用包裝類別的方法：解析字串為整數
        String strNumber = "123";
        int parsedNumber = Integer.parseInt(strNumber);
        System.out.println("Parsed number: " + parsedNumber);

        // 集合中只能使用物件，所以必須使用 Integer 而非 int
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10); // 自動裝箱發生
        numbers.add(20);
        numbers.forEach(n -> System.out.println("Number: " + n));
    }
}
