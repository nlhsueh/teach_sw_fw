# Ch02 物件導向設計

@nlhsueh 

## 2.1 虛虛實實：抽象與繼承

> [!TIP]
> * 何謂繼承，如何應用？
> * 繼承時子類別該如何設計建構子？
> * 父類別與子類別的宣告與生成該注意什麼？型態的轉換規則為何？
> * 子類別該如何保存父類別既有功能下延伸功能？
> * 抽象類別、抽象方法的意義為何？使用時機為何？

### 2.1.1 類別繼承

當類別 B 繼承  類別 A 時，表示 B 具備 A 的特性，不用再重複的寫一次，並且可以擴充自己的特性。

```java
class A {
  public void m1() {...}
}

class B extends A { //類別繼承
  public void m2() {  //新增方法
    ...
  }
}

B b = new B();
b.m1(); // => ok
b.m2(); // => ok
```

A 稱為父類別，B 稱為子類別

> [!NOTE]
> 子類別的物件生成時，其建構子會呼叫父類別建構子。


```java
class Vehicle {
  int speed;
  public Vehicle(int speed) {
     this.speed = speed;
  }   
}

class Bike extends Vehicle {
  int seatHeight;

  public Bike(int seatHeight) {
    super();
    this.seatHeight = seatHeight;
  }

  void setHeight(int seatHeight) {
    this.seatHeight = seatHeight;
  }
}
```

:question: 上述程式會產生編譯錯誤，為什麼？

<details>
<summary>提示</summary>

父類別並沒有不帶參數的建構子。需改為：
```java
  public Bike(int speed, int seatHeight) {
    super(speed);
    this.seatHeight = seatHeight;
  }
```
</details>


### 2.1.2 方法覆蓋

```java
class A {
  public void m1() { print A }
}

class B extends A {
  public void m1() {  //覆蓋方法
      print B
  }
  public void m2() {  //新增方法
    ...
  }
}
```

執行：
```java
A a = new A();
a.m1(); // => print A

B b = new B();
b.m1(); // => print B
```


### 2.1.3 型態轉換

不要讓 compiler 不開心。假設 Engineer 是 Person 的子類別:

```java
class Person {}
class Engineer extends Person {}

Person a = new Person(); //當然 ok
Person a = (Person) new Engineer(); //ok, upcasting
Person a = new Engineer(); //ok, upcasting, (Person) 可以省略

Engineer b = new Engineer(); //當然 ok
Engineer b = new Person(); //compiler error
Engineer b = (Engineer) new Person(); //downcasting, runtime error

Person a = new Engineer();
Engineer b = (Engineer)a; //downcasting, compiler, runtime ok
```



Engineer 繼承 Person 後具備 Person 的特性，所以 Engineer 可以做 Person 所有的事，反之 Person 無法做所有 Engineer 的事。

> [!NOTE]
> B b = new A()：把一個 弱A 當成一個 強B，產生編譯錯誤。

例子：

```java
Cat mao = new Cat();
Animal mimi = mao; //upcasting (把一個比較低階的物件給比較高階的類別）
//給 mao 多取一個名字 mimi, 並告訴大家 mimi 是一個動物

Cat jaja = mimi; // 編譯錯誤
Cat jaja = (Cat) mimi; //downcasting
//mimi 又多了一個名字 jaja, 並且告訴大家 jaja 是一支貓
```

### 2.1.4 擴充的應用範例


`StringTokenizer` 可以把一串字串做解析：

* `StringTokenizer(String a)` 生成物件時帶入所要解析的字。
* `nextToken()` 會傳回下一個字。
* `hasMoreToken()` 判斷是否還有未解析的字，true 時表示還有。false 表示已經到最後一個字了。


> Extend StringTokenizer to EnhancedStringTokenizer

* 進階字串處理器 `EnhancedStringTokenizer`。除了可以做字串的解析以外，還可以回傳目前解析的字串集（以陣列的方式回傳）。

```java
import java.util.StringTokenizer;

public class EnhancedStringTokenizer extends StringTokenizer {
    private String[] a;
    private int count;

    // enhance the constructor
    public EnhancedStringTokenizer(String theString ) {
        super(theString);
        a = new String[countTokens( )];
        count = 0;
    }

    // enhance nextToken
    public String nextToken( )    {
        String token = super.nextToken( );
        // 以下是添增的功能
        a[count++] = token;
        return token;
    }

    // new method
    public String[] tokensSoFar( ) {
        String[] arrayToReturn = new String[count];
        for (int i = 0; i < count; i++)
            arrayToReturn[i] = a[i];
        return arrayToReturn;
    }
}

class Main {
   public static void main(String args[]) {
      String s = "I love apple";
      EnhancedStringTokenizer tokenizer = new EnhancedStringTokenizer(s);
      while ( tokenizer.hasMoreTokens()) { // call parent's function
         System.out.println(tokenizer.nextToken()); // call child's function
         printSoFar(tokenizer.tokensSoFar()); // call child's (new defined) function
      }   
   }
   static void printSoFar(String[] ss) {
      for (String s: ss) System.out.println(s);
   }
}
```

```mermaid
classDiagram
    direction BT
    class StringTokenizer {
        +StringTokenizer(String str)
        +nextToken() String
        +hasMoreTokens() boolean
        +countTokens() int
    }
    
    class EnhancedStringTokenizer {
        -String[] a
        -int count
        +EnhancedStringTokenizer(String theString)
        +nextToken() String
        +tokensSoFar() String[]
    }
    
    class Main {
        +main(String[] args)
        +printSoFar(String[] ss)$
    }

    StringTokenizer <|-- EnhancedStringTokenizer : extends
    Main ..> EnhancedStringTokenizer : uses
```

