
# **Chapter 1: Object-Based Programming (OBP) – 基礎物件設計**  

**目標：** 介紹物件的基本概念，如何設計類別與物件，並掌握封裝的原則。

## **1.1 物件與類別的基本概念**  

### **1.1.1 什麼是物件（Object）與類別（Class）？**  
- **物件（Object）：** 物件是**具有特定屬性（Attributes）和行為（Behaviors）**的實體。  
  - 例如：「一台汽車」是一個物件，它有**顏色、型號、速度**（屬性），並且能**加速、剎車**（行為）。  
- **類別（Class）：** 類別是**物件的藍圖（Blueprint）或模板**，用來定義物件的屬性與行為。  
  - 例如：「汽車類別（Car）」描述了所有汽車應該具備的屬性（顏色、型號）和行為（加速、剎車），而**每一台特定的汽車**就是這個類別產生的**物件**。

📌 **重點區別：**  
|      | 類別（Class）                      | 物件（Object）                                |
| ---- | ---------------------------------- | --------------------------------------------- |
| 定義 | 一種抽象概念，描述物件的結構與行為 | 具體的實例，由類別建立                        |
| 作用 | 設計藍圖（Blueprint）              | 實際應用（Instance）                          |
| 範例 | `Car` 類別定義所有汽車的基本特徵   | `myCar = new Car("Toyota", "Red")` 是一個物件 |

---

### **1.1.2 Java 的類別與物件範例**  
在 Java 中，類別是用 `class` 關鍵字來定義的，而物件則是用 `new` 關鍵字來建立。  

📌 **範例：定義類別與建立物件**  
```java
// 定義一個類別
class Car {
    // 屬性（Attributes）
    String brand; // 汽車品牌
    String color; // 顏色
    
    // 建構子（Constructor）
    Car(String brand, String color) {
        this.brand = brand;
        this.color = color;
    }
    
    // 方法（Methods）
    void displayInfo() {
        System.out.println("這是一台 " + color + " 的 " + brand);
    }
}

public class Main {
    public static void main(String[] args) {
        // 建立物件
        Car myCar = new Car("Toyota", "紅色");
        
        // 呼叫物件的方法
        myCar.displayInfo();
    }
}
```
📌 **輸出：**  
```
這是一台 紅色 的 Toyota
```

---

### 1.1.3 物件的屬性與行為

**（1）屬性（Attributes，或稱為 Fields, Properties）**  
- 屬性用來存儲物件的狀態，例如汽車的品牌、顏色、速度等資訊。  
- 在 Java 中，屬性通常使用**變數**來表示，例如：  
  ```java
  String brand;  // 品牌
  String color;  // 顏色
  int speed;     // 速度
  ```

**（2）行為（Behavior）**  
- 行為代表物件可以執行的動作，例如汽車可以加速、剎車、變換顏色等。  
- 在 Java 中，行為通常使用**方法（Method）**來實作，例如：  
  ```java
  void accelerate() {
      speed += 10;
      System.out.println("車子加速，現在的速度是：" + speed);
  }
  ```

---

### **1.1.4 物件 vs. 資料結構的差異**
在傳統的 **資料結構** 設計中，資料和行為通常是分開的，例如：
```java
class CarData {
    String brand;
    String color;
}
void paintCar(CarData car, String newColor) {
    car.color = newColor;
}
```
在**物件導向**的設計中，資料和行為被封裝在一起：
```java
class Car {
    String brand;
    String color;

    void repaint(String newColor) {
        this.color = newColor;
    }
}
```
📌 **關鍵區別：**  
|                | 資料結構設計               | 物件導向設計           |
| -------------- | -------------------------- | ---------------------- |
| **資料與行為** | 資料與操作方法分開         | 物件內部包含資料與行為 |
| **可擴展性**   | 變更功能時，須修改多個函式 | 只需修改類別內的方法   |
| **封裝性**     | 易暴露內部結構             | 良好的封裝，降低耦合   |

---

### **1.1.5 物件導向的優勢**
物件導向設計（OOP）帶來幾個重要的好處：
1. **封裝（Encapsulation）：**  
   - 物件內部的屬性可被隱藏，外部只能透過公開的方法來操作，提高安全性。
2. **重用性（Reusability）：**  
   - 一個類別可以被多次使用，減少重複開發。
3. **可擴展性（Extensibility）：**  
   - 可以透過**繼承（Inheritance）**來擴充類別，實現更靈活的設計。
4. **易維護性（Maintainability）：**  
   - 物件導向的程式較容易理解、修改與擴充。

---

### 🎯 總結
✅ **類別（Class）** 是物件的藍圖，定義物件的屬性與行為。  
✅ **物件（Object）** 是類別的實體，每個物件有獨立的屬性與行為。  
✅ 物件導向設計（OOP）提升**封裝性、重用性、可擴展性**，讓程式更易維護。  

### **🔍 觀念測驗（選擇題）**
1️⃣ **關於物件和類別，下列敘述何者正確？**  
   (A) 物件是類別的模板，而類別是物件的實例  
   (B) 類別可以擁有多個物件，但每個物件只能屬於一個類別  
   (C) 物件可以修改類別的定義  
   (D) Java 物件必須在定義類別時就創建  

   **👉 答案：** (B) 類別可以擁有多個物件，但每個物件只能屬於一個類別  

2️⃣ **以下哪一項不屬於物件的特徵？**  
   (A) 屬性（Attributes）  
   (B) 方法（Methods）  
   (C) 記憶體位置（Memory Address）  
   (D) 類別名稱（Class Name）  

   **👉 答案：** (D) 類別名稱（Class Name）  

3️⃣ **當我們用 `new` 關鍵字建立物件時，會發生什麼事情？**  
   (A) 建立一個新的類別  
   (B) 建立一個新的物件並分配記憶體  
   (C) 呼叫類別的靜態方法  
   (D) 將物件存入 `.java` 檔案  

   **👉 答案：** (B) 建立一個新的物件並分配記憶體  

---

### ✍ 練習題

📌 **練習 1：類別與物件的基本使用**  
請撰寫一個 Java 類別 `Person`，該類別應包含：  
- 兩個屬性：`name`（姓名，字串）和 `age`（年齡，整數）。  
- 一個建構子來初始化這些屬性。  
- 一個 `introduce()` 方法，輸出該人的姓名與年齡，例如：  
  ```plaintext
  大家好，我叫 Alice，今年 25 歲。
  ```  
