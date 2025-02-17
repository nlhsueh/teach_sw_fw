###### tags: `OOSE`


# Ch01 物件基礎設計


## 1.1 萬相皆物：類別

:::success
:point_right: 本節重點

* 何謂類別？和物件的差異為何？
* 類別內包含哪些元素？
* 成員變數與靜態變數的差異為何？
* 物件內的方法與訊息有何關係？
* accessor 和 mutator 是很特殊的方法，其涵義為何？
* Python 中的 property 涵義為何？
* 靜態與非靜態方法的差異為何？
* 在呼叫方法傳遞參數時，針對原生型態變數與物件型態變數要注意什麼？
* 在宣告具備參數的建構子時，要注意什麼？
* 複製建構子的意涵為何？如何宣告？
:::

:::info
在物件導向程式設計中，"類別"（Class）是一個重要的概念。類別是用來描述一組具有相似特徵和行為的物件的模板或藍圖。在程式設計中，物件是類別的實例，也就是根據類別創建的具體對象或實體。
:::

**物件導向程式設計的主體是物件類別**，它們合作完成一個系統的運作。就像人類世界的主體是人，它們合作以完成一件工作。所以在設計物件導向程式時，你可以有點擬人化的思考，這樣有幫助你思考整個程式的架構。

以物件為抽象的中心，而非動作。例如在一個象棋系統中，以物件的角度你會先想到 Chess, ChessBoard, ChessGame 等物件，棋子的移動是附屬在 Chess 中。

```java=
chess1.move(12)
```

如果你是用功能導向，一開始想到的是 move

```java=
move(chess1, 12)
```

又例如傳統的行動電話設計，**撥電話"和**傳簡訊"是功能，被分別設計在手機重要選單上。後來新的手機都有聯絡人，把這兩個功能放到聯絡人裡面，可以說是一種物件設計的思維。

:::info
類別 vs. 物件 $\Longrightarrow$ 設計 vs. 實體
:::

#### 物件的生成

類別是抽象的、被設計出來的，物件則是具體被生成出來的，它在執行時具體的佔據了記憶體，可以執行程式。類別則不能執行，它是你設計出來一字字的程式碼。這就像是 **人是抽象的，張三李四才是具體的人**。人是類別，張三李四則是物件。人不會跑、張三李四會跑。但上帝（？）設計人的時候，讓它具備了眼睛、嘴巴等特性，具備了走路跑步等動作，張三李四就依照「人」這樣的形被創造出來了。

```java=
    // 透過 new 生成物件    
    Person changThree = new Person("張三", 24);
```    

物件雖然有相同的方法（或演算法），但表現出來的行為不一定會一樣，因為它在執行的過程中會參考到物件本身的資料（狀態），所以表現出來的行為還是不同的。

**類別** 通常代表一個真實事件的物體、概念、事務，通常是一個名詞。例如車子、房子等物體; 老師、學生等概念。它通常是一個名詞，有一些特性，有一些能力或功能。 許多寫傳統 C 習慣的人換到 OOP 常會把一個方法當成一個類別，可以先用詞態來檢驗：**類別通常是名詞**。

#### 類別型態

大家應該都知道 int 是一個型態，當我們宣告一個 age 是int 的形態，就表示它有 4 type 來儲存這個變數的值。類別 (class) 也是型態，所以你也可以宣告一個 age 是一個 Age 的類別型態（前提是你要先設計、宣告一個 Age 類別)。相對於類別型態, int, double, long 等就稱為基本型態 (primitive type)。

在Java中，類別型態和原生型態是兩種不同的資料類型，它們具有不同的定義和特性。

1. **類別型態（Class Type）：**
   - 類別型態是使用類別定義的一種資料型態。
   - 在Java中，所有的類別型態都是引用型態（Reference Type），也就是說，它們的變數存儲的是對物件的參考，而不是物件本身。
   - 類別型態的變數可以存儲任何類別的物件或null值。

2. **原生型態（Primitive Type）：**
   - 原生型態是Java語言內建的基本資料型態，它們不是使用類別定義的，而是直接支援在程式中使用的基本型態。
   - 原生型態的變數存儲的是實際的資料值，而不是對資料的引用。
   - 原生型態包括整數型態（如int、long）、浮點數型態（如float、double）、字符型態（char）和布林型態（boolean）等。

#### 屬性與方法 

一個類別可以有很多的屬性 (attribute) 和方法 (method)。例如一個車子類別，它可以有顏色、汽缸大小等屬性; 也可以有移動、停止、左轉、右轉等方法。一個類別所產生的物件，都具有相同的特性，包含屬性與方法，但內容可能不同。例如人都該有眼睛嘴巴，但大小形狀可能不同。

```java= 
public class Bicycle {
    // Bicycle 有三個 屬性
    int cadence; //大盤迴轉速
    int gear; //齒輪比
    int speed;
    // Bicycle 有四個 方法
    public void setCadence(int newValue) {
        cadence = newValue;
    }
    public void setGear(int newValue) {
        gear = newValue;
    }
    public void applyBrake(int decrement) {
        speed -= decrement;
    }
    public void speedUp(int increment) {
        speed += increment;
    }
}
```    

繼承後也可以新增屬性與方法：

```java= 
public class MountainBike extends Bicycle {
    // 繼承以後又多了一些屬性
    public int seatHeight;
    // 繼承以後又多了一些方法
    public void setHeight(int newValue) {
        seatHeight = newValue;
    }
}
```


#### 類別命名

Java 的命名慣例是採用全名。例如學生就取為 Student, 不要叫 STD。不清楚的名字在以後很難維護，別人看不懂，你自己也看不懂。第一個字母大寫 Student,  不要 student。ChessBoard, 不要 chessboard, 不要 Chessboard, Chess\_Board...

### 1.1.1 成員變數

成員變數（members variables）就是剛剛說的屬性。有很多其它的名字：實體變數 (instance variables)、 欄位 (fields)、或是特性 (properties)。**成員變數表達一個類別特殊的屬性或特質**。程式設計師和藝術家都是人，但前者有一個屬性「會的程式語言」，後者沒有，這就是前者的特性、屬性。又例如你要設計一個武俠遊戲，每一個人物可能都會有一個屬性「武功」來儲存它會的武功; 「生命值」來表達它目前的生命力等。

