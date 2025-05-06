###### tags: `OOSE`

# Ch17 神行百變：Decorator

## 17.1 目的與動機

> 可以動態的為一個物件加上功能(責任)。**Decorator** 提供一種除了繼承以外，有彈性的方法來擴充功能。 
>> *Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality*. 

## 17.2 動機

假設我們要做一個文字視窗(`TextView`)，並且提供各種不同的邊框(`border`)與捲軸(`scroll bar`)作為選擇。邊框的型態有：一般型(`Plain`)、3D型或花俏型(`Fancy`)，捲軸的型態有：無捲軸、水平型(`Horizontal`)、垂直型(`Vertical`)與水平垂直型。因此排列組合共有 `3*4=12` 種型別的文字視窗。

<img src="https://i.imgur.com/UhKdMIR.png" width="500">

FIG: 由框和捲軸而成的 `TextView`

### 17.2.1 方案 1: 繼承樹

為了提供各種可能的`TextView`，我們必須建立12種`TextView`的子類別。這種方法不但繁瑣，無法提供動態的物件生成，甚至連命名都很困難。


|                                   |                                       |
| --------------------------------- | ------------------------------------- |
| `TextView-Plain`                  | `TextView-Plain-Vertical`             |
| `TextView-Plain-Horizontal`       | `TextView-Plain-Vertical-Horizontal`  |
| `TextView-3D`                     | `TextView-3D-Vertical`                |
| `TextView-3D-Horizontal`          | `TextView-3D-Vertical-Horizontal`     |
| `TextView-Fancy`                  | `TextView-Fancy-Vertical`             |
| `TextView-Fancy-Horizontal`       | `TextView-Fancy-Vertical-Horizontal`  |


### 17.2.2 方案 2: Strategy 樣式

我們可以透過 `Strategy` 樣式來解決這個問題，在 `TextView` 建立的時候帶入兩個參數，透過參數的組合來形成各種不同的 TextView。程式碼如下：

```java= 
採用策略設計樣式的 TextView
public class TextView { 
    private Border border; 
    private Scrollbar sb; 
    public TextView(Border border, Scrollbar sb) { 
        this.border = border; this.sb = sb; 
    } 
    public void draw() { 
        border.draw(); 
        sb.draw(); 
        // Code to draw the TextView object itself. 
   } 
} 
```

此方法的缺點是缺乏彈性，如果我們在增加新的維度（除了 border, scrollbar 以外的維度），勢必要修該 TextView 的程式碼。

### 17.2.3 方案 3: Decorator 樣式

如果我們使用 `Decorator` 設計樣式一切就會變的容易許多：對 `TextView` 而言，是否增加 `Border` 的功能或捲軸的功能都可以隨意增減，就像是裝飾品一般。新的架構如圖:

![](https://i.imgur.com/Q3JIASW.png)
FIG: 使用 Decorator 來實作 `TextView`

注意我們將各種`Border`與`Scrollbar`視為一種 `Decorator`，而每一個`Decorator`可包含一個以上的`Component`，如`BorderDecorator`可能可以包含有`3D Border`、`Fancy Border`和`Plain Border`等個別裝飾品物件，而 `ScrollDecorator` 可以包含有垂直、水平的 `Scroll Bar`。這樣的架構方式可以讓動態生成的搭配裝飾更多樣性。讓我們來看`Border Decorator`中 `PlainBorder` 的程式片段：

```java=
// 採用 Decorator 樣式
abstract class Decorator extends AbstractTextView {
   AbstractTextView tv;
   // decorator 一定會有一個主要物件
   public Decorator(AbstractTextView tv) {
      this.tv = tv
   }
   public void draw() {
      tv.draw();
   }
}   

abstract class BorderDecorator extends Decorator {
    public BorderDecorator (AbstractTextView c) {
            super(c);
    }
    public void draw () {
        super.draw(); //先讓 component 繪出主要功能
        //做一些 border 的準備
    }
}

class PlainBorder extends BorderDecorator{
    public PlainBorder (AbstractTextView c) {
            super(c);
    }
    public void draw ( ) {
        super.draw( ); 
        //以下繪出一般型(Plain)邊框
    }
}

public class Client {
    public static void main(String[] args) {
        TextView data = new TextView();
        AbstractTextView borderData = new FancyBorder(data);
        AbstractTextView scrolledData = new VertScrollbar(data);
        AbstractTextView borderAndScrolledData = new HorzScrollbar(borderData);
    }
}
```

`PlainBorder`的建構子將傳入一個 `AbstractTextView` 的變數，透過 `super(c)` 來設定其所包含的元件。在 `draw()` 時呼叫 `super.draw()` 會讓所包含的 `textview` 先做它的 `draw()` 再執行 `PlainBorder` 自身的繪圖。因此，當我們想要建立一個`PlainBorder`的`TextView`只要執行以下的命令：

```java=
TextView tv = new TextView ( );
PlainBorder plainTextView = new PlainBorder (tv) ;
```
從「裝飾品」的角度來看，`PlainBorder` 裝飾在 `TextView` 之上，當我們需求 `PlainTV` 繪圖時，它會先要求 `tv` 繪出基本的文字視窗，然後再將邊線繪上。如果我們要繪一個有邊框又有垂直捲軸的文字視窗呢？我們只要在`PlainTextView`上再點綴上一個垂直捲軸即可：

```java= 
VerticalScrollBar verticalPlainTextView = new VerticalScrollBar (plainTextView) ;
```	

`VerticalScrollBar` 的建構子為 `VerticalScrollBar(AbstractTextView c)`，所以 `PlainTV` 可以順利的傳入`VerticalScrollBar` 的建構子中。當 `verticalPlainTextView` 繪圖時，其過程：其中(1)繪出一個文字視窗，(2)繪出一個邊框，(3)繪出垂直和水平的Scroll Bar。看完此例，各位應了解Decorator的用途了。我們接著來看Decorator的基本結構吧。

[gugu decorator](https://refactoring.guru/design-patterns/decorator)

## 17.3 結構與方法

<img src="https://i.imgur.com/KIeMPxd.png" width="500">
FIG: Decorator

### 17.3.1 參與者

- `Component`：系統內元件的配置管理。
- `ConcreteComponet`：系統內主要的功能元件。
- `Decorator`：功能元件的裝飾品管理，管理每一個裝飾品功能物件，使它們能夠動態生成的方式加入到系統中。
- `ConcreteDecoratorA`：實際生成的裝飾品功能物件。

`Decorator` 取名為神行百變，主要是在「百變」上，因為功能可以不斷的附加而改變。「神行」表達這個物件功能的多樣性。插圖中的俠客原有一顆赤子之心，因為行走江湖而不斷的附加許多行頭，例如草帽、臉巾等，但赤子之心仍然不變，象徵著 `Decorator` 的形態都還是 `Component`。

### 17.3.2 優點

透過裝飾品設計樣式，可以讓功能需求像是裝飾品一樣動態的加到系統中，而不會影響到系統本體結構，不會讓架構變的複雜，透過功能包裝成物件的方式可以讓架構更清晰，所屬物件負責工作更明確，進而提昇軟體品質。

### 17.3.3 程式樣板

```java=
package decorator;

abstract class Component {
	abstract void op();
}

class ConcreteComponent extends Component {
	void op() {
		System.out.println("Basic behavior");
	}
}
```

```java=
abstract class Decorator extends Component {
	Component c;

	public Decorator(Component c) {
		this.c = c;
	}

	void op() {
		c.op();
	}
}
```

```java=
class ConcreteDecorator1 extends Decorator {
	public ConcreteDecorator1(Component c) {
		super(c);
	}

	void op() {
		super.op();
		addedBehavior();
	}

	void addedBehavior() {
		System.out.println("Added behavior 1");
	}
}
```

```java=
class ConcreteDecorator2 extends Decorator {
	public ConcreteDecorator2(Component c) {
		super(c);
	}

	void op() {
		super.op();
		addedBehavior();
	}

	void addedBehavior() {
		System.out.println("Added behavior 2");
	}
}
```

```java=
public class DecoratorTemplate {
	public static void main(String[] args) {
		Component cc = new ConcreteComponent();
		cc.op();
		Component c1 = new ConcreteDecorator1(new ConcreteDecorator2(cc));
		c1.op();
	}
}
```


執行結果如下：

<img src="https://i.imgur.com/GawG0J8.png" width="250">


如果我們有 Decorator 子類別 `D1`, `D2`, `D3`, `D4`, decorator 內的功能為 `op()`, 假設每個 `op()` 都是先執行 `super().op()`, 再執行自身的行為（分別為 $f_1-f_4$），ConcreteComponent 的類別為 `CC`。

```java=
 Component d = new D2(new D1(new D3(new D4(new CC()))))
```

的生成方式，執行的順序為：`CC.op()`, `f4`, `f3`, `f1`, `f2`。

## 17.4 範例

### 17.4.1 Java I/O

熟悉 JAVA I/O 的讀者對 Decorator 應該有似曾相識的感覺吧！我們先看 Input Stream 的類別圖：

![](https://i.imgur.com/FcVmyeV.png)
FIG: Java IO- Using Decorator

在 Java I/O 中，`InputStream` 和 `OutputStream` 都有 `BufferedStream`、`DataStream`、`PushbackStream` 的功能需求，其中：

* `BufferedInputStream` 支援緩衝功能。
* `DataInputStream` 支援 Java 基本型態的 I/O。
* `PushbackInputStream` 支援「回復」功能。

但是如果靜態的生成方式必須作出 `2*2*2=8` 種不同搭配的類型，這樣讓系統架構變得很複雜龐大，修改維護上更是麻煩。

的確，Java I/O 的結構使用了裝飾者模式來解決這類型的功能擴充需求。`FilterInputStream` 的角色相當於裝飾者（`Decorator`），而 `BufferedInputStream`、`DataInputStream`、`PushbackInputStream` 等則是 `InputStream` 類別的具體裝飾者（`ConcreteDecorator`）。

以下是一個簡單的 Java 範例，展示如何使用 `FileInputStream` 作為基礎 `InputStream`，並使用 `BufferedInputStream` 和 `DataInputStream` 進行裝飾：

```java
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class InputStreamExample {
    public static void main(String[] args) {
        try (
            // 建立基礎的 FileInputStream
            FileInputStream fileIn = new FileInputStream("example.txt");
            // 使用 BufferedInputStream 裝飾，提供緩衝功能
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            // 使用 DataInputStream 裝飾，提供讀取基本型態的功能
            DataInputStream dataIn = new DataInputStream(bufferedIn)
        ) {
            // 從 DataInputStream 讀取資料
            int intValue = dataIn.readInt();
            double doubleValue = dataIn.readDouble();
            String stringValue = dataIn.readUTF();

            System.out.println("讀取的整數: " + intValue);
            System.out.println("讀取的小數: " + doubleValue);
            System.out.println("讀取的字串: " + stringValue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

在這個範例中：

1.  我們首先建立一個 `FileInputStream` 物件 `fileIn`，它負責從檔案 "example.txt" 讀取原始位元資料。
2.  接著，我們使用 `BufferedInputStream` 包裹 `fileIn`，建立 `bufferedIn`。`BufferedInputStream` 在內部維護一個緩衝區，可以減少實際的檔案 I/O 次數，提高讀取效率。
3.  然後，我們再使用 `DataInputStream` 包裹 `bufferedIn`，建立 `dataIn`。`DataInputStream` 提供了方便的方法來讀取 Java 的基本型態資料（如 `int`、`double`、`String` 等），而不需要手動處理位元轉換。

透過這種方式，我們將不同的功能（檔案讀取、緩衝、基本型態讀取）以裝飾者的方式疊加在一起，每個裝飾者都增強了底層 `InputStream` 的功能，而不需要修改原始的 `FileInputStream` 類別。當我們呼叫 `dataIn` 的讀取方法時，實際上是觸發了一連串的呼叫，最終由最底層的 `FileInputStream` 完成實際的位元讀取。

### 比較

* **Strategy 換骨，Decorator 換皮：**
    * **Strategy 模式** 關注於**演算法或行為的替換**。它允許在執行時改變物件的整個行為或「骨架」。不同的策略提供不同的行為實現，可以完全改變物件的運作方式。
    * **Decorator 模式** 關注於**在不改變物件介面的情況下，動態地增加額外的功能或「裝飾」**。它像給物件穿上不同的「皮膚」，在保持原有功能的基礎上，增加新的功能。核心功能（骨）不變，但外在行為（皮）可以通過裝飾者來增強。

* **Decorator 像洋蔥：**
    * 這個比喻非常貼切。裝飾者模式的結構就像洋蔥一樣，由一層一層的裝飾者包裹著核心的元件（`Component`）。每一層裝飾者都為核心元件添加新的功能。當呼叫最外層裝飾者的操作時，呼叫會層層向內傳遞，直到達到核心元件，然後再層層向外返回，每一層裝飾者在返回的過程中執行其特定的行為。

* **Decorator 像聖誕樹：**
    * 這個比喻也很形象。將核心元件想像成一棵聖誕樹，而每一個裝飾者就像是掛在樹上的裝飾品（例如彩燈、鈴鐺、星星等）。每一個裝飾品都為聖誕樹增添了新的特色和功能，但聖誕樹本身（核心元件）並沒有改變。你可以根據需要自由地添加或移除裝飾者，來組合出不同的功能組合。

這些比喻都強調了裝飾者模式的關鍵思想：**在不改變原有類別結構的基礎上，以組合的方式彈性地擴充物件的功能。**


> Composite 和 Decorator 有何異同？

## 17.CHECK

1. Java 的 `FileInputStream` 用了 Decorator 設計樣式，其中 `FilterInputStream` 相當於此樣式中的

A) Client
B) Decorator
C) ConcreteDecorator
D) Component
E) ConcreteComponent

2. 關於 Decorator pattern, 下列何者為錯

A) Decorator 可以包含一個 Decorator 物件
B) Decorator 和 ConcreteComponent 有部分共同的方法，宣告在 Component 中
C) Decorator 和 ConcreteComponent 都可以包含 Component


3. 請說明 Strategy 和 Decorator 設計樣式的異同。

**簡答:** 

1. B) `Decorator`


2. C) `Decorator` 和 `ConcreteComponent` 都可以包含 `Component`


## 17.EX

### 17.ex01 
有一個物件 A 其基本的功能為 `Basic`，可以從兩方面去擴充，分別為 `X`, `Y`。假設 `X` 方面可以有 `X1`, `X2` 兩種選項，`Y` 有 `Y1`, `Y2`, `Y3` 三種選項。 (1) 若以 Decorator 設計樣式來設計，該如何設計？請畫出 UML 設計圖。(2) 若要產一個具備 `Basic`, `X1`, `Y1` 功能的物件，該如何宣告生成此物件？
- 同上，若以繼承的方法來設計，需要設計多少類別?
- 同上，若改以 Strategy 設計樣式來設計，該如何設計？

### 17.ex02
聖誕樹 (`ChrismasTree`) 上面有許多的裝飾品，包含鈴鐺（`Bell`），糖果（`Candy`），與禮物（`Gift`），請用 `Decorator` 樣式設計之。所有的聖誕樹都會支援 `sing()` 的方法：
    - `聖誕樹：I am a Chrismas tree`
    - `有鈴鐺的聖誕樹：I have a bell, I am a Chrismas tree`
    - `有糖果和鈴鐺的聖誕樹：I have a candy, I have a bell, I am a Chrismas tree`

依此類推。請寫出完整可以執行的程式。

### 17.ex03

可作輸出，`FilterWriter` 是一個 `Decorator` 的物件。設計以下的 `Filter`:	
- `LowerCaseFilter`:  每個英文字都改成小寫
- `UpperCaseFilter`：每個英文字都改成大寫
- `CommaFilter`: 遇到數字就加上千分號
- `CountFilter`: 在每行字後面加上單字的個數
	
```java=
Writer w = new BufferredWriter(new FileWriter("test.txt"));
Writer w2 = new LowerCaseFilter(w2);
w2.write("THIS is A Test 12309092"); //印出 this is a test 12309092
Writer w3 = new CommaFilter(w2);
w3.write("THIS is A Test 12309092"); //印出 this is a test 12,309,092
```	

### 17.ex04
泡咖啡了！我們有手工（`HandBlend`）、深度烘胚（`DarkRoast`）、低卡 `Decaf`、`Espresso` 等咖啡，而且每一種咖啡都可以加上 `Milk`, `Mocha`, `Soy`，當然每一個都是額外需要加費的。請用 Decorator 設計樣式設計之，注意 Coffee 是父類別，而我們需要 `cost()` 方法來回傳費用。畫出 UML 圖，寫出程式（請自己假設個別的價格）。

### 17.ex05
象棋系統中，可否應用 Decorator 設計樣式？試說明之。