- 在 `main()` 方法中建立兩個 `Person` 物件並呼叫 `introduce()` 方法。  

---

📌 **練習 2：物件的行為**  
請設計一個 `BankAccount` 類別，包含以下內容：  
- 屬性：`accountNumber`（帳號，字串）、`balance`（餘額，浮點數）。  
- 方法：
  - `deposit(double amount)`：存款，增加 `balance`。  
  - `withdraw(double amount)`：提款，若 `balance` 不足則顯示「餘額不足」。  
  - `displayBalance()`：顯示目前的餘額。  
- 在 `main()` 方法中，建立一個帳戶，進行存款、提款與顯示餘額操作。

---

📌 **練習 3：物件 vs. 資料結構**  
在不使用物件導向的情況下（僅使用變數和函式），設計一個程式來管理學生的姓名與分數，並提供一個函式來計算平均分數。  
接著，將程式改寫為物件導向的方式，並比較兩種方式的優缺點。

---

## 1.2 Java 的類別與物件實作細節

### 1.2.1 類別的定義與成員

Java 中的類別由**屬性、方法和建構子** 組成。  
```java
class ClassName {
    // 屬性 (fields)
    資料型別 屬性名稱;
    
    // 建構子 (constructor)
    ClassName(參數) {
        // 初始化屬性
    }
    
    // 方法 (method)
    回傳型別 方法名稱(參數) {
        // 方法邏輯
    }
}
```
**範例：**
```java
class Car {
    String brand;  // 屬性
    int speed;
    
    // 建構子
    Car(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }
    
    // 方法
    void accelerate() {
        speed += 10;
        System.out.println(brand + " 加速中，目前速度：" + speed);
    }
}
```

---

### 1.2.2 建構子與 this
建構子用來初始化物件：
```java
class Person {
    String name;
    
    // 建構子
    Person(String name) {
        this.name = name;  // this 指向當前物件
    }
}
```
📌 **關鍵字 `this` 用來區分屬性與參數**，防止變數名稱衝突。

---

### 1.2.3 方法的回傳與參數
方法可以回傳資料，或接收參數來執行操作：
```java
class MathOperations {
    int add(int a, int b) {
        return a + b;
    }
}
```
**範例：**

```java
MathOperations math = new MathOperations();
int sum = math.add(3, 5);
System.out.println(sum);  // 輸出 8
```

---

### 1.2.4 屬性的封裝與存取修飾子
Java 提供四種存取修飾子：
| 修飾子    | 同類別內 | 同 package | 子類別 | 其他類別 |
| --------- | -------- | ---------- | ------ | -------- |
| private   | ✅        | ❌          | ❌      | ❌        |
| default   | ✅        | ✅          | ❌      | ❌        |
| protected | ✅        | ✅          | ✅      | ❌        |
| public    | ✅        | ✅          | ✅      | ✅        |

📌 範例：
```java
class Person {
    private String name;  // 私有屬性

    // 提供公開的方法存取 name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```
**封裝的好處：**
- **防止不當存取**（避免不合理的值被設定）
- **提高可維護性**（程式更易讀）

---

### 1.2.5 靜態成員（Static）

- `static` 修飾的屬性和方法**屬於類別本身**，不需要建立物件即可使用。
- **適用場景：** 計數器、常數、工具方法等。

📌 **範例：**
```java
class Counter {
    static int count = 0; // 類別變數
    
    Counter() {
        count++;
    }
}
public class Main {
    public static void main(String[] args) {
        new Counter();
        new Counter();
        System.out.println("建立的物件數量：" + Counter.count); // 輸出 2
    }
}
```

---

### 🎯 總結
✅ **類別的結構** 包含屬性、建構子、方法。  
✅ **封裝（Encapsulation）** 使用 `private` 保護屬性，透過 `get/set` 方法存取。  
✅ **靜態成員（Static）** 屬於類別，不需實例化即可使用。

### 🔍 觀念測驗（選擇題）
**1️⃣ 下面哪個關於 Java 類別的描述是正確的？**  
(A) 類別內的 `private` 屬性可以被外部類別直接存取。  
(B) 物件的方法只能使用該物件的屬性，不能修改它們。  
(C) `static` 方法不能存取非靜態成員變數。  
(D) `this` 關鍵字只能用在 `static` 方法內。  

**👉 答案：** (C) `static` 方法不能存取非靜態成員變數。  

---

**2️⃣ `static` 變數的特性是什麼？**  
(A) 每個物件都會有自己獨立的 `static` 變數。  
(B) `static` 變數屬於整個類別，而不是某個特定的物件。  
(C) `static` 變數必須在建立物件時初始化。  
(D) `static` 變數無法被修改。  

**👉 答案：** (B) `static` 變數屬於整個類別，而不是某個特定的物件。  

---

**3️⃣ 下列有關 `this` 關鍵字的描述，何者錯誤？**  
(A) `this` 用於指向當前物件的實例。  
(B) `this` 可以用來區分類別屬性與方法參數。  
(C) `this` 只能用於建構子內，不能用在其他方法中。  
(D) `this` 不能在 `static` 方法內使用。  

**👉 答案：** (C) `this` 只能用於建構子內，不能用在其他方法中。（錯誤，`this` 也可以在一般方法中使用）

---

### ✍ 練習題

#### **📌 練習 1：定義類別與使用建構子**
請設計一個 `Car` 類別，該類別應包含：  
1. 兩個私有屬性：`brand`（品牌，字串）和 `speed`（速度，整數）。  
2. 一個建構子來初始化這些屬性。  
3. 提供 `getBrand()` 和 `setBrand(String brand)` 方法來存取 `brand`。  
4. 提供 `accelerate(int increase)` 方法來增加 `speed`，每次調用應該輸出當前速度。  
5. 在 `main()` 方法中，建立 `Car` 物件並測試其功能。  

**範例輸出（執行 `accelerate(10)` 三次後）：**  
```plaintext
目前速度：10 km/h  
目前速度：20 km/h  
目前速度：30 km/h  
```

---