成員變數的值表達「狀態」。例如 棋局 class 有一個成員變數 gameStatus, 它的值可以是


* GAME$\_$OVER: 表達遊戲結束
* START: 表達遊戲開始
* BLACK: 表達目前由黑方下
* RED: 表達目前有白方下

:::info
每一個值表達不同的狀態。**保護屬性的值，盡可能讓它維持私有**。不要讓人家侵門踏戶的修改你的值，這樣這個物件的行為全亂啦。
:::

```java=
class Bicycle {
   private int speed; // 把 speed 宣告為私有（private）
}
```

車子的速度應該由車子自己來控制，如果其它的物件隨便來亂改的話，系統很容易出錯。


宣告為 private 後誰可以修改？外面的類別如果真的需要改的時候怎麼辦？例如人要控制車速，人要怎麼修改 speed? 應該是要透過方法來進行控制，例如踩煞車(brake()) 或是踩油門 (accelerate())等。

除了成員變數，還有其它的變數。

* 區域變數 local variable (或 method variable)。在方法內的變數。
* 參數（parameter）：在方法內宣告的變數。


```java= 
class A {
   String name; //name 是成員變數
   // y 是 參數
   public void m1(int y) {
      int x; // x 是區域變數
   }
}
```

#### 成員變數命名 Variable naming

全名、第一個字小寫，例如：好的寫法: name, speed, numberOfCar。不好的寫法: Name, _name, NumberOfCar, number_of_car。


#### 靜態變數 Static variable (class variable)

又稱為 **類別變數**，表示是所有的物件 **共用** 的。我們如果沒有特別註明它是靜態變數，它就是非靜態變數 -- 每個物件會有自己的一個空間來存變數的值。

例如 Bicycle 有一個變數 size (輪圈)是一個非靜態變數，當我們產生 b1, b2, b3 等腳踏車時

```java= 
class Bicycle {
   private int size;
   ...
}

Bicycle b1=new Bicycle(), b2=new Bicycle(), b3=new Bicycle();
```

b1, b2, b3 都有各自的的空間來存 size 喔。反之，若是宣告為 static, 則大家共用一份。

```java= 
Class Bicycle {
   private static int count=0;
   ...
   Bicycle () {
      count++;
   }
}
```

上述的程式，每當我們生成一個bicycle, count 就會加一，所以 count 可以用來計算物件的數量。

**常數**：不僅共用，也**不能修改**。Math.PI 就可以取到這個值了（不需要生成物件）。


```java= 
class Math {
   static final double PI = 3.141592653589793;
}
```    


定義常數，也是一種物件封裝的風格。不需要去記 PI 的值為多少，你會想到 Math 裡去找它，這就是好封裝的效果。

### 1.1.2 方法


http://docs.oracle.com/javase/tutorial/java/javaOO/methods.html

:::info
方法（method）表示一個物件的能力(capability)或責任(responsibility)。**能力**和**責任**是一體兩面。一個方法（method）可以由以下六個元素組成: 
* 存取權（ public, protected, private）
* 回傳型態 (void, int, String...)
* 方法名稱 (do(), getX()...)
* 參數列 (int x, int y...)
* 例外的形態 (throws)
* 方法本身 (for ...)
:::


當我們在設計一個系統，我們定義出一些類別，指定這些類別應該具備的功能或能力。沙盤推演一下，這樣功能是否能夠滿足全部的需求？如果可以，分配下去。A 類別就必須完成這樣的功能，對它來說，是它應該具備的責任。

在程式的世界中，怎麼把這能力實作出來靠的我們所設計的演算法，也就是執行它的方法。擬人化的說法是：能力與責任。

:::info
物件透過送訊息（message）來呼叫方法
:::

```java= 
//ChessBoard 送了一個訊息 move 給 chess1
class ChessBoard {
    public void play() {
        chess1.move(2,3);
    }
}    
```

送訊息也是一種擬人化的說法，其實就是呼叫物件 chess1去執行 move 的方法。

:::info
方法是物件間溝通的介面。
:::

這裡的介面是一般名詞，不是 java 中的 interface。物件 A 雖然有很多能力和方法，但未必全都會開放給外部的物件呼叫使用。A 提供了一些可呼叫的方法，其實也就是開放了一些介面，讓它物件可以存取我、可以使喚我的介面。

想像一個物件全部的方法都是 private 的（沒有溝通介面），別人都不能呼叫，這樣的物件就沒什麼用了。就像人不能孤獨的過生活不和人溝通交流吧！

#### 方法可能改變物件狀態

假設一個 Date 類別，裡面有 month, date, year 三個屬性，提供 setMonth(int) 的介面修改月份。一旦修改 Date 的值就改變了，也就是狀態改變了。

狀態可以是物件的屬性值，或是經過計算後的一個概念，例如春夏秋冬的概念是日期計算而來的。物件的行為是倚賴於狀態的，假設我們的程式邏輯是依據春夏秋冬的狀態來做事，那們 Date 的狀態就是春、夏、秋、冬：

```java=
// 屬性決定狀態，狀態控制行為
if 春
   耕作;
else if 夏
   耘
else if 秋
   收
else if 冬
   藏
```        

對農作的系統來說，從一月一日變成 一月二日並不具意義，重點是春夏秋冬的變化。對於另一個應用程式，例如星座相關的系統，則狀態又變成 12 個星座了。所以我們在談論系統設計時，多半談**狀態**。

方法，提供了一個介面讓其它的物件來改變本身的值，也就有可能改變狀態，就會改變系統可能的反應了，不得不注意。


#### 參數 (formal vs. actual) parameter: 

* formal parameter: 被呼叫端的宣告，如 `double myMethod(int p1, int p2, double p3)`，律定了對呼叫端的要求。 
* Actual parameter (also called *argument*): 呼叫端，例如 `myMethod(12, 20, 23.0)`。

#### 型態轉換

當 parameter 的形態與 argument 的形態不何時，會進行 type casting（型態轉換）, 如下：左邊的可轉換成右邊的，但右邊的不能轉成左邊的：

> byte, short, int, long, float, double



#### Accessor 和 Mutator

