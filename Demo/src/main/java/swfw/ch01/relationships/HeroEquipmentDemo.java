package swfw.ch01.relationships;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示物件關係：
 * 1. Composition (組合): Hero 與 Backpack (英雄消失，背囊也隨之消失)
 * 2. Aggregation (聚合): Hero 與 Equipment (英雄消失，裝備依然存在於世界中)
 */

class Equipment {
    private String name;

    public Equipment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Backpack {
    private List<Equipment> items = new ArrayList<>();

    public void addItem(Equipment e) {
        items.add(e);
    }

    public List<Equipment> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Backpack" + items;
    }
}

class Hero {
    private String name;
    private Backpack backpack; // Composition: 英雄擁有他的專屬背囊

    public Hero(String name) {
        this.name = name;
        this.backpack = new Backpack(); // 在英雄建立時同時建立背囊
    }

    public void pickup(Equipment e) {
        System.out.println(name + " 撿起了 " + e.getName());
        backpack.addItem(e);
    }

    public void showStatus() {
        System.out.println(name + " 狀態: " + backpack);
    }
}

public class HeroEquipmentDemo {
    public static void main(String[] args) {
        // 裝備獨立存在 (Aggregation)
        Equipment sword = new Equipment("王者之劍");
        Equipment shield = new Equipment("守護之盾");

        Hero hero = new Hero("亞瑟");
        hero.pickup(sword);
        hero.pickup(shield);

        hero.showStatus();

        // 即使 hero 物件被銷毀，sword 和 shield 物件依然存在於 main 的生命週期內。
        // 但 hero 內部的 backpack 物件會隨著 hero 的消失而變得無法存取 (Composition)。
    }
}