#### **📌 練習 2：靜態變數與方法**
請設計一個 `Student` 類別，該類別應包含：  
1. `name`（學生姓名，字串）和 `score`（分數，整數）兩個屬性。  
2. `static` 變數 `totalStudents`，計算目前創建的學生數量。  
3. `displayInfo()` 方法，輸出學生姓名和分數。  
4. `static` 方法 `getTotalStudents()`，返回 `totalStudents` 的值。  
5. 在 `main()` 方法中，建立多個學生物件，並測試 `totalStudents` 是否正確計數。  

---

#### **📌 練習 3：封裝與 `this`**
請設計一個 `BankAccount` 類別，該類別應包含：  
1. 私有屬性 `accountNumber`（帳號，字串）和 `balance`（餘額，浮點數）。  
2. 建構子，使用 `this` 關鍵字來初始化屬性。  
3. `deposit(double amount)` 方法，允許存款，增加 `balance`。  
4. `withdraw(double amount)` 方法，允許提款，但若 `balance` 不足則顯示「餘額不足」。  
5. `displayBalance()` 方法，顯示目前的餘額。  
6. 在 `main()` 方法中，建立一個 `BankAccount` 物件，進行存款、提款與餘額顯示測試。  


## **1.3 方法與參數**  

在 Java 中，「方法」(Method) 是一組可以執行特定操作的程式碼區塊，類似於其他語言中的「函式」(Function)。方法可以幫助我們將程式碼模組化，提升程式的可讀性與重用性。本章節將介紹方法的基本語法、參數傳遞方式、回傳值、方法的重載 (Overloading) 以及可見性修飾詞。

---

### **📌 1. 方法的基本結構**
Java 方法的基本語法如下：
```java
修飾詞 回傳型別 方法名稱(參數列表) {
    // 方法內部的程式碼
    return 回傳值; // (如果回傳型別不是 void)
}
```
- **修飾詞 (Modifier)**：例如 `public`, `private`, `static` 等，決定方法的可見範圍與屬性。
- **回傳型別 (Return Type)**：方法執行後回傳的資料型別，若無回傳值則使用 `void`。
- **方法名稱 (Method Name)**：命名規則與變數相同，通常使用小駝峰命名法 (camelCase)。
- **參數列表 (Parameter List)**：列出方法的輸入變數，格式為 `(資料型別 變數名稱, ...)`，可以為空。
- **方法主體 (Method Body)**：包含要執行的程式碼。
- **return**：如果方法有回傳值，必須使用 `return` 返回結果。

📍 **範例：定義並呼叫方法**
```java
public class MathUtil {
    // 加法方法
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int sum = add(10, 20); // 呼叫方法
        System.out.println("加法結果：" + sum);
    }
}
```

---

### 📌 2. 方法的參數傳遞
Java 方法的參數傳遞採用 **值傳遞 (Pass by Value)**，這表示當你傳遞變數到方法時，Java 會建立變數的「複製」，方法內的變數變動不影響原變數。

📍 **基本型別的傳遞 (不會影響原變數)**
```java
public class Test {
    public static void modify(int x) {
        x = x + 10; // 只改變副本，不影響原變數
    }

    public static void main(String[] args) {
        int a = 5;
        modify(a);
        System.out.println("a 的值：" + a); // 結果仍為 5
    }
}
```

📍 **參考型別的傳遞 (影響原物件內容)**
```java
class Person {
    String name;
}

public class Test {
    public static void modify(Person p) {
        p.name = "Alice"; // 修改物件屬性，影響原物件
    }

    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "Bob";
        modify(p1);
        System.out.println("p1.name：" + p1.name); // 結果變為 "Alice"
    }
}
```
➡ **基本型別傳遞的是「值」，但物件 (參考型別) 傳遞的是「參考的值」，會影響原物件內容。**

---

### **📌 3. 方法的回傳值**
方法可以有回傳值，也可以沒有 (`void`)。

📍 **有回傳值的方法**
```java
public static int square(int num) {
    return num * num; // 回傳平方值
}
```

📍 **沒有回傳值的方法**
```java
public static void greet(String name) {
    System.out.println("Hello, " + name + "!");
}
```

---

### **📌 4. 方法的重載 (Method Overloading)**
Java 支援「方法重載」，即**相同方法名稱但不同參數列表**的方法。

📍 **方法重載範例**
```java
public class OverloadExample {
    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static String add(String a, String b) {
        return a + b;
    }

    public static void main(String[] args) {
        System.out.println(add(5, 10));        // 呼叫 int 版本
        System.out.println(add(5.5, 2.3));    // 呼叫 double 版本
        System.out.println(add("Hello, ", "World!")); // 呼叫 String 版本
    }
}
```
➡ 方法的回傳型別不同**不構成重載**，唯有**參數數量或型別不同**才算是合法的重載。

---

### 🔍 觀念測驗  

**1.下列哪一個方法定義是錯誤的？**  
(A) `void printMessage(String msg) { System.out.println(msg); }`  
(B) `int add(int a, int b) { return a + b; }`  
(C) `static double square(double x) { return x * x; }`  
(D) `public String toString(int value) { return Integer.toString(value); }`  

**2️ Java 方法的參數預設傳遞方式是？**  
(A) 變數的參考傳遞（pass by reference）  
(B) 變數的值傳遞（pass by value）  
(C) 依變數型別決定傳遞方式  
(D) Java 不支援方法參數  

**3️ 下列哪一種方法重載 (Overloading) 是合法的？**  
(A) `int add(int a, int b) {...}` 和 `double add(int a, int b) {...}`  
(B) `int add(int a, int b) {...}` 和 `int add(double a, double b) {...}`  
(C) `int add(int a, int b) {...}` 和 `int add(int a, int b, int c) {...}`  
(D) (B) 和 (C)  

**4 在 Java 中，方法參數的傳遞方式為何？**  
(A) 傳遞參考（Pass by Reference）  
(B) 傳遞值（Pass by Value）  
(C) 根據資料型別決定傳遞方式  
(D) 同時支援參考與值傳遞  

**5 下列哪個方法定義會導致編譯錯誤？**   
(A) `public int sum(int a, int b) { return a + b; }`  
(B) `public void sum(int a, int b) { return a + b; }`  
(C) `private static double multiply(double a, double b) { return a * b; }`  
(D) `protected String greet(String name) { return "Hello, " + name; }`  