Accessor 和 mutator 是兩種比較特殊的方法，
* Accessor是獲取物件屬性值的方法，例如 getAge(), getHeight()等。
* Mutator是修改物件屬性的方法，例如setHeight(int), setAddress(String)等。
    * mutator 會把物件內部的屬性的值設為所傳進來的參數，但通常會做一些檢查，以控制修改是否合適，例如 setHeight 若帶進來的參數值大於 250 公分顯然不太合理，就可以拋出例外、回傳 false 或終止程式的執行。又稱為 **Getter/Setter**。
    * Mutator 方法也可以回傳 boolean


#### Python 中的 property

```python=
class Car:
    def __init__(self, make, model, year):
        self.make = make
        self.model = model
        self.year = year
        self._speed = 0

    @property
    def speed(self):
        return self._speed

    @speed.setter
    def speed(self, value):
        if value < 0:
            raise ValueError("Speed cannot be negative")
        self._speed = value
```

#### 前置條件、後置條件

方法模組設計以前，先好好的想一想：這個方法的前置條件是什麼？後置條件是什麼


* 前置條件 (precondition): 功能能正常運作的條件。例如一個除法 $div(x/y)$, 前置條件是 $y \neq 0$。
* 後置條件 (postcondition): 功能完成後必須滿足的條件。例如遞增排序後， 必定會滿足 $a[0] \leq a[1]$ 的條件。


後置條件和 invariant (不變的規則) 是不同的。Invariant 是指在此應用領域中不會改變的規則，當它被違反了，有可能就是程式出錯了。例如計算年齡的程式, age >0 , age < 200 是一個不變的規則，我們可以在程式進行過程中不斷的檢驗這樣的規則，以降低程式出錯的機率日後除錯的 effort。

```java=
assert age > 0 && age < 200
```    

直接把屬性設為公開，和透過 accessor, modifier 來公開有什麼不同？

有了前置後置條件的觀念後，上面的問題就迎刃而解了。我們可以在 modifier 中加入前置與後置條件的檢查，這是直接修改公開屬性無法做的功能。

```java=
boolean setHeight(int h) {
   if (h>250) //前置條件
      return false;
   else {
      this.height = h;
      return true;
   }
}
```

#### 靜態方法

靜態方法（static method）又稱為類別方法（非靜態方法又稱為物件方法 instance method）。靜態方法不會存取到物件的狀態（物件的屬性值），也就是說，它的運作和物件的非靜態屬性完全沒有關係。

**靜態方法不可存取成員變數，但可存取靜態變數。**

假設一個 Person class, 內部有一個 birthday 的屬性，及一個 getAge() 的方法：

```java=
int getAge() {
    return (now() - birthday);
}
```    

因為這個方法會使用到物件的屬性（非靜態屬性），所以它是**非靜態方法**。反之，假設 getAge() 是這樣設計的：

```java=
// 此運算與物件的 instance variable 無關，可宣告為 static

static int getAge(Date birth) {
    return (now() - birth);
}
```    


可以看得到它完全不會用到物件的屬性，所以可以宣告為 static method。它的呼叫方法：

```java=
    Person.getAge(new Date(12,1,1990));
```    

注意到它並不需要產生一個物件才去呼叫，直接用類別就可以呼叫。（為什麼？）

:::danger
以下幾點注意：

* 靜態方法不可存取物件變數 (instance variable)
* 靜態方法不可呼叫物件方法 (instance method)
:::


#### 公用程式

很多有用的公用程式（utility function）都被設計成靜態方法，因為它主要的功能是提供一些便捷的運算：$input \Rightarrow (computation) \Rightarrow output$。例如 Math 這個類別裡面提供了大量的數學運算的方法，都是靜態的：sin(double), cos(double)等。記得它們都是有帶參數的。

找找看，Java API 中還有哪些  utility function? Integer這個 class, 裡面有哪些靜態方法？

```java=
Integer.parseInt()
```    

#### 包裝類別（wrapper class）

把每一種基本型態包裝成一個類別型態，例如 int 包成 Integer, double 包成 Double 等。

```java= 
Integer i = new Integer(20);
//透過 intValue 來做開箱(unboxing)
int j = i.intValue();
Integer s = 30; //自動包裝
int k = i; //自動開箱
```

包裝類別內有很多的常數：例如 Integer.MAX$\_$VALUE, Integer.MIN$\_$VALUE, Double.MAX$\_$VALUE, Double.MIN$\_$VALUE 等。也包含很多的靜態方法可以把字串變成基本型態。

```java= 
// parseInt 是靜態方法, 把字串轉成整數
int i = Integer.parseInt("23");
double d = Double.parseDouble("23.4");
// 把整數轉成字串
String s = Integer.toString(100);
```

#### 傳參數

基本上 java 都是傳值（call by value），所以當我們傳一個基本型態的值時

```java= 
int a = 100;
m1(a);
```
    

是把 a 的值傳過去給方法 m1。m1 修改了參數 arg 的值也不會對 a 有所影響。

```java= 
m1(int arg) {
   arg = 200;
}
```

也就是說執行到 m1 時，會提出一個空間來存 100，讓 arg 指到這個空間。a 與 arg 有各自的空間，兩不相擾。

如果傳的是物件型態，就要注意值可能會被方法修改

```java= 
Person p = new Person("Jack");
m2 (p);
```

這時候一樣是傳值，可是 p 的值可不是 Jack 阿（物件型態的值可沒那麼單純），而是存放 p 物件的位址，假設是 #A10 好了。

```java= 
setName(People p2) {
   p2.setName("Nick");
}
```

