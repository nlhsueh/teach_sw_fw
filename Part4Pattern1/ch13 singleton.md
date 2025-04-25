###### tags: `OOSE`

# Ch13 獨體：獨一無二 (Singleton)

## 13.1 目的與動機

> 確定一個類別只會有一個物件實體，並且提供一個可以存取該物件的統一方法。
>> Ensure a class only has one instance, and provide a global point of access to it


### 動機
有時候我們在系統中只想要一個實體，唯一的一個。例如說我們只需要一個視窗管理員，只需要一個 RadioPlayer (要不然聲音就打架了)，或只需要一個產品的工廠物件（請參考 Abstract factory）。我們希望該唯一的物件很容易的被讀取到；並且確定不會有其他的物件被產生出來。

## 13.2 結構與方法

### 結構


Figure: Singleton Structure:

<img src="https://i.imgur.com/SYp4vz2.png" width=500>


### 優點

控制外界的物件只能參考到同一唯一的一個物件。

#### 沒有繼承樹的獨體

這個設計的技巧在於

- 宣告一個靜態的物件參考；
- 將原有的建構子宣告為私有的；
- 建立另外一個生成物件的方法，通常稱為 instance(), 它判斷是否物件已經生成了，若已生成則不再生成，若未生成則生成一個。


以下是一個範例：

```java=
public class Singleton {
   // 宣告為 static 讓物件唯一   
   private static Singleton uniqueInstance = null;
   private int data =  0;

   public static Singleton instance() {
      if(uniqueInstance == null)
          uniqueInstance = new Singleton();
      return uniqueInstance;
   }
   // 把一般的建構子宣告為私有
   private Singleton() {}
   public void setData(int d) {
        this.data = d;
   }
	   
   public int getData() {
        return this.data;
   }
}
```

主程式

```java=
public class TestSingleton {
   public static void main(String args[]) {
      Singleton s = Singleton.instance();
      s.setData(34);
      System.out.println("s 的參考為：" + s);
      System.out.println("s 的值為：" + s.getData());

      Singleton s2 = Singleton.instance();
      System.out.println("s2 的參考為：" + s2);
      System.out.println("s2 的值為：" + s.getData());
   }
}   
```

執行的結果

```java= 
s 的參考為：Singleton@1cc810
s 的值為：34
s2 的參考為：Singleton@1cc810
s2 的值為：34
```


有上述的例子可以看到，不如我們呼叫多少次 Singleton.instance，回傳的都是相同的物件。

## 13.3 有繼承樹的獨體

如果在一個繼承樹中只允許產生一個物件，該怎麼設計？


- 把該物件的 reference 建立在父類別中，並且宣告為 protected; 如此一來，子類別都可共享這一份物件了。
- 父類別把原建構子宣告為 private, 因為不允許其他物件透過父類別來生成物件：一切都要從子類別來生成。
- 子類別的原有建構子宣告為 private，如此一來，其他物件無法透過建構子「偷生」其他的物件實體了。
- 因為父類別與子類別們都共享同一份物件參考，所以就可以控制只生一個物件了。


### 迷宮範例

不論是 `EnchantedMazeFactory` 或 `AgentMazeFactory` 只能生成一個物件。

```java=
public abstract class MazeFactory {
    //宣告成 protected 這樣子類別才看得到
    protected static MazeFactory uniqueInstance = null;

    //藏起來不讓外界的呼叫。但因為有子類別，不能宣告為 private
    protected MazeFactory() {}

    // Return a reference to the single instance.
    public static MazeFactory instance() {
        return uniqueInstance;
    }
}

public class EnchantedMazeFactory extends MazeFactory {
    //參考到父類別的 uniqueInstance
    public static MazeFactory instance() {
       if(uniqueInstance == null)
          uniqueInstance = new EnchantedMazeFactory();
       return uniqueInstance;
    }
    private EnchantedMazeFactory() {}
}

public class AgentMazeFactory extends MazeFactory {
    //同樣的參考到父類別的 uniqueInstance
    public static MazeFactory instance() {
       if(uniqueInstance == null)
          uniqueInstance = new AgentMazeFactory();
       return uniqueInstance;
    }
    private AgentMazeFactory() {}
}
```

### 比較
Factory method 和 abstract factory 都是討論設計的彈性，希望日後在功能擴充時減少程式碼的修改。Singleton 的目的在於解決設計上的問題，是少數設計樣式中不討論設計彈性的樣式。


## 13.4 Check

1.  Singleton 的目的為
    A) 快速的為陣列內每一個類別產生一個物件
    B) 讓一個類別只能產生一個物件
    C) 讓一個套件只能產生一個類別
    D) 強迫一個類別只能有一個子類別

2.  static method 的定義哪一個錯誤
    A) 不需產生物件就可以呼叫
    B) Singleton 產生物件的方法是呼叫 static method
    C) 必須引用類別中的 instance variable
    D) 可以引用類別中的 static variable

