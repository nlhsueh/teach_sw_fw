package swfw.ch02.modern;

/**
 * 展示 Java 17 引入的密封類別 (Sealed Classes)。
 * 允許開發者控制哪些子類別可以繼承特定的類別。
 */

// 定義密封介面，只允許 CreditCard 與 Cash 實作
sealed interface Payment permits CreditCard, Cash {
    void pay(int amount);
}

final class CreditCard implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("使用信用卡支付了 " + amount + " 元");
    }
}

final class Cash implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("使用現金支付了 " + amount + " 元");
    }
}

// 如果在此嘗試建立一個新類別實作 Payment，編譯器會報錯
// class PayPal implements Payment { }

public class SealedShapeDemo {
    public static void main(String[] args) {
        Payment p1 = new CreditCard();
        Payment p2 = new Cash();

        p1.pay(1000);
        p2.pay(500);
    }
}
