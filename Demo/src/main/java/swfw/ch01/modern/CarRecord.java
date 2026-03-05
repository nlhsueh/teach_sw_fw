package swfw.ch01.modern;

/**
 * 展示 Java 14+ 引入的 Record 特性。
 * Record 是一種特殊的類別，專門用於儲存不可變的資料。
 * 它會自動生成 constructor, getter (名稱與欄位同), equals, hashCode, 與 toString。
 */
public record CarRecord(String brand, String color, int price) {
    // 可以在 record 中加入自定義方法
    public String getDescription() {
        return brand + " (" + color + ")";
    }

    public static void main(String[] args) {
        CarRecord myCar = new CarRecord("Toyota", "Red", 800000);

        // 存取方法名稱與欄位相同，不帶 get 前綴
        System.out.println("品牌: " + myCar.brand());
        System.out.println("描述: " + myCar.getDescription());

        // 自動生成的 toString
        System.out.println("物件內容: " + myCar);
    }
}