### 2.1.5 抽象方法與類別

抽象類別無法產生物件，但可被繼承，例如交通工具 Vehicle 可分為 Bike 和 Car, 是一種完全分類，不會有物件從 Vehicle 產生出來。

抽象方法 宣告方法的的介面（參數及回傳型態），但不具備實作（implementation）。例如所有的交通工具都會向左轉，向右轉，但怎麼做則由子類別自己定義。

```java
abstract class Vehicle { //抽象類別
   private String ID;
   public abstract void turnLeft(); //抽象方法
   public abstract void turnRight(); //抽象方法
   public String getID() { //具體方法
      return ID;
   }   
}

class Bike extends Vehicle {
   public void turnLeft() {
       ... //腳踏車的左轉方法
   }
   public void turnRight() {
       ... //腳踏車的右轉方法
   }
}

class Car extends Vehicle {
   public void turnLeft() {
       ... //汽車的左轉方法
   }
   public void turnRight() {
       ... //汽車的右轉方法
   }
   public void backward() { //new method
       ... //汽車倒車的方法
   }
}
```

```mermaid
classDiagram
    class Vehicle {
        <<abstract>>
        -String ID
        +turnLeft()* void
        +turnRight()* void
        +getID() String
    }
    class Bike {
        +turnLeft() void
        +turnRight() void
    }
    class Car {
        +turnLeft() void
        +turnRight() void
        +backward() void
    }
    Vehicle <|-- Bike
    Vehicle <|-- Car
```

> [!NOTE]
> 抽象類別是一個半成品，等待子類別去完成。

---

### 2.1.6 現代 Java 特性：密封類別
在傳統繼承中，任何類別只要不是 `final` 都可以被繼承。Java 17 引入了 **Sealed Classes**，讓開發者可以精確限制哪些類別可以繼承自己。

📌 **範例：限制 Shape 的子類別**
```java
// 只允許 Circle 和 Square 繼承 Shape
public sealed class Shape permits Circle, Square { }

public final class Circle extends Shape { /* ... */ }
public final class Square extends Shape { /* ... */ }

// 如果嘗試建立其他子類別（如 Triangle），將會編譯錯誤
// public class Triangle extends Shape { } 
```
> [!TIP]
> 當你希望類別階層是有限且可控的（例如：支付方式只能是信用卡或轉帳），Sealed Classes 是最佳工具。

### 🔍 觀念測驗 2.1

1️⃣ **關於 Java 的類別繼承，以下哪一項是正確的？**  
   A) Java 支援多重繼承（multiple inheritance）  
   B) `final` 關鍵字可用來防止類別被繼承  
   C) `private` 方法可以被子類別覆寫（override）  
   D) 抽象類別（abstract class）無法包含已實作的方法  

2️⃣ **以下關於方法覆蓋（Method Overriding）說法正確的是？**  
   A) 子類別的覆蓋方法可以擴大訪問權限，例如從 `protected` 改為 `public`  
   B) `@Override` 註解是強制性的，否則無法覆蓋  
   C) 子類別的覆蓋方法可以減小訪問權限，例如從 `public` 改為 `private`  
   D) 父類別的方法若標記為 `static`，子類別仍然可以覆蓋  

3️⃣ **以下哪個 Java 程式碼片段會產生編譯錯誤？**  
   ```java
   class Animal {
       public void speak() {
           System.out.println("Animal speaks");
       }
   }

   class Dog extends Animal {
       private void speak() {  // (X)
           System.out.println("Dog barks");
       }
   }
   ```
   為什麼？  
   A) `speak()` 方法的返回類型不同  
   B) 子類別的 `speak()` 方法無法縮小訪問權限  
   C) `speak()` 方法必須標記為 `static`  
   D) `Dog` 不能繼承 `Animal`  


4️⃣ **型態轉換（Type Casting）時，何時會發生 `ClassCastException`？**  
   A) 向下轉型（downcasting）時，若物件實際類型與目標類型不匹配  
   B) 向上轉型（upcasting）時，若目標類型不匹配  
   C) 任何時候執行 `instanceof` 之前  
   D) 只有當類別包含 `static` 方法時  


---

<details>
<summary>👉 點擊查看答案</summary>

參考答案：
1. **B** (`final` 關鍵字可用來防止類別被繼承)
2. **A** (覆蓋方法可以維持或擴大存取權限，但不能縮小。例如：`protected` 可以改為 `public`)
3. **B** (子類別覆寫時不能將訪問權限從 `public` 縮小為 `private`)
4. **A** (當嘗試將父類別物件轉換為不正確的子類別型態時，會發生 `ClassCastException`)
</details>


---

6️⃣ **Java 中，以下何者正確：**
1. 抽象類別內至少有一抽象方法 
2. 抽象類別多個抽象方法，也可以沒有
3. 抽象類別可以有多個個具體方法 ，也可以沒有
4. 具體類別不可以有任何抽象方法
5. 抽象類別不能直接生成物件; 具體類別可以

7️⃣ **B 是 A 的子類別，下列何者正確？**

```java
1. public B do1() { return new A(); } 
2. public String m1(int i) {return "1"; } 
3. public private String m2(String s1, String s2) {return s2;} 
4. public A do2() { return new B(); } 
```

### ✍ 練習 2.1