**6 題目：** 關於方法重載（Method Overloading），下列哪項敘述是正確的？  
(A) 方法重載允許兩個方法僅返回型別不同即可區分。  
(B) 方法重載要求方法名稱相同，但必須有不同的參數數量或型別。  
(C) 方法重載與方法覆寫（Override）為相同概念。  
(D) 方法重載只能在不同的類別中實現。  

---

👉 答案
1. (D) `toString(int value)`  (`toString()` 方法為 Java 內建，應為 `public String toString()`)
2. (B) Java 採用 **值傳遞 (Pass by Value)** ➡ 但對於物件參數，傳遞的是「參考的值」。
3. (D) `(B) 和 (C)`➡ 方法重載需**改變參數數量或型別**，回傳型別不同**不能**構成重載。
4. (B) 傳遞值（Pass by Value）。**解析：** Java 中所有的參數傳遞都是值傳遞。對於基本型別，傳遞的是值的複製；而對於物件，傳遞的是物件參考的複製，因此修改物件內部的狀態會影響原物件，但無法更改參考本身。
5. (B) `public void sum(int a, int b) { return a + b; }`。**解析：** 此方法宣告為 `void`，表示不會回傳任何值，但卻使用 `return a + b;`。若方法有回傳值，應正確宣告其回傳型別，或將回傳敘述移除。
6. (B) 方法重載要求方法名稱相同，但必須有不同的參數數量或型別。**解析：** 方法重載是在同一個類別中定義多個名稱相同但參數列表不同的方法；僅僅改變返回型別不足以構成重載，且方法重載與覆寫是不同的概念。

---

### ✍ 練習題

#### **📌 練習 1：方法基本操作**
請實作一個 `Calculator` 類別，包含：
- `add(int, int)`：回傳兩數相加的結果。
- `subtract(int, int)`：回傳兩數相減的結果。
- `multiply(int, int)`：回傳兩數相乘的結果。
- `divide(int, int)`：當除數為 0 時，回傳 0，否則回傳商。

#### **📌 練習 2：值傳遞與參考傳遞**
1. 建立 `Person` 類別，包含 `name` 和 `age` 屬性。
2. 建立 `modify(Person p)` 方法，修改 `p.name` 為 `"Alice"`。
3. 測試 `modify()` 是否影響原 `Person` 物件的 `name` 值。

#### **📌 練習 3：方法重載**
建立 `print()` 方法：
- `print(int)` 顯示整數
- `print(double)` 顯示浮點數
- `print(String)` 顯示字串  
在 `main()` 測試這些方法是否能正確執行。

---

## 1.4 封裝與存取控制

封裝（Encapsulation）是物件導向程式設計（OOP）的四大核心概念之一，其主要目的是將物件的狀態（屬性）與行為（方法）包裝在同一個單位中，同時隱藏內部實作細節，只提供必要的介面與存取方式。這樣可以有效地保護資料，避免外部程式直接修改物件內部狀態，減少耦合性並提升程式的維護性與安全性。

### 1.4.1 封裝的概念
- **封裝（Encapsulation）**：將物件的資料（屬性）與行為（方法）集中管理，隱藏內部的實作細節，僅對外提供有限的操作介面。
- **好處：**
  - **保護資料安全**：防止外部程式直接修改物件狀態，避免不合法的操作。
  - **降低耦合性**：內部實作細節改變不會影響外部程式，方便維護與擴充。
  - **提高可讀性**：將物件的功能集中在一起，使程式邏輯更清晰。

### **1.4.2 存取控制（Access Control）**
在 Java 中，存取控制可以透過修飾子來實現，不同的存取修飾子決定了類別、屬性或方法的可見範圍：

| 修飾子                      | 同一類別 | 同一 Package | 子類別（不同 Package） | 其他類別 |
| --------------------------- | -------- | ------------ | ---------------------- | -------- |
| **public**                  | ✅        | ✅            | ✅                      | ✅        |
| **protected**               | ✅        | ✅            | ✅                      | ❌        |
| **(default)**（不寫修飾子） | ✅        | ✅            | ❌                      | ❌        |
| **private**                 | ✅        | ❌            | ❌                      | ❌        |

- **private**：僅能在本類別中存取。
- **default**（沒有修飾子）：在同一 package 中存取。
- **protected**：同一 package 以及子類別可存取。
- **public**：在任何地方皆可存取。

### 1.4.3 Getter 與 Setter 方法
為了實現封裝，我們通常將物件的屬性設為 private，並透過 public 的 getter 與 setter 方法來存取與修改屬性值。

📌 **範例：**
```java
public class Person {
    // 私有屬性
    private String name;
    private int age;

    // 建構子
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter 方法：取得 name
    public String getName() {
        return name;
    }

    // Setter 方法：設定 name
    public void setName(String name) {
        this.name = name;
    }

    // Getter 方法：取得 age
    public int getAge() {
        return age;
    }

    // Setter 方法：設定 age，並進行簡單檢查
    public void setAge(int age) {
        if(age >= 0) {  // 確保年齡不為負數
            this.age = age;
        } else {
            System.out.println("年齡必須為非負數");
        }
    }
    
    // 顯示資訊的方法
    public void displayInfo() {
        System.out.println("姓名：" + name + "，年齡：" + age);
    }
}
```

在上例中，`name` 與 `age` 屬性被宣告為 private，只有透過 public 的 getter 與 setter 方法才能讀取或修改。這樣可以在 setter 中加入驗證邏輯，防止不正確的數值進入物件內部。

---

### 觀念測驗

**1. 下列哪個修飾子能夠使類別成員只能在該類別內存取？**  
(A) public  
(B) protected  
(C) default  
(D) private  


**2. 使用 getter 與 setter 方法的主要好處是什麼？**
(A) 允許直接存取所有屬性  
(B) 強制外部類別使用特定格式存取資料  
(C) 提供封裝，保護資料的完整性與安全性  
(D) 減少程式碼長度  


**3. 如果一個屬性宣告為 protected，那麼下列敘述正確的是？**  
(A) 該屬性只能在本類別內存取  
(B) 該屬性在同一個 package 內和所有子類別中皆可存取  
(C) 該屬性可以被任何類別存取  
(D) 該屬性在不同 package 中的子類別也無法存取  

---

