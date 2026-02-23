package swfw;

import swfw.ch01.basics.EncapsulationDemo;
import swfw.ch01.modern.CarRecord;
import swfw.ch01.relationships.HeroEquipmentDemo;
import swfw.ch02.inheritance.InheritanceDemo;
import swfw.ch02.poly.PolyDemo;
import swfw.ch02.modern.SealedShapeDemo;
import swfw.ch02.interfaces.InterfaceDemo;
import swfw.generics.GenericClassExample;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n======== OOP 教材範例程式目錄 ========");
            System.out.println("--- Chapter 1: OBP 基礎 ---");
            System.out.println("1) 封裝演示 (Encapsulation)");
            System.out.println("2) Java Records (現代特性)");
            System.out.println("3) 物件關聯 (Hero & Equipment)");
            System.out.println("--- Chapter 2: OOP 進階 ---");
            System.out.println("4) 繼承與覆寫 (Inheritance)");
            System.out.println("5) 多型與動態綁定 (Polymorphism)");
            System.out.println("6) 密封類別 (Sealed Classes)");
            System.out.println("7) 介面實作 (Interfaces)");
            System.out.println("--- Others ---");
            System.out.println("8) 泛型範例 (Generics)");
            System.out.println("0) 結束程式");
            System.out.print("請選擇要執行的範例: ");

            if (!scanner.hasNextLine())
                break;
            String input = scanner.nextLine();
            if ("0".equals(input))
                break;

            try {
                switch (input) {
                    case "1" -> EncapsulationDemo.main(null);
                    case "2" -> CarRecord.main(null);
                    case "3" -> HeroEquipmentDemo.main(null);
                    case "4" -> InheritanceDemo.main(null);
                    case "5" -> PolyDemo.main(null);
                    case "6" -> SealedShapeDemo.main(null);
                    case "7" -> InterfaceDemo.main(null);
                    case "8" -> GenericClassExample.main(null);
                    default -> System.out.println("無效的選擇，請重新輸入。");
                }
            } catch (Exception e) {
                System.out.println("執行範例時發生錯誤: " + e.getMessage());
            }
        }
        System.out.println("程式結束。");
    }
}