<!-- ![](https://i.imgur.com/9uZ4Y1W.png) -->

<img src = "https://i.imgur.com/9uZ4Y1W.png" width="500">

這時候 p2 會被建立出來，裡面放的 p1 的值（也就是，拷貝一份 p1 給 p2），所以 p2  的值也是 #A10 了。執行 p2.setName("Nick") 時，就是要原來叫 Jack 的改名為 Nick 了，因為 p1 和 p2 都是指到同一個物件阿。

變數只是一個物件參考、一個別名，它們可能指到同一個實體。

所以如果你不想你的物件值受到可能的修改，記得先複製一份，再傳進去。

```java=
Person p2 = new Person(p1); //應用了 copy constructor
setName(p2);
```

p1 與 p2 有各自的空間。


#### Overloading 方法

同一個類別可以有多個相同名稱的方法，只要參數不同即可。如下：

```java= 
public class DataArtist {
    ...
    public void draw(String s) {
        ...
    }
    public void draw(int i) {
        ...
    }
    public void draw(double f) {
        ...
    }
}
```


注意不能方法參數一樣，只是傳回參數型態不同。下方的程式碼會導致編譯錯誤。

```java= 
public void draw(double f) {
    ...
}
// 回傳型態為 double
public double draw(double f) {
    ...
}
```


#### 任意數量的參數

有時候我們不知道呼叫端會傳多少參數，可利用 `...` 來允許傳任何數量的參數。

```java= 
public Polygon polygonFrom(Point... corners) {

   int numberOfSides = corners.length;
   double squareOfSide1, lengthOfSide1;
   squareOfSide1 = (corners[1].x - corners[0].x)
      * (corners[1].x - corners[0].x)
      + (corners[1].y - corners[0].y)
      * (corners[1].y - corners[0].y);
   lengthOfSide1 = Math.sqrt(squareOfSide1);
    ...
}
```

記得要先用 .length 來取得數量。


#### 應用程式介面 API

和屬性的存取權控制一樣，method 也可以有 public, protected, package, private 等控制權。


### 1.1.3 建構子

建構子的觀念 

* 也是一種方法 method
* 沒有回傳值，也不用 void
* 可以有很多不同參數的建構子
* 建構子可以用來初始化，不同參數的建構子可以做不同的初始化


```java= 
class ClassName {
   public ClassName( parameter) {
     ...
   }
}
```

預設建構子是沒有參數的建構子。但一旦宣告了有參數的建構子，沒有參數的建構子就消失了。

```java=
    class P {
    }
```

上面的 P 並沒有宣告建構子，但你可以這樣呼叫

```java=
    P p1 = new P();
```

因為預設有一個沒有參數的建構子。但如果你宣告了一個有參數的建構子

```java= 
class P {
    public P(int x) {
        ...
    }
}
```    

則 p1 = new P() 就會造成 compile 錯誤。因為我們認為設計者想要強制物件的生成一定要做某個與 x 相關的初始化，所以預設的就會被取消。當然如果你想要也可以加回來：

```java= 
class P {
    public P(int x) {
        ...
    }
    public P() { //加上預設建構子
        ...
    }
}
```    

建構子可以呼叫其它的方法。因為建構子內常常做初始化，這個動作可以交給 mutator 來做。這樣前置條件的設計可以寫在一個地方就好了。

#### 複製建構子

我們可以依照我們的需求生成一個我們想要的手機，只要把相關的參數帶進去就好了。

```java= 
class Phone {
    int memory, size;
    public Phone (int memory, int size) {
        this.memory = memory;
        this.size = size;
    }
}
```    

有時候想要複製一樣的手機，

```java= 
class Phone {
    int memory, size;
    public Phone (Phone p) {
        this.memory = p.memory;
        this.size = p.size;
    }
}
```    

生成時只要呼叫 Phone p2 = new Phone(p1) 就可以生成一個和 p1 一模一樣的手機。上述的建構子就稱為複製建構子。它的形態是：

```java= 
class ClassName {
    public ClassName(ClassName para) {
        ...
    }
}
``` 

Copy constructor 很好用也很重要。設計類別時可多加利用。一個類別可以有很多的建構子。

```java= 
public Bicycle(int startSpeed, int startGear) {
    gear = startGear;
    speed = startSpeed;
}
public Bicycle() {
    gear = 1;
    speed = 0;
}

public Bicycle(Bicycle b) {
    gear = b.gear;
    speed = b.speed;
}
```

### 1.1.x 小節練習

#### Q01 回答以下問題 
:::success
修正以下的錯誤程式：
```java= 
// Filename: Temperature.java
PUBLIC CLASS temperature {
   PUBLIC static void main(string args) {
      double fahrenheit = 62.5;
      double celsius = f2c(fahrenheit);
      System.out.println(fahrenheit + 'F' +
      " = " + Celsius + 'C');
    }
    double f2c(float fahr) {
        RETURN (fahr - 32) * 5 / 9;
    }
   }
}
```
:::

##### EX-chess-class

:::success
一個棋局遊戲中，有一個 `Chess` 類別，內有其名稱 String `name`，權重 int `weight`，位置 int `location`。有一個 `move(int newLoc)` 的功能，執行後會改變位置 (需先檢查 newLoc 是否 < 32); boolean eat(Chess other) 會比較 weight 的大小，若比 other 大則會回傳 true, 並改變位置。請設計此類別，並撰寫一程式測試之。（ps. 假設一簡單的棋奕遊戲即可）
* 同上，若有一類別 ChessGame, 內有一方法 boolean eat(Chess c1, Chess c2)，則此方法可否宣告為 static, 為什麼？也請實作之。
* 同上，若把 location 宣告為一類別，而非整數，有何好處？也請實作之。
:::



## 1.2 陰隱陽顯：封裝

:::success
:point_right: 本節重點

* 物件對於成員變數與方法哪些不同的存取權？目的為何？
* 何謂私有外洩？如何避免？
* 何謂不可變類別？
:::

封裝討論一個類別應該具備哪些屬性、方法及它們的存取權。

<img src=https://i.imgur.com/MxQpdf0.png width=450 />

物件的封裝：屬性透過方法來讓外部取得


### 1.2.1 存取權

除了私有 private 以外，還有其它的存取修飾（存取權）：

* public: 所有的類別都可以存取。
* private: 只有該類別可以存取。
* package: 同一個 package 內的類別可以存取。當我們沒有寫任何 access modifier 時就是 package。
* protected: 子類別可以存取。

可以把類別依據親密程度分成四類：

* Class: 自己
* Package: 朋友
* Subclass: 子孫
* World: 外人

|           | Class | Package | Subclass | World |
| --------- | ----- | ------- | -------- | ----- |
| public    | Y     | Y       | Y        | Y     |
| protected | Y     | Y       | Y        | N     |
| package   | Y     | Y       | N        | N     |
| private   | Y     | N       | N        | N     |


#### Getter, Setter

**getter** 一種特別的方法，專門讓其它類別來取得該屬性的值。例如

```java=
class Person {
   private int age;
   getAge() { //getAge 是一種 getter
      return age;
   }
}
```

**setter** 也是一種特別的方法，專門讓其它類別來設定該屬性。例如

```java=
//可以在 setter 裡面先做一些檢查
boolean setAge(int age) { //setAge 是一種 setter
   if (age < 150) {
      this.age = age;
      return true;
   }
   return false;      
}
```

:::warning
:question: 有 getter, setter 來存取修改變數, 那為什麼不直接把該變數宣告為 public 就好了？
:::

:::spoiler 提示
Setter 可以寫**控制碼**決定是否更改屬性的值，或是如何更改。反之，如果宣告為 public, 雖然權限是允許的，卻無控制權了。
:::

### 1.2.2 私有外洩

我們常會把變數宣告為私有，但它真的有達到私有的效果嗎？不要以為宣告為私有就很安全了。如果是基本型態，宣告為私有就可確保不會有私有外洩。**但如果是 類別型態，就要小心了！！**

即便 Person 宣告生日 birthday 為私有，還是有可能被修改到。

```java= 
class Person {
    private Date birthday;
    public Date getBirthDate() {
        return birthday; //** 危險
    }
}
```

:::warning
如何避免私有外洩（privacy leak）？

* 為你的類別設計拷貝建構子（copy constructor）
* 不要直接回傳物件參考，要拷貝，在回傳拷貝的參考。
:::


記住要 **回傳拷貝的參考**，不要直接回傳該物件的參考。


### 1.2.3 不可變類別  

不可變類別 (Immutable class) 一旦它的值被設定了，就不能修修改了。我們常用的String 是一個不可變類別。

```java= 
String a = "this is a book"
a.append(", not a pen");
```    

append後 a 字串變化了嗎？==沒有==！真實的運作是先產生一個新的字串，它的內容是 **this is a book, not a pen**。所以上述的程式碼應改為以下才有意義。append 後的值可以用 b 或 a 來參考，都可以。

```java= 
String a = "this is a book"
b = a.append(", not a pen"); // ok
// a = a.append(", not a pen"); // ok, too
```    

:::info
不可變類別（immutable）是一個沒有提供可以改變內部值的方法的類別。
:::

不可變類別有什麼好處？因為它的值不會被修改，所以也就不用擔心私有漏出時被亂改了。

**不要寫一個會回傳「可變物件」的方法。** 如果你想要回傳的是一個可變物件，你應該使用拷貝建構子先生成一個物件，再這個物件回傳。打個比喻好了，你的筆記本借給同學，難保不會被同學修改的亂七八糟，怎們辦?

* 拿去影印一份，在給同學。不管同學怎麼修改，你的原稿都是好的。這就是 **copy and write**。
* 你的原稿是用鋼板刻的 - 這樣你的同學就沒有辦法在上面塗鴉啦。這就是 immutable class。


即便 Person 宣告生日 birthday 為私有，還是有可能被修改到。

```java= 
class Person {
    private Date birthday;
    public Date getBirthDate() {
        return birthday; //** 危險
    }
}
```


改成下面的：

```java= 
public Date getBirthDate() {
    // 回傳拷貝參考
    return new Date(birthday);
}
```


但前提是有 Date(Date) 的拷貝建構子，如下：

```java= 
class Date {
   public Date(Date d) {
      this.year = d.getYear();
      this.month = d.getMonth();
      this.date = d.getDate();
   }
}
```

#### 深拷貝 Deep copy

拷貝後的物件和原有的物件不會有任何共同的參考，但有一個例外：**共同的參考是一個不可變物件**。


### 1.2.x 小節練習

#### Q01
呼叫 b.m1() 後 age 的值為多少？
```java=
B b = new B();
class A {
  private int age=12;
}
class B extends A {
  public void m1() {
    age++;
  }
}
```
:::spoiler 提示
會發生錯誤，因為 age 是私有的，即便是子類別也不能存取。
:::


#### Q02
Java 中，以下何者正確？
* (1) 建構子的名稱和類別名稱一樣 
* (2) 一個類別建構子只能有一個 
* (3) 建構子不會有回傳值
:::spoiler Hint
ANS: 1, 3
:::


#### Q03

以下程式否正確
```java=
class A {
   private int a, b; 
   public A(int a, int b) {
      this.a=a;
      this.b=b;
   }
}

class B extends A {
  private int c;
  public B(int a, int b, int c) {
    super(a, b); 
    this.c = c;
  }
}
```

#### Q05 
何者不會造成程式編譯錯誤？

```java=
package p1;
class A { 
   protected int n=0;
}
package p2;
class B extends A {
  (code) 
}
(1) public void m1() {n++;}
(2) public void m1() {B b = new B(); b.n++;}
(3) public void m1() {A a = new A(); a.n++;}
```

:::spoiler 提示
(3) 會造成編譯錯誤。所生成的 a 物件對 A 而言是外部物件，不可存取。
(1, 2) B 是 A 的子類別，所以可以存取 protected 的變數
:::

##### EX-copy-chess
:::success

我們想把一個棋盤上 (ChessBoard) 的所有棋子做一個副本回傳會去，Chess 的 copy constructor 及以下的程式碼該如何設計？
```java=
class ChessBoard {
   Chess[]  ch;
   Chess[] getChesses() {
       ? //copy and return
   }
}
class Chess {
   private name; 
   int priority, loc;
   ? //copy constructor
}   
```
:::

:::success
**EX-intellij-refactor**

使用 Intellij 的工具 refactor, 將一個類別內的屬性進行封裝。
:::


## 1.3 Python 物件設計

:::success
:point_right: 本節重點

* Python 的類別如何宣告？
* `__init__()` 的意義為何？
* `__add__()` 的意義為何？
* 與 Java 比較，Python 中宣告在 class 內的變數是成員變數嗎？
* Python 如何宣告成員變數？
* 在 Python 中如何宣告私有變數？`_` 和 `__` 的差異為何？
* 如何宣告 getter 與 setter?
:::


### 1.3.1 類別

```python=
# 類別的宣告
class class_name():
   def __init__(self, x, y):
      # 一些初始化

   def f1(self, z):
      # 會用到物件變數的函式

   def f2(p, q):
      # 不會用到物件變數的函示

   def getX(self):
      # 取用 x 並計算後回傳的函式

   def setX(self, new_x):
      # 設定 x 的函示

# 物件的生成
obj = class_name()
```

#### List 他們都是物件
物件大家並不陌生，和我們之前在講到的各種資料形態，例如 int、float、list、dict等在Python中
都是用物件來設計，只是過去我們是直接用 所以大家可能沒有感覺。

以 list 物件為例來說明物件的資料與函式。list 內的資料例如一群成績資料是物件的資料，操作這些資料的函式包含 append、extend、insert等是這個物件的方法。我可以透過呼叫這些方法來修改物件內部的資料。

#### People 類別範例

我們再多看了一個例子：我們宣告一個類別 People，內部有一些屬性包含
姓名、身高、體重等。除了身高和體重以外，在健康資訊中我們更重視一個人的BMI值，太高或太低的 BMI 都不好。BMI 是延伸計算出來的。我們可以提供 getBMI 

![People 類別與物件](https://i.imgur.com/oU0Mnx8.png "People 類別與物件")


我們可以設計一個方法 better() 來比較兩個人的健康狀態。


我們可以把兩個人的參數帶進去做比較，因為 People 裏面已經封裝了他的姓名、身高、體重等資訊。

如果沒有用物件導向設計的話，傳進去的參數可能要這麼多，第一個人的姓名、身高、體重，第二個人的姓名、身高、體重，最後才算出來以後， 才去回傳比較健康的那一個人的名字，所以大家可以看出有這樣物件封裝的好處，你的程式的簡潔度也會大大的提升。

```python=
jack = People(...)
jack.height
jack.weight
jack.BMI()

# 透過物件封裝做較好的設計
def better(p1, p2): 
   …

# 參數太多-- 不好的設計
def better(n1, h1, w1, n2, h2, w2):
```

#### Currency 類別範例

我們再多看幾個例子。Currency 代表的是錢的概念，事實上錢不只是一個數字，必須還包含幣值。例如 100台幣或是 100 美金。所以他是包含兩個屬性的資訊，就很適合用一個類別來包裝。

在 Currency 中也會有許多的函式，例如 convert() 來做幣值的轉換; add() 來做錢幣的加總等。下方我們先展示部分的程式碼，讓大家有概念，詳細說明描述在後。

```python==
class Currency:
    def __init__(self, symbol, amount):
        self.symbol = symbol
        self.amount = amount


    def __add__(self, other):
        ...

    def convert(sy1, sy2, amount):
        ...

a = Currency('NTD', 100.0)
b = Currency('USD', 200.0)
print (a, b)
print ('Total is',  (a + b))
```


####  BankAccount 類別範例

一個銀行的帳戶也可以封裝為類別，包含姓名與餘額，姓名用來識別唯一的開戶者（這裡姑且用姓名，比較精準應該用身分證字號）。

BankAccount 會包含一些與帳號相關的函式，例如 deposit() 會存款，帳戶內的金額會增加。withdraow() 會提款，帳戶內的金額會減少。

```python==
class BankAccount():
    '銀行帳號類別，可以存款與扣款'

    def __init__(self, uname, money):       
        self.name = uname     # user name
        self.balance = money  # initial balance                
    def deposit(self, money): 
        # 存錢
        self.balance += money               

    def withdraw(self, money):       
        # 提款
        self.balance -= money               

    def get_balance(self): 
        # 回傳餘額
        return self.balance

nick = BankAccount('Nick', 10000) 
print ('{} 的帳戶有 {} 元'.format(nick.name, nick.get_balance()))

nick.deposit(60000)
print ('{} 的帳戶有 {} 元'.format(nick.name, nick.get_balance()))
nick.withdraw(5000)    
print ('{} 的帳戶有 {} 元'.format(nick.name, nick.get_balance()))
```

#### 物件相關函式


*  type(obj): 返回實現物件的類的名稱。
*  dir(obj)。 返回物件的所有方法和屬性。
*  id(obj)。 返回物件的唯一標識 (內存地址)。
*  hasattr(obj, name)。 檢查屬性是否屬於物件。
*  getattr(物件，名稱，預設值)。 獲取可能屬於某個物件的屬性。
*  callable(物件)。 檢查物件是否可調用，即是否可以調用。


```python=
class Person:
  BMI_UPPER = 30
  BMI_BOTTOM = 25
  WEIGHT_UNIT = 'kg'
  HEIGHT_UNIT = 'm'
  def __init__(self, weight, height):
    self.weight = weight
    self.height = height

p = Person(70, 1.7)
print ('type(): ', type(p))

print ('id(): ', id(p))

print ('hasattr(p, weight): ', hasattr(p, 'weight'))

print ('\ndir() and getattr(): ')
for att in dir(p):
    print (att, getattr(p,att))
```

輸出如下：
```
type():  <class '__main__.Person'>
id():  140392751773008
hasattr(p, weight):  True

dir() and getattr(): 
BMI_BOTTOM 25
BMI_UPPER 30
HEIGHT_UNIT m
WEIGHT_UNIT kg
__class__ <class '__main__.Person'>
__delattr__ <method-wrapper '__delattr__' of Person object at 0x7fafbc193550>
__dict__ {'weight': 70, 'height': 1.7}
__dir__ <built-in method __dir__ of Person object at 0x7fafbc193550>
__doc__ None
__eq__ <method-wrapper '__eq__' of Person object at 0x7fafbc193550>
__format__ <built-in method __format__ of Person object at 0x7fafbc193550>
__ge__ <method-wrapper '__ge__' of Person object at 0x7fafbc193550>
__getattribute__ <method-wrapper '__getattribute__' of Person object at 0x7fafbc193550>
__gt__ <method-wrapper '__gt__' of Person object at 0x7fafbc193550>
__hash__ <method-wrapper '__hash__' of Person object at 0x7fafbc193550>
__init__ <bound method Person.__init__ of <__main__.Person object at 0x7fafbc193550>>
__init_subclass__ <built-in method __init_subclass__ of type object at 0x7fafb0008d30>
__le__ <method-wrapper '__le__' of Person object at 0x7fafbc193550>
__lt__ <method-wrapper '__lt__' of Person object at 0x7fafbc193550>
__module__ __main__
__ne__ <method-wrapper '__ne__' of Person object at 0x7fafbc193550>
__new__ <built-in method __new__ of type object at 0x9085a0>
__reduce__ <built-in method __reduce__ of Person object at 0x7fafbc193550>
__reduce_ex__ <built-in method __reduce_ex__ of Person object at 0x7fafbc193550>
__repr__ <method-wrapper '__repr__' of Person object at 0x7fafbc193550>
__setattr__ <method-wrapper '__setattr__' of Person object at 0x7fafbc193550>
__sizeof__ <built-in method __sizeof__ of Person object at 0x7fafbc193550>
__str__ <method-wrapper '__str__' of Person object at 0x7fafbc193550>
__subclasshook__ <built-in method __subclasshook__ of type object at 0x7fafb0008d30>
__weakref__ None
height 1.7
weight 70
```

在 dir() 中，會回傳所有的屬性與方法，前綴字有 `__` 的表示是私有的內建屬性，我們沒有宣告也會有。其中 `p.__dict__` 紀錄著我們宣告的屬性和他的值。


### 1.3.2 屬性


*  物件屬性 (實體屬性)：每個物件都有個別的一個空間儲存。
*  類別屬性：類別層級，所有物件共用。
*  特性：加上 @property 的屬性，用來設定該屬性的存取。


下面的例子，car\_id 是物件變數，kind 是類別變數。

```python=
class Car:
   kind = '燃油車'             # 類別變數

   def __init__(self, car_id):
      self.car_id = car_id    # 物件變數

print ('類別變數的值：', Car.kind)
c1 = Car('c1')
c2 = Car('c2')
print (c1.car_id, c1.kind)
print (c2.car_id, c2.kind)

Car.kind = '電動車'
c1.car_id = 'c01'
print (c1.car_id, c1.kind)
print (c2.car_id, c2.kind)
```

其產出為：
```
類別變數的值： 燃油車
c1 燃油車
c2 燃油車
c01 電動車
c2 電動車
```

#### 私有屬性

如果一個屬性沒有設為私有，外面的程式可以任意的修改它，這是很危險的：
```python=
'''
balance 不是私有屬性，會被外界修改
'''
nick.balance = nick.balance - 100000
```

Python 宣告私有屬性的方式：加上雙底線，如以下程式中的 \_\_balance:

```python=
# 用 __ 來宣告私有變數
class BankAccount():
    def __init__(self, uname, money):       
        self.name = uname       # user name
        self.__balance = money  # 私有屬性
        
    def deposit(self, money): 
        # 存錢
        self.__balance += money               

    def withdraw(self, money):       
        # 只有透過這個方法才能讓錢減少
        if (self.__balance - money < 0):
          raise Exception('餘額不足')
        else:
          self.__balance -= money               

    def get_balance(self): 
        # 回傳餘額
        return self.__balance

nick = BankAccount('Nick', 10000) 
print ('{} 的帳戶有 {} 元'.format(nick.name, nick.get_balance()))

nick.deposit(60000)
print ('{} 的帳戶有 {} 元'.format(nick.name, nick.get_balance()))
nick.withdraw(5000)    
print ('{} 的帳戶有 {} 元'.format(nick.name, nick.get_balance()))
```


#### 特性

我們有一個新的屬性 riskLevel, 透過 getter, setter 來存取設定他，有時過於麻煩，如下：

```python=
# property 的使用

class BankAccount():
    def __init__(self, uname, money):       
        self.name = uname     # user name
        self.__balance = money  # initial balance                
        self.__riskLevel = 0

    def deposit(self, money): 
        # 存錢
        self.__balance += money               

    def withdraw(self, money):       
        # 提款
        if (self.__balance - money < 0):
          raise Exception('餘額不足')
        else:
          self.__balance -= money               

    def get_balance(self): 
        # 回傳餘額
        return self.__balance

    def set_riskLevel(self, risk_level):
        if self.__balance < 100:
          raise Exception("存款不足，無法設定此風險等級")
        else:
          self.__riskLevel = risk_level   
    
    def get_riskLevel(self):
        level = {0: 'not set', 1: 'Low', 2: 'medium', 3: 'high'}
        return level[self.__riskLevel]

    riskLevel = property(get_riskLevel, set_riskLevel)    
         
nick = BankAccount('Nick', 10000) 

print ('呼叫 getter, setter 來設定 risk level')
print ('Risk level: ', nick.get_riskLevel())
nick.set_riskLevel(2)
print ('Risk level: ', nick.get_riskLevel())

print ('\n因為 risk level 已經被宣告為 property, 所以可以直接設定取用')
print ('Risk level: ', nick.riskLevel)
nick.riskLevel = 3
print ('Risk level: ', nick.riskLevel)
```

改用 @property 語法來做：

```python=
# property 的使用: 透過裝飾品 @ 來撰寫 property
class BankAccount():
    def __init__(self, uname, money):       
        self.name = uname    
        self.__balance = money  
        self.__riskLevel = 0

    def deposit(self, money): 
        # 存錢
        self.__balance += money               

    def withdraw(self, money):       
        # 提款
        if (self.__balance - money < 0):
          raise Exception('餘額不足')
        else:
          self.__balance -= money               

    def get_balance(self): 
        # 回傳餘額
        return self.__balance

    @property
    def riskLevel(self):    
        # 注意這裡不需要有 get_; 直接是該 property 的名稱
        level = {0: 'not set', 1: 'Low', 2: 'medium', 3: 'high'}
        return level[self.__riskLevel]

    @riskLevel.setter
    def riskLevel(self, r): 
        # 注意這裡不需要有 set_; 直接是該 property 的名稱
        if self.__balance < 100:
          raise Exception("存款不足，無法設定此風險等級")
        else:
          self.__riskLevel = r   
    
nick = BankAccount('Nick', 10000) 

print ('Risk level: ', nick.riskLevel)
nick.riskLevel = 2
print ('Risk level: ', nick.riskLevel)
```

要注意 .setter 的宣告一定要在 @property 宣告之後。

#### 再談類別變數

當我們用 `物件.類別屬性` 來設定值時，要注意該屬性是否是可修改的（immutable的。若是不可修改 (如 str)，則會建立一個物件屬性給該物件專用。

```python=
# 類別變數的使用
class Car:

    kind = '燃油車'     # 類別變數
    travel = []         # 類別變數 

    def __init__(self, car_id):
        self.car_id = car_id    # 物件變數

print ('類別變數的值：', Car.kind, Car.travel)
c1 = Car('c1')
c2 = Car('c2')
print (c1.car_id, c1.kind)
print (c2.car_id, c2.kind)

print ('\n修改一些 c1 的類別變數，c1 變成 電動車 且加上 車架')
c1.kind = '電動車'             
c1.travel.append('車架')

print (Car.kind, Car.travel)
print (c1.car_id, c1.kind, c1.travel)
print (c2.car_id, c2.kind, c2.travel)

print ('\n現在直接修改 Car 的類別變數')
Car.kind = '油電混合車'
Car.travel.append('旅行支架')
print (c1.car_id, c1.kind, c1.travel)
print (c2.car_id, c2.kind, c2.travel)
```

#### 動態屬性

在 runtime 的時候宣告物件的屬性，注意動態屬性只屬於該物件，其他同類別的物件並不會同時有該動態屬性。

```python=
class Book:
   pass
   
b1 = Book()
b1.title = 'Design pattern'
print (b1.title)

b2 = Book()
print (b2.title) # Error
```

### 1.3.3 方法

#### 建構子
```python=
# 多重建構子
# 定義一個類別 Person
class Person():
    # 建構子(全部屬性)
    def __init__(self, id=None , name=None , gender=None , address=None , father=None , mother=None):
        self.id = id
        self.name = name
        self.gender = gender
        self.address = address
        self.father = father
        self.mother = mother

# 只給 name 和 address
Jack = Person(name='Jack',address='Taichung')

# 印出名字，而father會顯示None
print(Jack.name,' ',Jack.father)
```

### 1.3.4 特殊方法


```python=
class C():
   def __init()__:
      # 物件建立時會呼叫
 
   def __str()__:
      # print 此物件時會呼叫
 
   def __repr()__:
      # 在命令列直接打物件時名稱時會呼叫
 
   def __iter()__:
      # 迭代
```
      
      
```python=
class Rational:
    'Rational 有理數物件，主要由分子與分母構成'
    
    def __init__(self, n, d):  # 物件建立之後所要建立的初始化動作
        self.numer = n
        self.denom = d
    
    def __str__(self):   # 定義物件的字串描述
        return str(self.numer) + '/' + str(self.denom)
    
    def __add__(self, that):  # 定義 + 運算
        return Rational(self.numer * that.denom + that.numer * self.denom, 
                        self.denom * that.denom)
    
    def __sub__(self, that):  # 定義 - 運算
        return Rational(self.numer * that.denom - that.numer * self.denom,
                        self.denom * that.denom)
                           
    def __mul__(self, that):  # 定義 * 運算
        return Rational(self.numer * that.numer, 
                        self.denom * that.denom)
        
    def __truediv__(self, that):   # 定義 / 運算
        return Rational(self.numer * that.denom,
                        self.denom * that.denom)

    def __eq__(self, that):   # 定義 == 運算
        return self.numer * that.denom == that.numer * self.denom

print (Rational.__doc__)
x = Rational(1, 2)
y = Rational(2, 3)
z = Rational(2, 3)
print(x)       # 1/2
print(y)       # 2/3
print(x + y)   # 7/6
print(x - y)   # -1/6
print(x * y)   # 2/6
print(x / y)   # 3/6
print(x == y)  # False
print(y == z)  # True
```


```python=
class Currency:
    def __init__(self, symbol, amount):
        self.symbol = symbol
        self.amount = amount

    def __repr__(self):
        return '{} {:.3f}'.format(self.symbol, self.amount)

    def __str__(self):
        return '{} {:.2f}'.format(self.symbol, self.amount)

    def __add__(self, other):
        new_amount = self.amount + \
        Currency.convert(other.symbol, self.symbol, other.amount)
        return Currency(self.symbol, new_amount)

    def convert(sy1, sy2, amount):
      rate = {('USD','NTD'):30, ('NTD', 'USD'):0.33}
      if (sy1, sy2) in rate:
        return rate[(sy1, sy2)] * amount
      else:
        raise Exception('No such rate')

a = Currency('NTD', 100.0)
b = Currency('USD', 200.0)
print (a, b)
print ('Total is',  (a + b))
print ('Total is',  (b + a))
```

```python=
class Wallet:
    def __init__(self):
        self.currencies = []
    def put(self, money):
        self.currencies.append(money)

    def __iter__(self):
        for c in self.currencies:
            yield c

wallet = Wallet()
wallet.put(Currency('USD', 10))
wallet.put(Currency('USD', 100))
wallet.put(Currency('NTD', 300))

for i in wallet:
  print (i)
```

#### callable

```python
class MyCallable:
    def __call__(self):
        print("I am callable!")

obj = MyCallable()
obj()  # 可以呼叫具有 __call__() 方法的物件
```

### 1.3.x 小節練習

#### EX-person-bmi
:::success
宣告一個 Person 類別，內有實體屬性姓名，身高與體重。宣告一些方法來獲得一個 Person 的 BMI (Body Measurement Index)、及其健康建議（如果太高就建議運動少吃，太低就建議多吃）。
* bmi 公式:w/(h*h); 其中 h 單位為公尺; w 單位為公斤
* john.BMI
* john.health_advice()
:::

#### EX-chess-python
:::success
一個棋局遊戲中，有一個 Chess 類別，內有其名稱 String name，權重 int weight，位置 int location。有一個 move(int newLoc) 的功能，執行後會改變位置 (需先檢查 newLoc 是否 < 32); boolean eat(Chess other) 會比較 weight 的大小，若比 other 大則會回傳 true, 並改變位置。
* 針對 name, loc, weight, 外界可以取得其值，但不可以改變。用 property 該如何設計？
* 取得 loc 時，會將之轉換為座標 (4*8), 例如10 會回傳 (1,2); 20 會回傳 (2,4) 
:::

#### EX-currency
:::success
完成 Currency 的程式
* nt100 = Currency(100, 'NT')
* us20 = Currency(20, 'US')
* print (nt100 + us20)
:::

## 1.4 綜合練習

:::info
設計一個簡易的象棋系統，ChessGame 會生成 32 個 Chess，並指定其位置、名稱與初始狀態。在 main() 中模擬一些象棋行為，例如移動 move、吃子 kill等。ChessGame 有一個方法可以呈現所有象棋的狀態。See [Hint](https://github.com/nlhsueh/OOSE/blob/master/src/basic/javaclass/ChessApp.java)。
:::
