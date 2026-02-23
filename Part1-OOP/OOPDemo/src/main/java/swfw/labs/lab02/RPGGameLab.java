package swfw.labs.lab02;

/**
 * 練習 2.2.1：繼承、多型與介面進階練習 (RPG)
 * 
 * 任務：
 * 1. 閱讀 Character 抽象類別與其抽象方法 skill()。
 * 2. 實作 Warrior (戰士) 類別繼承 Character，覆寫 skill 回傳 "斬擊"。
 * 3. 實作 Mage (法師) 類別繼承 Character，覆寫 skill 回傳 "火球術"。
 * 4. 在主程式中使用多型 (Polymorphism) 陣列，讓不同職業輪流施放技能。
 */

abstract class Character {
    protected String name;

    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 抽象方法：每種職業都有不同的技能
    public abstract String skill();
}

class Warrior extends Character {
    public Warrior(String name) {
        super(name);
    }

    // TODO: 2. 實作 skill 方法，回傳 "斬擊"
    @Override
    public String skill() {
        return "待實作";
    }
}

// TODO: 3. 實作 Mage 類別承 Character
class Mage extends Character {
    public Mage(String name) {
        super(name);
    }

    @Override
    public String skill() {
        // 請在此實作...
        return "待實作";
    }
}

public class RPGGameLab {
    public static void main(String[] args) {
        System.out.println("--- Lab 02: RPG 技能演示 ---");

        // TODO: 4. 利用多型建立角色陣列 (包含一戰士、一法師)
        // Character[] party = { ... };

        /*
         * for (Character c : party) {
         * System.out.println(c.getName() + " 使用了 " + c.skill() + "!");
         * }
         */

        System.out.println("(請依照 TODO 完成程式碼後取消註釋進行測試)");
    }
}
