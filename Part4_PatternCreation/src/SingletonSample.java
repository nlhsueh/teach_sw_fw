package singleton;

public class Singleton {
    // 宣告為 static 讓物件唯一
    private static Singleton uniqueInstance = null;
    private int data = 0;

    // 把一般的建構子宣告為私有
    private Singleton() {}

    public static Singleton instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

    public void setData(int d) { this.data = d; }
    public int getData() { return this.data; }
}

class TestSingleton {
    public static void main(String args[]) {
        Singleton s = Singleton.instance();
        s.setData(34);
        System.out.println("s 的參考為：" + s);
        System.out.println("s 的值為：" + s.getData());

        Singleton s2 = Singleton.instance();
        System.out.println("s2 的參考為：" + s2);
        System.out.println("s2 的值為：" + s2.getData());
    }
}