正確答案：
1. (D) private
2. (C) 提供封裝，保護資料的完整性與安全性
3. (B) 該屬性在同一個 package 內和所有子類別中皆可存取

---

### 練習題

#### **練習 1：設計 Student 類別**
請設計一個 `Student` 類別，要求如下：
- **屬性：**  
  - `private String id`：學生學號  
  - `private String name`：學生姓名  
  - `private double gpa`：學生 GPA  
- **建構子：** 使用上述屬性進行初始化
- **方法：**  
  - `public String getId()` 與 `public void setId(String id)`  
  - `public String getName()` 與 `public void setName(String name)`  
  - `public double getGpa()` 與 `public void setGpa(double gpa)`（在 setter 中，檢查 GPA 是否在 0.0 到 4.0 之間，否則輸出錯誤訊息）  
  - `public void displayStudentInfo()`：顯示學生的所有資訊

在 `main()` 方法中建立至少兩個 `Student` 物件，並測試 getter 與 setter 的功能。

---

#### **練習 2：資料驗證與封裝**
修改下列程式碼，使其符合封裝原則，並在 setter 中加入驗證邏輯：
```java
class BankAccount {
    double balance;

    BankAccount(double balance) {
        this.balance = balance;
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        account.balance = -500; // 不合理的餘額
        System.out.println("餘額：" + account.balance);
    }
}
```
要求：  
1. 將 `balance` 改為 private。  
2. 提供 `getBalance()` 與 `setBalance(double balance)` 方法，並在 setter 中檢查餘額不得為負數。  
3. 修改 `main()` 方法以使用 getter 與 setter。

---

#### **練習 3：模擬日常生活情境**
請設計一個 `Car` 類別，要求如下：
- **屬性：**  
  - `private String model`：車型  
  - `private int speed`：車速
- **建構子：** 初始化 `model` 與 `speed`（預設 `speed` 為 0）
- **方法：**  
  - `public void accelerate(int increment)`：將 `speed` 增加指定數值  
  - `public void brake(int decrement)`：將 `speed` 減少指定數值，但不低於 0  
  - `public void displayStatus()`：顯示車型與目前車速  
- **提示：** 利用封裝確保 `speed` 不能被設定成負值。

在 `main()` 方法中建立 `Car` 物件，並模擬加速與剎車的情境，驗證封裝與存取控制的效果。


## 1.5 建構子與物件初始化

在 Java 中，**建構子（Constructor）** 是一個特殊的方法，用於初始化新建立的物件。當使用 `new` 關鍵字建立物件時，系統會依序進行記憶體分配、執行初始化區塊，然後呼叫建構子來設定物件初始狀態。本節將探討以下三個主題：

- **預設建構子 vs. 自訂建構子**
- **建構子多載（Constructor Overloading）**
- **`this` 關鍵字的使用**

---

### 1.5.1 預設建構子 vs. 自訂建構子

#### 預設建構子
- 當你在類別中**未明確定義任何建構子**時，Java 編譯器會自動提供一個無參數的預設建構子。
- 預設建構子會將物件的成員變數初始化為其預設值（例如：數值型別為 `0`、布林型別為 `false`、參考型別為 `null`）。

**範例：**
```java
public class Animal {
    private String species;
    private int age;
    
    // 沒有自訂建構子時，Java 會自動生成類似以下的預設建構子：
    // public Animal() { }
    
    public void display() {
        System.out.println("Species: " + species + ", Age: " + age);
    }
    
    public static void main(String[] args) {
        // 呼叫預設建構子建立物件
        Animal animal = new Animal();
        animal.display();  // 輸出：Species: null, Age: 0
    }
}
```

#### 自訂建構子
- 當你定義自己的建構子時，就可以指定初始值或執行特定的初始化動作。
- 一旦你定義了**任何一個**建構子，Java 就不再自動生成預設建構子（除非你顯式定義）。

**範例：**
```java
public class Animal {
    private String species;
    private int age;
    
    // 自訂建構子：初始化物件時直接設定 species 與 age
    public Animal(String species, int age) {
        this.species = species;
        this.age = age;
    }
    
    public void display() {
        System.out.println("Species: " + species + ", Age: " + age);
    }
    
    public static void main(String[] args) {
        // 使用自訂建構子建立物件
        Animal lion = new Animal("Lion", 5);
        lion.display();  // 輸出：Species: Lion, Age: 5
    }
}
```

---

### 1.5.2 建構子多載（Constructor Overloading）

- **建構子多載** 指的是在同一個類別中定義多個建構子，但每個建構子的參數列表必須不同（參數數量、型別或順序）。
- 多載的建構子讓你可以根據不同需求提供不同的初始化方式。

**範例：**
```java
public class Book {
    private String title;
    private String author;
    private double price;
    
    // 無參數建構子，設定預設值
    public Book() {
        this("Unknown Title", "Unknown Author", 0.0);
    }
    
    // 帶一個參數的建構子，只設定書名，其他使用預設值
    public Book(String title) {
        this(title, "Unknown Author", 0.0);
    }
    
    // 帶兩個參數的建構子
    public Book(String title, String author) {
        this(title, author, 0.0);
    }
    
    // 帶全部參數的建構子
    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
    
    public void display() {
        System.out.println("Title: " + title + ", Author: " + author + ", Price: $" + price);
    }
    
    public static void main(String[] args) {
        Book b1 = new Book();
        Book b2 = new Book("Java Programming");
        Book b3 = new Book("Effective Java", "Joshua Bloch");
        Book b4 = new Book("Clean Code", "Robert C. Martin", 42.95);
        
        b1.display();
        b2.display();
        b3.display();
        b4.display();
    }
}
```

在這個例子中，我們定義了四個建構子，讓使用者可以根據需要提供不同數量的參數來初始化 `Book` 物件。

---

### 1.5.3 `this` 關鍵字的使用

`this` 關鍵字有幾種常見用途：

1. **區分成員變數與參數**  
   當建構子或方法的參數名稱與成員變數相同時，可以使用 `this` 來區分。例如：
   ```java
   public class Person {
       private String name;
       private int age;
       
       public Person(String name, int age) {
           // 這裡 this.name 指的是成員變數，而 name 指的是參數
           this.name = name;
           this.age = age;
       }
   }
   ```