#### 📌 練習 2.1.1：型態轉換與物件識別
**問題描述：**
請依以下步驟，練習物件的轉型與型態判斷：
1. **建立類別**：設計 `Animal` 父類別（含 `speak()` 方法），以及繼承它的 `Dog` 與 `Cat` 子類別，並分別讓 `speak()` 方法輸出 `"Dog barks"` 與 `"Cat meows"`。
2. **向上轉型**：在主程式中，宣告一個 `Animal` 型態的變數並指向 `Dog` 物件（`Animal a = new Dog();`）。呼叫 `a.speak()`，觀察會執行哪一個類別的方法？
3. **安全向下轉型**：撰寫一個測試方法 `void testAnimal(Animal a)`。在方法內，請使用 `instanceof` 檢查傳入的參數 `a` 是否為 `Dog`：
   - 如果是，請將其向下轉型（強制轉型）為 `Dog` 並呼叫 `speak()`。
   - 如果不是，則印出 `"Not a Dog"`。這可以避免當傳入 `Cat` 物件時發生 `ClassCastException` 錯誤。最後請分別傳入 `Dog` 與 `Cat` 物件來測試此方法。

---

#### 📌 練習 2.1.2：覆寫 `toString()`
**問題描述：**  
請建立 `Person` 類別，包含 `name` 和 `age` 屬性，並覆寫 `toString()` 方法，使其輸出 `"Name: XXX, Age: YYY"`。  

**範例輸入輸出：**
```java
Person p = new Person("Alice", 25);
System.out.println(p);  
// 輸出: "Name: Alice, Age: 25"
```

Hint: `String.format(format, args)`

#### 📌 練習 2.1.3：Fruit parser
擴充 StringTokenizer 為 FruitParser，字串中若有水果名稱，可以透過固定的介面取得水果的名稱。請應用 StringTokenizer 既有的方法 (hasMoreToken(), nextToken() 等方法)。

```java
public class FruitParser extends StringTokenizer {
    String[] fruit_set = { "apple", "avocado", "banana", "cherry", "coconut", "jujube", "durian", "grape", "grapefruit",
			"guava", "lemon", "lichee", "orange", "kiwi" };
	
    public String[] getFruits() {
        // ?
    }

    public static void main(String[] args) {
        String s = "I like apple, banana, and orange. Marry like kiwi";
        FruitParser f = new FruitParser(s);
        String[] fruits = f.getFruits();		
    }
}
```

<details>
<summary>參考解答</summary>

* [練習 2.1.1：型態轉換與物件識別](../Demo/src/main/java/swfw/ch02/ex2_1/Exercise2_1_1.java)
* [練習 2.1.2：覆寫 `toString()`](../Demo/src/main/java/swfw/ch02/ex2_1/Person.java)
* [練習 2.1.3：Fruit parser](../Demo/src/main/java/swfw/ch02/ex2_1/FruitParser.java)

</details>

<details>
<summary>💡 提示</summary>

1. 在 `getFruits()` 中，使用 `while(hasMoreTokens())` 迴圈與 `nextToken()` 逐一取得字串中的每個單字。
2. 檢查該單字是否存在於 `fruit_set` 陣列中。可以利用 `Arrays.asList(fruit_set).contains(單字)` 來檢查，或者是自己寫一個 `for` 迴圈跑過 `fruit_set` 去比對 `.equals()`。可以將找到的水果暫存到 `ArrayList<String>` 中，最後再轉換為 `String[]` 回傳（例如利用 `arrayList.toArray(new String[0])`）。
3. 為了避免逗號與句號等標點符號干擾比對（例如 `"apple,"`），建議在建構子中呼叫 `super(s, " ,.");`，將空格與標點符號都設定為分隔符號 (Delimiters)。

</details>


## 2.2 一法多形：多型 

「多型」(Polymorphism) 是物件導向的三大支柱之一。它的字面意義是「多種形式」。在 Java 中，多型允許我們將子類別物件視為父類別物件來處理，這使得程式碼具備極高的靈活性與擴充性。

### 2.2.1 多型的核心觀念

1.  **向上轉型 (Upcasting)**：子類別物件可以自動轉換為父類別型態。這也是多型發生的前提。
2.  **方法覆蓋 (Method Overriding)**：父類別定義介面，子類別提供不同的實作。
3.  **動態綁定 (Dynamic Binding)**：JVM 在執行時期才決定呼叫哪一個類別的方法。

```java
class A {
  void m1() {
      System.out.println("執行 A 的 m1");
  }
}

class B extends A {
  @Override
  void m1() {
      System.out.println("執行 B 的 m1");
  }
}

class Client {
  void op1(A a) {
    // 這裡只知道 a 是一個 A，但不知道它具體是 A 還是 B
    a.m1(); 
  }
}
```

對 `Client` 的 `op1` 而言，`a` 可能是一個 `A` 的物件或是 `B` 的物件，取決於 runtime 時帶進的物件。**不要以為 a 的型態宣告為 A，就認為它執行的是 A 的邏輯。** runtime 時才做 binding, 稱之為 **dynamic binding**。

```java
Client c = new Client();
c.op1(new A()); // 輸出：執行 A 的 m1
c.op1(new B()); // 輸出：執行 B 的 m1 (這就是多型！)
```

### 2.2.2 為什麼需要多型？

假設我們正在開發一個繪圖軟體，需要管理各種形狀。

**沒有多型時：**
如果要繪製圓形和正方形，我們可能需要針對每種形狀寫不同的方法：
`drawCircle(Circle c)`, `drawSquare(Square s)`... 每增加一種形狀，主程式就要修改一次。

**有了多型後：**
我們定義一個父類別 `Shape`，並讓所有形狀繼承它。