3.  Singleton with subclassing 的意義為
    A) 一個繼承樹上只能產生一個物件
    B) 一個繼承樹上每一個類別都只能產生一個物件

4.  在象棋系統中，「將」「士」等每一個 Chess 都個別只會有一隻，所以 Chess 可以用 Singleton 來設計。
    A) 對
    B) 錯

5.  Singleton 的目的為何？ 

6.  Singleton 主要應用的物件技巧為何？

7.  Singleton 應用在繼承樹時，主要應用的技巧為何？
  
----
參考解答

1.  **Singleton 的目的為**
    **B) 讓一個類別只能產生一個物件**

    **說明：** Singleton 模式的核心目標就是確保一個類別在整個應用程式生命週期中只存在一個實例（物件）。這樣做的目的是為了控制資源的存取，或者在多個組件之間共享某個唯一的狀態。

2.  **static method 的定義哪一個錯誤**
    **C) 必須引用類別中的 instance variable**

    **說明：**
    * **A) 不需產生物件就可以呼叫：** 這是正確的。Static 方法屬於類別本身，可以直接透過類別名稱調用，不需要先創建該類別的物件。
    * **B) Singleton 產生物件的方法是呼叫 static method：** 這在 Singleton 的常見實現中是正確的。通常會提供一個靜態方法（例如 `getInstance()`）來獲取 Singleton 的唯一實例。
    * **C) 必須引用類別中的 instance variable：** 這是**錯誤**的。Static 方法不直接與任何特定的物件實例關聯，因此它們**不能直接**存取非靜態的（instance）成員變數。它們只能存取類別中定義的靜態（static）成員變數。
    * **D) 可以引用類別中的 static variable：** 這是正確的。Static 方法可以存取同一個類別中定義的其他靜態成員變數。

3.  **Singleton with subclassing 的意義為**
    **B) 一個繼承樹上每一個類別都只能產生一個物件**

    **說明：** 當 Singleton 應用於繼承體系時，"Singleton with subclassing" 的目標是確保繼承樹中的**每一個具體的子類別**都只能擁有一個唯一的實例。這與整個繼承樹只允許一個實例（選項 A）不同。

4.  **在象棋系統中，「將」「士」等每一個 Chess 都個別只會有一隻，所以 Chess 可以用 Singleton 來設計。**
    **B) 錯**

    **說明：** 雖然「將」只有一隻，「士」也只有一隻，但它們是**不同種類**的棋子。Singleton 適用於確保**同一個類別**只有一個實例。在這裡，「將」是一個 `King` 類的實例，「士」是一個 `Advisor` 類的實例。`King` 和 `Advisor` 可能是 `Chess` 的子類別，但它們本身是不同的類別，各自應該有它們唯一的實例。因此，直接將 `Chess` 類設計為 Singleton 並不恰當。更適合的做法是確保每個具體的棋子類別（如 `King`、`Advisor` 等）在遊戲中只創建一個實例。

5.  **Singleton 的目的為何？**

    Singleton 的主要目的是**確保一個類別在應用程式的生命週期中只存在一個實例，並提供一個全局唯一的存取點來獲取這個實例**。這有助於控制資源的使用、管理全局狀態，並避免由於創建多個實例而可能導致的不一致性問題。

6.  **Singleton 主要應用的物件技巧為何？**

    Singleton 主要應用的物件技巧包括：
    * **私有建構子 (Private Constructor)：** 防止外部直接使用 `new` 關鍵字創建該類別的實例。
    * **靜態成員變數 (Static Member Variable)：** 用於持有該類別唯一的實例。
    * **靜態方法 (Static Method)：** 提供一個全局唯一的存取點，用於獲取該類的唯一實例。這個方法通常會檢查實例是否已經創建，如果沒有則創建一個，然後返回該實例。

7.  **Singleton 應用在繼承樹時，主要應用的技巧為何？**

    當 Singleton 應用在繼承樹時，主要需要考慮如何確保每個子類別都擁有自己的唯一實例。常見的技巧包括：
    * **為每個具體子類別實現 Singleton 模式：** 每個需要保證單一實例的具體子類別都實現自己的 Singleton 邏輯。
    * **使用註冊表 (Registry) 的方式：** 維護一個註冊表，將每個子類別的唯一實例存儲起來，並提供一個統一的介面來獲取這些實例。這通常涉及到一個基類，它負責管理子類別的實例。

### 13.5 Exercie

### 13.5.1 MusicPlayer
系統要求 MusicPlayer 只能產生一個物件，我們要用 Singleton 設計樣式來實踐，請寫出程式碼。


### 13.5.2 Vehicle
class Vehicle 有兩個子類別 Motor 與 Bike。若我們只想生成一個 Vehicle (不論他是 Motor, Bike)，請利用 Singleton 設計之。

<!-- ### 解答參考

- \ref{sel:sg:objective} (B)
- \ref{sel:sg:static} (C)
- \ref{sel:sg:subclassing} (A)
- \ref{sel:sg:chess} (B), 錯。如果這樣設計，就只能產生一支棋子了。 -->