2. **呼叫同一類別中的另一個建構子**  
   可以使用 `this(參數列表)` 來呼叫同一類別中另一個建構子，此呼叫必須是建構子中的第一行程式碼。
   ```java
   public class Point {
       private int x;
       private int y;
       
       // 帶參數建構子
       public Point(int x, int y) {
           this.x = x;
           this.y = y;
       }
       
       // 無參數建構子，使用 this() 呼叫帶參數建構子設定預設值
       public Point() {
           this(0, 0);  // 呼叫上面的建構子
       }
       
       public void display() {
           System.out.println("Point(" + x + ", " + y + ")");
       }
       
       public static void main(String[] args) {
           Point p1 = new Point();
           Point p2 = new Point(5, 10);
           p1.display();  // 輸出：Point(0, 0)
           p2.display();  // 輸出：Point(5, 10)
       }
   }
   ```

3. **返回當前物件的參考**  
   在某些情況下，`this` 可以用於返回當前物件的引用，方便方法鏈（method chaining）。
   ```java
   public class Builder {
       private String value;
       
       public Builder setValue(String value) {
           this.value = value;
           return this;  // 返回當前物件，使多個方法可以連續呼叫
       }
       
       public void display() {
           System.out.println("Value: " + value);
       }
       
       public static void main(String[] args) {
           Builder b = new Builder();
           b.setValue("Hello").display();  // 方法鏈呼叫
       }
   }
   ```

---

### 觀念測驗

1. **如果一個類別中沒有定義任何建構子，Java 編譯器會：**
(A) 報錯，因為必須明確定義建構子  
(B) 自動生成一個無參數的預設建構子  
(C) 自動生成一個帶有所有成員變數參數的建構子  
(D) 需要手動呼叫超類別的建構子  

2. **下列哪個敘述正確描述了建構子多載（Constructor Overloading）的概念？**  
(A) 建構子必須具有相同的參數列表才能構成多載  
(B) 建構子多載指在同一類別中定義多個建構子，但必須有不同的參數列表  
(C) 建構子多載可以透過不同的返回型別來區分  
(D) 建構子多載只能定義一個無參數建構子和一個帶參數建構子  

3. **在建構子中使用 `this(參數列表)` 的主要目的為何？**
(A) 呼叫超類別的建構子  
(B) 呼叫同一類別中另一個建構子，避免重複程式碼  
(C) 指向當前物件的成員變數  
(D) 用於返回當前物件的參考  

---

答案
1. (B) 自動生成一個無參數的預設建構子
2. (B) 建構子多載指在同一類別中定義多個建構子，但必須有不同的參數列表
3. (B) 呼叫同一類別中另一個建構子，避免重複程式碼

---

### **練習題**

#### **練習題 1：設計 Person 類別**
請設計一個 `Person` 類別，要求如下：
- **屬性：**  
  - `private String name`  
  - `private int age`
- **建構子：**  
  - 定義一個無參數建構子，將 `name` 設為 `"Unknown"` 且 `age` 設為 `0`  
  - 定義一個帶參數建構子，根據傳入的參數初始化 `name` 與 `age`
- **方法：**  
  - `public void displayInfo()`：輸出 `name` 與 `age`
- **要求：**  
  在 `main()` 方法中建立兩個 `Person` 物件，分別使用無參數和帶參數建構子，並呼叫 `displayInfo()` 驗證初始化是否正確。

---

#### **練習題 2：使用建構子多載與 `this()`**
請設計一個 `Rectangle` 類別，要求如下：
- **屬性：**  
  - `private int width`  
  - `private int height`
- **建構子：**  
  - 定義一個帶參數建構子，接收寬度與高度，並初始化對應屬性  
  - 定義一個無參數建構子，使用 `this(預設寬度, 預設高度)` 呼叫帶參數建構子，設定寬度和高度皆為 10
- **方法：**  
  - `public void display()`：顯示矩形的寬度與高度
- **要求：**  
  在 `main()` 方法中分別建立使用無參數和帶參數的 `Rectangle` 物件，並驗證初始化結果是否正確。

---

#### **練習題 3：實例初始化區塊與建構子的執行順序**
請設計一個 `Counter` 類別，要求如下：
- **屬性：**  
  - `private int count`
- **初始化：**  
  - 使用實例初始化區塊將 `count` 設定為 `100`  
- **建構子：**  
  - 定義一個無參數建構子，在建構子中印出 `count` 的值
- **要求：**  
  在 `main()` 方法中建立 `Counter` 物件，觀察並驗證實例初始化區塊與建構子執行的順序與結果。


## **1.6 靜態成員與類別方法**

在 Java 中，`static` 關鍵字用來宣告靜態成員，也稱為類別成員。這些成員屬於整個類別，而非單一的物件實例。這一章節將深入探討：

- **static 修飾詞的用途**
- **靜態方法 (static methods) 與物件方法 (instance methods) 的差異**
- **以 Math 類別作為例子**

---

### **1.6.1 static 修飾詞的用途**

- **靜態成員（Static Members）**：  
  靜態變數與靜態方法屬於整個類別，而不是單一物件。這意味著所有物件共用同一份靜態成員資料。

  **用途：**
  - **共享資料**：例如計數器，所有物件共享同一數值。
  - **工具方法**：不需要建立物件即可呼叫的方法，如數學運算、字串處理等。

- **宣告方式：**
  ```java
  public class Example {
      // 靜態變數（類別變數）
      public static int counter = 0;
      
      // 靜態方法（類別方法）
      public static void displayCounter() {
          System.out.println("Counter: " + counter);
      }
  }
  ```

---

### **1.6.2 靜態方法 vs. 物件方法**

- **靜態方法（Static Methods）：**
  - 屬於類別本身，不需要建立物件即可呼叫。
  - 靜態方法中無法使用 `this` 及存取非靜態成員，因為它們與具體的物件無關。
  - 範例呼叫：`ClassName.methodName();`

- **物件方法（Instance Methods）：**
  - 屬於物件實例，必須透過建立物件後才能呼叫。
  - 可以存取該物件的實例變數和其他物件方法。
  - 範例呼叫：`objectName.methodName();`

