package swfw.ch01.basics;

/**
 * 練習 1.5.3：實例初始化區塊與建構子的執行順序
 */
public class Counter {
    private int count;

    // 實例初始化區塊
    {
        count = 100;
        System.out.println("實例初始化區塊：count 設定為 " + count);
    }

    public Counter() {
        System.out.println("建構子：count 的值為 " + count);
    }

    public static void main(String[] args) {
        System.out.println("--- 開始建立 Counter 物件 ---");
        Counter c = new Counter();
        System.out.println("--- Counter 物件建立完成 ---");
    }
}