```java
abstract class Shape {
    abstract void draw();
}

class Circle extends Shape {
    void draw() { System.out.println("畫一個圓"); }
}

class Square extends Shape {
    void draw() { System.out.println("畫一個正方形"); }
}

class DrawingApp {
    // 使用多型：只需接收 Shape 即可處理所有子類別
    void render(Shape s) {
        s.draw();
    }

    public static void main(String[] args) {
        DrawingApp app = new DrawingApp();
        app.render(new Circle());
        app.render(new Square());
        // 未來新增 Triangle, 這裡的 render 方法完全不需要改動！
    }
}
```

這體現了設計模式中的 **開閉原則 (Open-Closed Principle)**：對於擴充是開放的，對於修改是封閉的。

### 2.2.3 多型容器

多型讓不同的物件可以儲存在同一個集合中，稱之為 **多型容器 (Polymorphic Collections)**。

```java
ArrayList<Shape> shapes = new ArrayList<>();
shapes.add(new Circle());
shapes.add(new Square());

// 統一處理不同類型的物件
for (Shape s : shapes) {
    s.draw();
}
```

汽車的例子：
```java
class VehicleController {
  void manage(Vehicle v) {
     v.turnLeft();
  }   
}
``` 

```mermaid
classDiagram
    class VehicleController {
        +manage(Vehicle v)
    }
    class Vehicle {
        <<abstract>>
        +turnLeft()*
    }
    VehicleController ..> Vehicle : uses
```

---

### **2.2.4 編譯時型態 vs. 執行時型態**
理解多型的關鍵在於區分「變數的型態」與「物件的型態」。

- **編譯時型態 (Compile-time Type)**：變數宣告時的型態（例如 `Vehicle v`）。編譯器以此決定變數能呼叫哪些方法。
- **執行時型態 (Runtime Type)**：程式執行時實際上由 `new` 產生的物件型態（例如 `new Car()`）。

**動態綁定 (Dynamic Binding)**：
當呼叫 `v.turnLeft()` 時，即便 `v` 的編譯時型態是 `Vehicle`，Java 也會根據執行時型態（`Car`）來決定要執行哪一個版本的 `turnLeft()`。這就是多型的核心威力。

### 🔍 觀念測驗 2.2

1️⃣ **以下會印出什麼**   
```java 
public class Game {
  public static void main(String[] args) {
    ChessBoard cb = new LongChessBoard();
    cb.show();
  }
}
class ChessBoard{
  public void show() { System.out.println ("一般象棋");}
}
class LongChessBoard extends ChessBoard{
  private void show() { System.out.println("長棋");}
}
```

2️⃣ **以下會出現什麼訊息？**
```java
class A {
   public A() {
      System.out.println("hi");
   }
}
class B extends A {
}

public class Main {
     public static void main(String[] arg) {
          B b = new B();
     }
}
```
<details>
<summary>提示</summary>

子類別會自動的呼叫父類別的預設建構子。所以會印出 hi

</details>

3️⃣ **回答以下問題：**
```java
class A {
    int max(int x, int y) {
      if (x>y) return x;
      else return y;
   }
}
class B extends A {
    int max(int x, int y) {
      return super.max(y, x) - 10;
    }
}
A a = new B();
a.max(100,20)=?
```

4️⃣ **回答以下問題**

我們宣告 print(Object) 於下方。以下哪些不會造成 compiler 錯誤 
```java
public static void print(Object x) {...}
```
1. print(new Object());
2. print(new Employee());
3. print(12);
4. print("abc");
5. print(new Integer(12));	

   D) `final` 類別可以被繼承但不能被實例化  

<details>
<summary>👉 點擊查看答案</summary>

參考答案：
1. **一般象棋** (子類別覆寫方法時若權限設為 `private` 會導致編譯錯誤，但以此題 code 來看，若能編譯執行，動態綁定會呼叫實際物件的方法)
2. **hi** (子類別建構時會先呼叫父類別建構子)
3. **90** (動態綁定會呼叫 B 的 max，此方法會調用 `super.max(20, 100)` 得到 100，再減去 10)
4. **1, 2, 3, 4, 5** (這題考的是 Upcasting。在 Java 中，所有類別都繼承自 Object，因此任何物件、字串、甚至經過 Auto-boxing 的基本型態都能傳入)
5. **B** (多型分為編譯時多型（Overloading）與執行時多型（Overriding）)
</details>

### ✍ 練習 2.2

#### 📌 練習 2.2.1：不同族群的健康檢查
People 內部宣告一個 `boolean overWeight()` 的抽象方法。People 的建構子會帶入身高體重。Student 和 Athlete 都是 People 的子類別，前者的 bmi > 24 時過重，後者超過 22 時過重。請實作之。

---

#### 📌 練習 2.2.2：薪資系統
請建立一個抽象類別 `Employee`，包含一個抽象方法 `double calculateSalary()`。建立兩個子類別：
1. `FullTimeEmployee`：擁有固定月薪。
2. `PartTimeEmployee`：根據時薪 (`hourlyRate`) 與工作時數 (`hoursWorked`) 計算。
練習建立一個 `ArrayList<Employee>`，存入不同類型的員工，並計算出總薪資支出。

<details>
<summary>參考解答</summary>

* [練習 2.2.1：不同族群的健康檢查](../Demo/src/main/java/swfw/ch02/ex2_2/Exercise2_2_1.java)
* [練習 2.2.2：薪資系統](../Demo/src/main/java/swfw/ch02/ex2_2/Exercise2_2_2.java)

</details>