**範例說明：**
```java
public class Sample {
    // 靜態變數：所有 Sample 物件共享
    public static int staticCount = 0;
    
    // 物件變數：每個 Sample 物件各自擁有
    public int instanceCount = 0;
    
    // 靜態方法：無法存取 instanceCount
    public static void incrementStatic() {
        staticCount++;
        // 下面這行會編譯錯誤：無法從靜態方法存取非靜態變數
        // instanceCount++;
    }
    
    // 物件方法：可以存取靜態和非靜態成員
    public void incrementInstance() {
        instanceCount++;
        staticCount++;
    }
    
    public static void main(String[] args) {
        // 呼叫靜態方法，不需要建立物件
        Sample.incrementStatic();
        System.out.println("Static Count: " + Sample.staticCount); // 輸出：1
        
        // 建立物件呼叫物件方法
        Sample s1 = new Sample();
        s1.incrementInstance();
        System.out.println("s1 Instance Count: " + s1.instanceCount); // 輸出：1
        System.out.println("Static Count: " + Sample.staticCount);      // 輸出：2
        
        Sample s2 = new Sample();
        s2.incrementInstance();
        System.out.println("s2 Instance Count: " + s2.instanceCount); // 輸出：1
        System.out.println("Static Count: " + Sample.staticCount);      // 輸出：3
    }
}
```

---

### **1.6.3 Math 類別的例子**

Java 中的 `Math` 類別是一個典型的工具類別，其所有方法都被宣告為靜態的。這使得我們可以直接呼叫 `Math` 類別的方法而無需建立物件，例如：

- **常用方法：**
  - `Math.sqrt(double a)`：計算平方根。
  - `Math.pow(double a, double b)`：計算 a 的 b 次方。
  - `Math.random()`：生成 0.0 到 1.0 之間的隨機數。

**範例：**
```java
public class MathExample {
    public static void main(String[] args) {
        double value = 16.0;
        double sqrtValue = Math.sqrt(value); // 呼叫靜態方法，計算平方根
        double powerValue = Math.pow(2, 3);    // 計算 2^3 = 8
        double randomValue = Math.random();    // 生成隨機數
        
        System.out.println("sqrt(" + value + ") = " + sqrtValue);
        System.out.println("2^3 = " + powerValue);
        System.out.println("Random Value: " + randomValue);
    }
}
```
在上述範例中，我們直接通過類別名稱 `Math` 來呼叫其靜態方法，這正是靜態方法的一大特點。

---

### **觀念測驗**

1. **下列關於 `static` 修飾詞的敘述，何者正確？**
(A) 靜態成員屬於每個物件實例，各自擁有獨立副本。  
(B) 靜態方法可以直接存取非靜態變數。  
(C) 靜態成員屬於整個類別，所有物件共享同一份資料。  
(D) 靜態方法必須先建立物件才能被呼叫。  

**正確答案：** (C) 靜態成員屬於整個類別，所有物件共享同一份資料。

---

2. **下列哪一項是靜態方法與物件方法的主要區別？**
(A) 靜態方法可以使用 `this` 關鍵字，而物件方法不能。  
(B) 物件方法屬於類別本身，而靜態方法屬於物件實例。  
(C) 靜態方法在類別載入時就存在，而物件方法必須透過物件建立後才能呼叫。  
(D) 靜態方法可以直接存取物件方法中的非靜態變數。  

**正確答案：** (C) 靜態方法在類別載入時就存在，而物件方法必須透過物件建立後才能呼叫。

---

3. **以下有關 Math 類別的描述，哪一項是正確的？**
(A) Math 類別中的所有方法都是物件方法，因此需要建立 Math 物件。  
(B) Math 類別中的方法都是靜態方法，可以直接使用類別名稱呼叫。  
(C) Math 類別提供了一個預設建構子，可用於初始化數學常數。  
(D) Math 類別僅包含靜態變數，沒有方法。  

**正確答案：** (B) Math 類別中的方法都是靜態方法，可以直接使用類別名稱呼叫。

---

### 練習題

#### **練習題 1：設計一個工具類別**
請設計一個 `StringUtil` 工具類別，要求如下：
- **方法：**  
  - 定義一個靜態方法 `reverse(String s)`，用以回傳反轉後的字串。
  - 定義一個靜態方法 `isEmpty(String s)`，檢查字串是否為空或 `null`。
- **要求：**  
  在 `main()` 方法中測試這些靜態方法，展示它們的使用。

---

#### **練習題 2：比較靜態變數與物件變數**
請設計一個 `Counter` 類別，要求如下：
- **屬性：**  
  - `private static int totalCount`：用來計算所有物件共用的計數器。
  - `private int instanceCount`：用來計算每個物件自身的計數器。
- **建構子：**  
  每建立一個 `Counter` 物件，則：
  - `totalCount` 加 1。
  - `instanceCount` 初始化為 1。
- **方法：**  
  - `public void increment()`：將 `instanceCount` 加 1，並同步將 `totalCount` 加 1。
  - `public void display()`：顯示該物件的 `instanceCount` 以及目前的 `totalCount`。
- **要求：**  
  在 `main()` 方法中建立多個 `Counter` 物件，並透過呼叫 `increment()` 及 `display()` 觀察靜態與物件成員的差異。

---

#### **練習題 3：利用 Math 類別進行數學運算**
請撰寫一個程式，要求如下：
- 從鍵盤讀取一個正數，計算並顯示其平方根與立方（即三次方）的值。
- 使用 `Math.sqrt()` 及 `Math.pow()` 方法進行運算，並直接使用這些靜態方法，不建立 Math 物件。

## **1.7 物件之間的關係**

在物件導向程式設計中，物件之間的關係定義了它們如何互動與依賴。主要有三種關係：  
- **關聯（Association）：** 描述物件彼此間的互動關係。  
- **聚合（Aggregation）：** 表示一個物件包含另一個物件，但所包含的物件可獨立存在。  
- **組合（Composition）：** 表示一個物件「擁有」另一個物件，所包含的物件與整體有非常強的生命週期依賴關係。

---

### 1.7.1 關聯（Association）

**定義：**  
關聯描述兩個或多個物件之間的互動或連結關係。例如，一位教師與學生之間的關係，或者一個司機與他的車輛之間的關係。  
- **雙向關聯：** 物件之間彼此認識與互動。  
- **單向關聯：** 一個物件認識另一個物件，但反之則不一定知道。

