package swfw;

import swfw.ch01.basics.CarDemo;
import swfw.ch01.basics.EncapsulationDemo;
import swfw.ch01.basics.PrivacyLeakDemo;
import swfw.ch01.basics.WrapperDemo;
import swfw.ch01.modern.CarRecord;
import swfw.ch01.relationships.HeroEquipmentDemo;
import swfw.ch02.inheritance.InheritanceDemo;
import swfw.ch02.poly.PolyDemo;
import swfw.ch02.modern.SealedShapeDemo;
import swfw.ch02.interfaces.InterfaceDemo;
import swfw.generics.GenericClassExample;
import swfw.labs.lab01.StudentLab;
import swfw.labs.lab01.BankAccountLab;
import swfw.labs.lab01.CarLab;
import swfw.labs.lab02.RPGGameLab;
import swfw.labs.lab02.ShapeAreaLab;
import swfw.labs.lab02.PeopleBMILab;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n======== OOP 教材範例程式目錄 ========");
            System.out.println("--- Chapter 1: OBP 基礎 ---");
            System.out.println("1) 基礎物件 (Class & Object)");
            System.out.println("2) 封裝演示 (Encapsulation)");
            System.out.println("3) 原生型態與包裝類別 (Wrapper Classes)");
            System.out.println("4) 隱私洩漏與防禦性複製 (Privacy Leak)");
            System.out.println("5) Java Records (現代特性)");
            System.out.println("6) 物件關聯 (Hero & Equipment)");
            System.out.println("--- Chapter 2: OOP 進階 ---");
            System.out.println("7) 繼承與覆寫 (Inheritance)");
            System.out.println("8) 多型與動態綁定 (Polymorphism)");
            System.out.println("9) 密封類別 (Sealed Classes)");
            System.out.println("10) 介面實作 (Interfaces)");
            System.out.println("--- Lab Exercises (實習課練習) ---");
            System.out.println("L1) Lab 01: 學生封裝練習 (Student)");
            System.out.println("L2) Lab 01: 銀行帳戶練習 (BankAccount)");
            System.out.println("L3) Lab 01: 汽車控制練習 (Car)");
            System.out.println("L4) Lab 02: RPG 多型練習 (Character)");
            System.out.println("L5) Lab 02: 圖形面積練習 (Shape)");
            System.out.println("L6) Lab 02: 健康檢查練習 (BMI)");
            System.out.println("--- Others ---");
            System.out.println("11) 泛型範例 (Generics)");
            System.out.println("0) 結束程式");
            System.out.print("請選擇要執行的範例: ");

            if (!scanner.hasNextLine())
                break;
            String input = scanner.nextLine().toUpperCase();
            if ("0".equals(input))
                break;

            try {
                switch (input) {
                    case "1" -> CarDemo.main(null);
                    case "2" -> EncapsulationDemo.main(null);
                    case "3" -> WrapperDemo.main(null);
                    case "4" -> PrivacyLeakDemo.main(null);
                    case "5" -> CarRecord.main(null);
                    case "6" -> HeroEquipmentDemo.main(null);
                    case "7" -> InheritanceDemo.main(null);
                    case "8" -> PolyDemo.main(null);
                    case "9" -> SealedShapeDemo.main(null);
                    case "10" -> InterfaceDemo.main(null);
                    case "11" -> GenericClassExample.main(null);
                    case "L1" -> StudentLab.main(null);
                    case "L2" -> BankAccountLab.main(null);
                    case "L3" -> CarLab.main(null);
                    case "L4" -> RPGGameLab.main(null);
                    case "L5" -> ShapeAreaLab.main(null);
                    case "L6" -> PeopleBMILab.main(null);
                    default -> System.out.println("無效的選擇，請重新輸入。");
                }
            } catch (Exception e) {
                System.out.println("執行範例時發生錯誤: " + e.getMessage());
            }
        }
        System.out.println("程式結束。");
    }
}