## 2.3 無色無相：介面

介面定義一個規格，一個多個物件之間彼此溝通的規格，但他僅定義規格，並不描述其實作方法。Java 中介面的宣告如下：

```java
interface E {
   public void m1();
   public void m2();
}
```

m1() m2() 預設都是 `public abstract` 的抽象方法，不需要顯式宣告。

當一個類別實踐一個介面，表示它必須實踐這個規格。D 必定要實作 m1() 與 m2()，因為這兩個方法都宣告在介面 E 中。

```java
public class D implements E {
  public void m1() {
    ... //實作
  }
  public void m2() {
    ... //實作
  }
}

class Client { //Client 是介面的使用者   
   void m (E e} {
       e.m1();
   }   
}
```


> [!NOTE]
> * 能做什麼，是類別
> * 該做什麼，是介面
> * 能做什麼，又該做什麼，是抽象類別

```mermaid
classDiagram
    class Vehicle {
        <<interface>>
        +left()*
        +right()*
    }
    class Car {
        +left()
        +right()
    }
    class Bike {
        +left()
        +right()
    }
    Vehicle <|.. Car
    Vehicle <|.. Bike
```

```mermaid
classDiagram
    class Vehicle {
        <<abstract>>
        countWheel
        color
        +left()*
        +right()*
    }
    class Car {
        +left()
        +right()
    }
    class Bike {
        +left()
        +right()
    }
    Vehicle <|-- Car
    Vehicle <|-- Bike
```

### 2.3.1 介面實踐與使用

> [!NOTE]
> * 一個好的建築，需要有一個人會蓋，一個人會欣賞。
> * 一個介面，需要有類別去實作，也需要有 client 去使用。


只要能實踐 E 的物件， m1() 都可以呼叫使用。

### 2.3.2 繼承和實作的異同

* 兩者都具備多型，也就是說，當 `class C extends D implements E`, 則 `c instanceof D`, `c instanceof E` 都是 true;
* extends 享受到 code reuse 的好處，但 implements 沒有（因為 interface 內沒有程式碼），它只有被規範**要去履行介面所定義的功能**。


> [!NOTE]
> 他一生下來，就背負著「皇帝」的使命，對它來說，是一種責任，一種規範。從這個觀點來看，皇命是一個介面（規格）實踐。

> [!NOTE]
> 他一生下來，就擁有龐大的繼承資源，即使什麼都不會做，很多是還是順理成章的完成了。從這個觀點來看，皇命是一個資源的繼承。


### 2.3.3 多重繼承

Java 所謂的多重繼承是指多重的**介面**繼承。一個類別可以實作很多的介面，但只能繼承一個類別。類別 G 繼承類別 C 並實作介面 E 與 F 是被允許的。

**為什麼 Java 不允許類別的多重繼承？**
主要原因是為了避免**菱形繼承問題 (The Diamond Problem)**。
假設類別 A 有一個 `move()` 方法，類別 B 與 C 都繼承 A 並分別覆寫了 `move()`。如果類別 D 同時繼承 B 與 C，當 D 呼叫 `move()` 時，編譯器將無法判斷該執行 B 還是 C 的版本。這會造成嚴重的邏輯混淆。

```mermaid
graph TD
    A[Class A: move] --> B[Class B: move]
    A --> C[Class C: move]
    B --> D[Class D: ???]
    C --> D
```

**介面如何解決這個問題？**
1. **傳統介面**：因為方法都是抽象的（沒有實作），即使類別 G 實作了兩個擁有相同方法簽署的介面 E 和 F，G 最終也只能提供**一份實作**，衝突自然消失。
2. **預設方法 (Default Methods)**：如果 E 和 F 都有同名的 `default` 方法，Java 會強制類別 G **必須覆寫**該方法，手動決定要使用哪一個（或是提供全新的實作）。

```java
public class G extends C implements E, F {
  @Override
  public void op2() {
     E.super.op2(); // 明確指定使用 E 介面的預設實作
  }

  public void op1() { ... }
  public void op4() { ... }
}
```
```java
  interface Vehicle {
     // 右轉最大角度常數
     public final static int MAX_TURN_ANGLE = 60;
     
     // 一定要能右轉，左轉
     public void turnRight();
     public void turnLeft(); 
  }
```


### 2.3.4 介面的應用：best

假設我們要寫一個副程式來找到三個整數中最大的一個，相信這很簡單：

```java
   public int best(int x, int y, int z) {
      if (x > y) {
          if (x > z) ? return x: return z;
      }
      else if (y > z ) {
         return y;
      }
      else return z;
   }         
```

如果要比較的不是一般數字呢？

> [!NOTE]
> 一般化：整數比較 $\Longrightarrow$ 物件比較

**Comparable 介面**

任何物品只要符合 `Comparable` 的介面都是可以比較的。針對 Comparable 我們設計一個 `best(x, y, z)` 的方法來比較三個物品，該方法將回傳最「好」的物件。

```java
interface Comparable {
    public boolean betterThan(Comparable x);
}

class Util {
    public static Object best(Comparable x, Comparable y, Comparable z) {
        if (x.betterThan(y)) {
            if (x.betterThan(z))
                return x;
            else
                return z;
        } else if (y.betterThan(z)) {
            return y;
        } else
            return z;
    }
}
```

如果我想比較水果，我該如何修改以下的 Fruit 類別？我們只要定義什麼是「好水果」即可，下面的例子是以 sweetDegree (甜度) 作為好水果的標準。