**範例：**  
```java
public class Teacher {
    private String name;
    
    public Teacher(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

public class Student {
    private String name;
    // 單向關聯：每個 Student 擁有一位 Teacher 作為導師
    private Teacher advisor;
    
    public Student(String name, Teacher advisor) {
        this.name = name;
        this.advisor = advisor;
    }
    
    public void displayInfo() {
        System.out.println("Student: " + name + ", Advisor: " + advisor.getName());
    }
    
    public static void main(String[] args) {
        Teacher t = new Teacher("Mr. Smith");
        Student s = new Student("Alice", t);
        s.displayInfo();
    }
}
```
在上述例子中，`Student` 與 `Teacher` 之間就存在一種關聯關係。

---

### 1.7.2 聚合（Aggregation）

**定義：**  
聚合是一種特殊的關聯，描述一個物件包含另一個物件，但這兩者之間有獨立的生命週期。  
- **範例：** 一個系所（Department）包含多位教授（Professor），但教授可以不依賴於某個系所獨立存在。

**範例：**  
```java
import java.util.ArrayList;
import java.util.List;

public class Professor {
    private String name;
    
    public Professor(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

public class Department {
    private String departmentName;
    // 聚合：Department 擁有多個 Professor，但教授本身可以獨立於 Department 存在
    private List<Professor> professors;
    
    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.professors = new ArrayList<>();
    }
    
    public void addProfessor(Professor prof) {
        professors.add(prof);
    }
    
    public void displayDepartment() {
        System.out.println("Department: " + departmentName);
        System.out.println("Professors:");
        for(Professor prof : professors) {
            System.out.println("- " + prof.getName());
        }
    }
    
    public static void main(String[] args) {
        Department csDept = new Department("Computer Science");
        Professor p1 = new Professor("Dr. Johnson");
        Professor p2 = new Professor("Dr. Lee");
        
        csDept.addProfessor(p1);
        csDept.addProfessor(p2);
        csDept.displayDepartment();
    }
}
```

---

### 1.7.3 組合（Composition）

**定義：**  
組合也是一種「擁有」的關係，但相較於聚合，組合具有更強的依賴性。  
- **特點：**  
  - 所包含的物件（部分）與整體（整體物件）有同樣的生命週期。  
  - 當整體物件被銷毀時，部分物件也隨之消失。  
- **範例：** 一棟房子（House）包含房間（Room），如果房子不存在，房間也無法獨立存在。

**範例：**  
```java
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    
    public Room(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

public class House {
    private String address;
    // 組合：House 擁有房間，房間的存在依賴於 House
    private List<Room> rooms;
    
    public House(String address) {
        this.address = address;
        this.rooms = new ArrayList<>();
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public void displayHouse() {
        System.out.println("House at " + address + " has the following rooms:");
        for(Room room : rooms) {
            System.out.println("- " + room.getName());
        }
    }
    
    public static void main(String[] args) {
        House house = new House("123 Main St");
        Room livingRoom = new Room("Living Room");
        Room kitchen = new Room("Kitchen");
        
        house.addRoom(livingRoom);
        house.addRoom(kitchen);
        house.displayHouse();
    }
}
```
在這個例子中，`Room` 物件是 `House` 的一部分，當 `House` 被銷毀時，其包含的 `Room` 也隨之消失。

---

### 觀念測驗

1. **下列哪一項最能描述「關聯（Association）」的特性？**
(A) 一個物件包含另一個物件，且部分的生命週期依賴於整體  
(B) 物件之間僅僅是互相認識，彼此獨立存在  
(C) 一個物件與另一個物件完全不可分離  
(D) 物件必須透過繼承來建立關係  

**正確答案：** (B) 物件之間僅僅是互相認識，彼此獨立存在

---

2. **聚合（Aggregation）與組合（Composition）主要的區別在於：**
(A) 聚合描述的是「擁有」關係，而組合描述的是「使用」關係  
(B) 聚合中的部分可以獨立存在，而組合中的部分不能獨立存在  
(C) 聚合只能是單向關聯，組合則必須是雙向關聯  
(D) 聚合需要透過接口實現，組合則不需要  

**正確答案：** (B) 聚合中的部分可以獨立存在，而組合中的部分不能獨立存在

---

3. **以下敘述何者最符合組合（Composition）的特性？**
(A) 一個課程（Course）擁有多位老師（Teacher），但老師可以獨立於課程存在。  
(B) 一個汽車（Car）由引擎（Engine）構成，當汽車不存在時，引擎也不存在。  
(C) 一個圖書館（Library）與其書籍（Book）之間沒有生命週期上的依賴關係。  
(D) 兩個獨立的應用程式透過接口共享數據。  

**正確答案：** (B) 一個汽車（Car）由引擎（Engine）構成，當汽車不存在時，引擎也不存在。

---

### **練習題**

#### **練習題 1：建立關聯**
請設計一個簡單的「公司（Company）」與「員工（Employee）」系統，要求如下：
- 定義 `Company` 類別，包含屬性 `companyName` 與 `List<Employee>`。
- 定義 `Employee` 類別，包含屬性 `name` 與 `position`。
- 在 `Company` 中提供方法來新增員工及顯示公司所有員工資訊。
- 在 `main()` 方法中建立一個 `Company` 物件，新增多個 `Employee` 物件，並顯示其資訊。

---

#### **練習題 2：實作聚合**
請設計一個「學校（School）」與「老師（Teacher）」的系統，要求如下：
- 定義 `Teacher` 類別，包含屬性 `name` 與 `subject`。
- 定義 `School` 類別，包含屬性 `schoolName` 與 `List<Teacher>`，並提供方法添加老師與顯示所有老師資訊。
- 在 `main()` 方法中，建立一個 `School` 物件，新增數位 `Teacher` 物件，展示聚合關係。

---

#### **練習題 3：實作組合**
請設計一個「電腦（Computer）」與「組件（Component）」的系統，要求如下：
- 定義 `Component` 類別，包含屬性 `componentName` 與 `specification`。
- 定義 `Computer` 類別，包含屬性 `computerName` 與 `List<Component>`。  
  **提示：** 當建立 `Computer` 物件時，必須透過建構子或方法來初始化其所有組件，並確保組件與電腦具有相同的生命週期。
- 在 `main()` 方法中，建立一個 `Computer` 物件，為其添加多個 `Component` 物件，並顯示整台電腦的配置信息。
---