```java
class Fruit implements Comparable {
    String name;
    int price;
    int sweetDegree;
    int waterDegree;

    public static void main(String args[]) {
        Fruit f1 = new Fruit(12), f2 = new Fruit(23), f3 = new Fruit(9);
        Fruit best = (Fruit) Util.best(f1, f2, f3);
    }

    public boolean betterThan(Comparable x) {
        if (x instanceof Fruit)
            if (this.sweetDegree > ((Fruit) x).sweetDegree)
                return true;
            else
                return false;
        else {
            System.out.println("錯誤的比較");
            return false;
        }
    }

    public Fruit(int sweetDegree) {
        this.sweetDegree = sweetDegree;
    }
} 
```

```mermaid
classDiagram
    class Comparable {
        <<interface>>
        +boolean compare()
    }

    class Fruit
    class Student
    class Book

    Comparable <|.. Fruit
    Comparable <|.. Student
    Comparable <|.. Book
```

設計一個 Student 的類別，也透過 `Comparable` 介面、`Util.best` 來比較學生（以學生的成績作為比較的基準）。

> [!WARNING]
> Student 和 Fruit 都實踐了 `Comparable`，那 Student 可以和 Fruit 相互比較嗎？如果不能，上述的程式需要如何修改？

> [!WARNING]
> 上述的程式的優點為何？從 reuse 的角度來看，我們 reuse 了什麼？

> [!TIP]
> :thinking_face: Collections.sort()
> 
> Study [This example](https://medium.com/@thecodebean/java-object-sorting-explained-using-comparable-and-comparator-03b93b988f75)
> 
> Think: how the Java API design the framework?

### 2.3.5 介面內的常數

Interface 可以宣告常數
```java
interface Vehicle {
    //右轉最大角度
     public final static int MAX_TURN_ANGLE = 60;
     public void turnRight();
}
```

只能宣告常數，不能宣告 instance variable。


### 2.3.6 抽象的應用：NNEntity

大家都寫過 99 乘法表:

```
    1   2   3   4   5   6   7   8   9
    ==================================
1   1   2   3   4   5   6   7   8   9
2   2   4   6   8   10  12  14  16  18
3   3   6   9   12  15  18  21  24  27
4   4   8   12  16  20  24  28  32  36
5   5   10  15  20  25  30  35  40  45
6   6   12  18  24  30  36  42  48  54
7   7   14  21  28  35  42  49  56  63
8   8   16  24  32  40  48  56  64  72
9   9   18  27  36  45  54  63  72  81
```

我們現在把這個程式「一般化」（generalization）-- 變成 NN 乘法表。這很很簡單，參數變化就可以做到。

```java
public void multiply(int x, int y) {
   ...
}   
```

ps. NN 只是取名，上例我們的變數 xy, 表示我們可以做一個 1..x, 1..y 的乘法表。例如 34 乘法表：

```
    1   2   3  
    ==========
1   1   2   3 
2   2   4   6 
3   3   6   9 
4   4   8   12
```


那我們再把這個問題更一般化些：如果「乘」的主體不限定是整數呢？例如字串相乘，什麼是字串相乘？我們需要依據我們的需求來做定義：在此定義為「字串相連」。也可以「顏色相乘」，其效果就是顏色相混。也可以是「化學物質」想混合。如何進行這個設計讓重用性高一點？

```
字串相'乘'
    ab     xy      pq  
    ==================
ab  abab   abxy   abpq 
xy  xyab   xyxy   xypq
pq  pqab   pqxy   pqpq
```


> [!NOTE]
> 一般化：9\*9 $\Longrightarrow$ N\*N $\Longrightarrow$ 物件 **R** 物件

> [!NOTE]
> 以下是局部的程式碼，該如何設計 NNEntity? 整數 (NNInteger)、字串 (NNString)、顏色 (NNColor) 又該如何設計？

顏色的混和：

```
顏色相'乘'
     紅色    綠色   黃色
     ==================
紅色  紅色    棕色   橙色
綠色  棕色    綠色   黃綠
黃色  橙色    黃綠   黃色
```


**一個抽象的 NNEntity，都會「乘」**

```java
abstract class NNEntity {
    public abstract NNEntity multiply(NNEntity otherone);
}
```

**整數的「乘」**

```java
class NNInteger extends NNEntity {
	private int number;

	public NNInteger(int number) {
		this.number = number;
	}

	public NNInteger(NNInteger copy) {
		this(copy.number);
	}

	// 數字相乘
	public NNEntity multiply(NNEntity otherone) {
		if (otherone == null) {
			return null;
		} else if (getClass() != otherone.getClass()) {
			return null;
		} else {
			NNInteger otherone2 = (NNInteger) otherone;
			return new NNInteger(this.number * otherone2.number);
		}
	}

	public String toString() {
		return Integer.toString(number);
	}
}
```

**字串的「乘」**

```java
class NNString extends NNEntity {
	private String words;

	public NNString(String words) {
		this.words = words;
	}

	public NNString(NNString copy) {
		this(copy.words);
	}

	// 字串相連
	public NNEntity multiply(NNEntity otherone) {
		if (otherone == null) {
			return null;
		} else if (getClass() != otherone.getClass()) {
			return null;
		} else {
			NNString otherone2 = (NNString) otherone;
			return new NNString(this.words + otherone2.words);
		}
	}

	public String toString() {
		return words;
	}
}
```

**不論是哪一種型態，TableDisplayer 都一樣**

```java
class TableDisplayer {
    public static void multiplyAndShow(NNEntity[] xList, NNEntity[] yList) {
        /* Multiply */
        NNEntity[][] table = new NNEntity[yList.length][xList.length];
        for (int i = 0; i < yList.length; i++) {
            for (int j = 0; j < xList.length; j++) {
                table[i][j] = xList[j].multiply(yList[i]);
            }
        }
        /* Show */
        System.out.printf("%7s", "");
        for (int i = 0; i < xList.length; i++) {
            System.out.printf("%7s", xList[i]);
        }
        System.out.println();
        for (int i = 0; i < yList.length; i++) {
            System.out.printf("%7s", yList[i]);
            for (int j = 0; j < xList.length; j++) {
                System.out.printf("%7s", table[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
```
**主程式**

```java
package basic;
/*
 * This is an ERROR code, for students to fix
 */
public class NNMultiplication {
    public static void main(String[] args) {
        // 整數對整數
        NNEntity[] xListA = { new NNInteger(2), new NNInteger(3), new NNInteger(5), new NNInteger(6), new NNInteger(10) };
        NNEntity[] yListA = { new NNInteger(7), new NNInteger(2), 	new NNInteger(3), new NNInteger(4), new NNInteger(8) };
        TableDisplayer.multiplyAndShow(xListA, yListA);

        // 字串對字串        
        NNEntity[] xListB = { new NNString("Q"), new NNString("D"), new NNString("T"), new NNString("H"), new NNString("Z") };
        NNEntity[] yListB = { new NNString("Y"), new NNString("D"), new NNString("Z"), new NNString("V"), new NNString("B") };
        TableDisplayer.multiplyAndShow(xListB, yListB);

        // 顏色對顏色
        NNEntity[] xListC = { new NNColor("Red"), new NNColor("Red"), new NNColor("Red"), new NNColor("Green"), new NNColor("Blue") };
        NNEntity[] yListC = { new NNColor("Green"), new NNColor("Blue"), new NNColor("Red"), new NNColor("Blue"), new NNColor("Green") };
        TableDisplayer.multiplyAndShow(xListC, yListC);
    }
}
```

> [!WARNING]
> 這樣的程式重用了什麼？

> [!WARNING]
> 上述程式 NNEntity 可否改為 interface? 請實做看看



也試著說明 NNEntity 如何實踐物件特性：封裝、繼承、介面、多型。

### 2.3.7 現代 Java 特性：介面進階

> [!NOTE]
> **現代 Java 補充**：自 Java 8 起，介面可以使用 `default` 關鍵字提供預設實作，並支援 `static` 方法；Java 9 起更支援 `private` 方法供內部共用邏輯。
> 
> ```java
> interface SmartDevice {
>     void operate(); // 抽象方法
> 
>     default void powerOn() { // 預設實作 (Java 8+)
>         log("Powering on...");
>         System.out.println("Device is now ON.");
>     }
> 
>     static void basicCheck() { // 靜態方法 (Java 8+)
>         System.out.println("System integrity check: OK.");
>     }
> 
>     private void log(String msg) { // 私有方法 (Java 9+)
>         System.out.println("[LOG]: " + msg);
>     }
> }
> ```
> 
> **為什麼要這樣改？** 主要原因在於 **向後相容性 (Backward Compatibility)**。
> 考慮 Java 的 `Iterable` 介面：在 Java 1.5 引入時，它只有 `iterator()` 方法。到了 Java 8，開發者想增加 `forEach()` 方法來支援 Lambda。
> 
> 如果直接新增一個抽象方法 `void forEach(...)`，全世界所有實作了 `Iterable` 的自定義類別（例如某個舊系統s的 `MyList`）都會**編譯失敗**，因為它們沒有實作新加的方法。
> 
> 透過 `default` 關鍵字，Java 官方可以直接在介面中提供 `forEach()` 的預設邏輯。舊的類別不需要修改任何程式碼就能直接升級到 Java 8 並繼續執行，甚至能自動「繼承」到這個新功能。

### 🔍 觀念測驗 2.3

1️⃣ **宣告一個 Moveable 的介面，應用在 Chess 系統。**

2️⃣ **從以下項目比較 class, abstract class, interface：**
* 可具備抽象方法
* 可具備 "非" 抽象方法
* 可生成物件
* 可具備實體變數
* 可具備常數
* 是一種型態

<details>
<summary>提示</summary>

| -                    | class | abs class | interface |
| -------------------- | ----- | --------- | --------- |
| 可具備抽象方法       | x     | v         | v         |
| 可具備 "非" 抽象方法 | v     | v         | x         |
| 可生成物件           | v     | x         | x         |
| 可具備實體變數       | v     | v         | x         |
| 可具備常數           | v     | v         | v         |
| 是一種型態           | v     | v         | v         |

</details>

3️⃣ **回答以下問題**

介面會有實作者 (implementer) 和用戶 (client)。下面程式中，哪個是實作者？哪個是用戶?
```java
interface IA {
   void m1();
}
class A implements IA {}
class B {
   void m2(IA a) {
      a.m1();
   }
}
```

4️⃣ **請改以 interface 的方式重新設計：A 不要直接對 B, 而是對 「會做 m1()」 的物件。**

```java
 class A {
     void op1(B b) {
        ...
        b.m1();
     }
 }
 class B {
    public void m1() {
       ...
    }
 }
```

5️⃣ **以下哪裡錯？**

下述程式為某系統的部分程式碼，是否會發生編譯錯誤？執行錯誤？

```java
interface IA {
  void m1();
}

class B {
    void m1(IA a) {
      a.m1();
    }
}
```

6️⃣ **以下程式有哪些錯誤？**

```java
interface IA {
    private int x = 100;
    int y;
    int z=100;
    public static final int P = 200;
    public void m1();
    
    protected void m2();
    int m3() {
      return 100;
    }

    void m4();
}

interface IB extends IA {
    void m5();
}

class B implements IA {
    public void m1() {}
    int m3() {}
}

class C implements IB {
    public void m5() {}
}

abstract class D implements IA {
    abstract public void m1();
    void m4() {} //注意沒有 public
}

class D {
   void m1() {
       System.out.println(IA.z + "");
   }

   void m2() {
       IA a = new B();
       a.m1();
   }

   void m3() {
       IA a = new IA();
       a.m1();
   }
}
```

**Hint:**
```java
String s = super.nextToken();	
```    

### ✍ 練習 2.3
#### 📌 練習 2.3.1：SuperStringTokenizer

請寫一個 `SuperStringTokenizer`, 它除了可以解析字元以外,還會把 解析的字元轉成大寫回傳回來。
* 透過繼承 `SuperStringTokenzier` 繼承 `StringTokenizer` 來實作。(注意 `SuperStringTokenizer` 將 override 父類別的 nextToken()
* 透過委託 `SuperStringTokenizer` 將 **包含** `StringTokenizer`。你一樣要宣告一個 `nextToken()` 來傳回每一個大寫字元的 token。	


#### 📌 練習 2.3.2：星座速配幸運號碼表
* 請參考  NNEntity 的例子，製作一個星座速配幸運號碼表，例如 巨蟹座X雙子座 => 06220522 % 144 => 10 是此搭配的幸運號碼，其中0622是巨蟹的起始日，0522是雙子的起始日，% 表示取餘數。
* TableDisplay 可以用 System.out.print 來輸出此幸運號碼表; 但以 HTML table 的方式輸出更好。

<details>
<summary>Hint</summary>

```mermaid
classDiagram
    class NNEntity {
        <<interface>>
        +multiply(NNEntity) Object*
    }
    class Sign
    class TableDisplayer {
        -xList: List of NNEntity
        -yList: List of NNEntity
        +TableDisplayer(xList, yList) void
        +displayTable() void
    }

    class TableDisplayer2 {
        +displayTable(list of NNEntity, list of NNEntity)$
    }

    TableDisplayer o-- NNEntity
    NNEntity <.. TableDisplayer2
    NNEntity <|-- Sign
    NNEntity <|-- Integer 
    NNEntity <|-- String
```

</details>


<details>
<summary>參考解答</summary>

* [練習 2.3.1：SuperStringTokenizer](../Demo/src/main/java/swfw/ch02/ex2_3/Exercise2_3_1.java)
* [練習 2.3.2：星座速配幸運號碼表](../Demo/src/main/java/swfw/ch02/ex2_3/Exercise2_3_2.java)

</details>

## 2.4 綜合練習

### ✍ 練習 2.4.1：圖形介面實作
> * 建立一個 `Shape` 的介面，裡面有 `getArea()` 來回傳面積
> * 建立圓形 (`Circle`)、正方形(`Square`)、矩形(`Rectangle`)、三角形(`Triangle`)等圖形的類別，實作Shape

### ✍ 練習 2.4.2：學生排序
> * 應用 `Comparable（內有 int compareTo(Comparable) 方法)` 介面來寫一個排序的程式，並且用來排序以下的物件。
> 	* 一個類別 Student, 裡面的屬性包含身高、體重、成績，如果 「身高+成績-體重」 比較較高，則較好。
> 	* 請以 selection sort 來完成此作業

### ✍ 練習 2.4.3：象棋翻棋遊戲
> * 考慮一個象棋翻棋遊戲，32 個棋子會隨機的落在 4*8的棋盤上。透過 Chess 的建構子產生這些棋子並隨機編排位置，再印出這些棋子的名字、位置
> 	* ChessGame
> 	    * void showAllChess(); 
> 	    * void generateChess();
> 	* Chess: 
> 	    * Chess(name, weight, side, loc); 
> 	    * String toString();	
> * 同上， 
>     * ChessGame 繼承一個抽象的 AbstractGame; AbstractGame 宣告若干抽象的方法：
>         * setPlayers(Player, Player)
>         * boolean gameOver()
>         * boolean move(int location)
> * 撰寫一個簡單版、非 GUI 介面的 Chess 系統。使用者可以在 console 介面輸入所要選擇的棋子的位置 (例如 A2, B3)，若該位置的棋子未翻開則翻開，若以翻開則系統要求輸入目的的位置進行移動或吃子，如果不成功則系統提示錯誤回到原來狀態。每個動作都會重新顯示棋盤狀態。
> * 規則：請參考 [這裏](https://zh.wikipedia.org/wiki/%E6%9A%97%E6%A3%8B#%E5%8F%B0%E7%81%A3%E6%9A%97%E6%A3%8B)
> 
> ```
>    1   2   3  4   5  6   7   8
> A  ＿  兵  ＿  車  Ｘ  ＿  象  Ｘ
> B  Ｘ  ＿  包  Ｘ  士  ＿  馬  Ｘ   
> C  象  兵  Ｘ  車  馬  ＿  ＿  將 
> D  Ｘ  包  ＿  士  兵  Ｘ  ＿  Ｘ  
> ```

<details>
<summary>參考解答</summary>

* [練習 2.4.1：圖形介面實作](../Demo/src/main/java/swfw/ch02/ex2_4/Exercise2_4_1.java)
* [練習 2.4.2：學生排序](../Demo/src/main/java/swfw/ch02/ex2_4/Exercise2_4_2.java)

</details>
