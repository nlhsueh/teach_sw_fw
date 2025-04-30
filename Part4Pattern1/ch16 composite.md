###### tags: `OOSE`

# Ch17 隻手乾坤：Composite
   
## 17.1 目的與動機


> 複合設計樣式的目的是要單元物件與複合物件一視同仁, 以降低程式的複雜度。所謂的複合物件就是它可以包含其他的複合物件或單元物件。
>> *To **compose** objects into tree structures to represent part-whole hierarchies. Implementing the composite pattern lets clients treat individual objects and compositions uniformly*

容器，可以裝東西。也可以被裝。下面的圖形就是一個典型的複合物件，其中有顏色的圖示是一個基礎元件，而圓角矩型是一個容器，可以放圖示，也可以被放在一個個大的容器中。

<img src="https://i.imgur.com/9xObBAE.png" width="300">

FIG: 繪圖可視為一個 Composite 物件


**應用**

- 物件間有複合的關係。
- 我們不想區別單元物件與複合物件之間的差異，對他們有相同的呼叫方式。 

## 17.2 結構與方法

<img src="https://i.imgur.com/GEM8amW.png" width="400">

FIG: Composite 結構


## 17.3 程式樣板

Java 已經有很好的集合物件可以幫我處理元件的管理，所以 Composite 內有一個 ArrayList 的物件，執行新增元件、刪除元件、列舉出元件的工作，也就是說，委託給 ArrayList。

```java=
import java.util.ArrayList;
import java.util.Iterator;

public class CompositeTemplate {
    public static void main(String args[]) {
        //just for demo
        Composite c1 = new Composite("C1");
        c1.addComponent(new Leaf("J"));
        c1.addComponent(new Leaf("K"));
        c1.op();

        Composite c2 = new Composite("C2");
        c2.addComponent(c1);
        c2.addComponent(new Leaf("Q"));
        c2.op();
    }
}

// COMPONENT
abstract class Component {
  abstract void op(); //OPERATION
}

// COMPOSITE
class Composite extends Component {
  ArrayList<Component> list;
  String name;

  public Composite(String n) {
  	  this.name = n;
      list = new ArrayList<Component>();
  }

  void addComponent(Component c) {
      list.add(c);
  }

  // OPERATION in COMPOSITE
  void op() {
    Iterator<Component> iterator = list.listIterator();
    while (iterator.hasNext()) {
       Component c = iterator.next();
       c.op();
    }
  }  
  //  System.out.println(name);
}

// LEAF
class Leaf extends Component {
  String name; 	

  public Leaf(String a) {
  	name = a;
  }

  // OPERATION in LEAF
  void op() {
 	 System.out.println(name); 
  }
}
```

> 應用 Composite 畫出圖 \ref{fig:draw} 的 UML 設計

**優點**

- 容易新增新的元件。
- 讓客戶端的物件設計更為容易，因為不用區分單元物件與複合物件的差異。

## 17.4 範例

### 17.4.1 GUI 元件範例

#### 方案1：通通不一樣

```java=
public class Window {
   Button[] buttons;
   Menu[] menus;
   TextArea[] textAreas;
   WidgetContainer[] containers;

   public void update() {
      if (buttons != null) 
         for (int k = 0; k < buttons.length; k++) 
             buttons[k].draw();
      if (menus != null) 
          for (int k = 0; k < menus.length; k++)
             menus[k].refresh();
      if (containers != null)
          for (int k = 0; k < containers.length; k++ )
        	 containers[k].updateWidgets();
   }
}   
```

在這個例子中我們看到視窗物件把各種不同的介面元件視為不相同的物件，每一個呼叫的方式都不一樣。如此一來，這個程式的複雜度變高了，整個程式的耦合力也相對的高。

#### 方案 2：widget 與 container

方案 1 的問題：違反 OCP 原則：如果我們想要加一個新種類的 widget, 我們必須要修改 update()。違反開閉原則，當我們要新增新的元件、我們必須修改原來的程式碼。為了解決這個問題，程式碼修改如下：

```java=
public class Window {
   Widget[] widgets;
   WidgetContainer[] containers;

   public void update() {
      if (widgets != null)
         for (int k = 0; k < widgets.length; k++) 
             widgets[k].update();
      if (containers != null)
          for (int k = 0; k < containers.length; k++ )
              containers[k].updateWidgets()       
   }
}
```

在第二個解決方案中，我們把所有單元物件的介面統一，如此一來，程式變的更簡單了。但是單元物件與複合物件仍是不同的介面，處理上還是需要分開來。我們可以用 Composite 來改善。

#### 方案 3: Composite Pattern!

```java=
public class Window {
   Component[] components;
      public void update() {
         if (components != null)
            for (int k = 0; k < components.length; k++)
               components[k].update();
    }
}
```

在第三個方案中，所有的物件都視為 Component 了，統一的介面方法都是 update(), 程式是不是變得更簡潔了？

### 17.4.2 通透與安全
實作上有兩種選擇：通透與安全。

**通透（Transparent)**：Component 具備 operation 以外，還具備了 add(Component), remove(Component) 等複合物件的方法。如此一來，Component 和 Composite 是沒有差異的，所以稱為 Transparent。但如此一來，Leaf 物件繼承了 add() 的功能顯得奇怪，通常我們會讓它沒有作用：

```java=
   class Leaf extends Component {
      public void add() {} // 沒有實作
   }
```

但這是危險的，因為 Client 可能誤以為你有加入 Component 物件。

**安全（Safe）**：Component 僅具備 operation 方法。Client 要新增元件時，必須先判斷它是不是一個複合物件：

```java=
if (c instanceOf Composite) {
    ((Composite)c).add(...)
```		

### 17.4.3 `Container` 與 `Component`

在 Java 的圖形使用者介面 (GUI) 程式設計中，`java.awt.Container` 和 `java.awt.Component` 體現了 Composite 模式的思想。

* **`java.awt.Component` (Component)：** 代表 GUI 中的基本視覺元素，例如按鈕 (`JButton`)、標籤 (`JLabel`)、文字欄位 (`JTextField`) 等。這些是 **Leaf 節點**。它們負責自身的繪製和事件處理。
* **`java.awt.Container` (Composite)：** 是一種特殊的 `Component`，它可以容納其他的 `Component`。例如，視窗 (`JFrame`)、面板 (`JPanel`) 等。`Container` 本身也是一個 `Component`，因此可以形成階層結構。`Container` 提供了管理子 `Component` 的方法，例如 `add(Component c)`、`remove(Component c)` 和 `getComponents()`。

**運作方式：**

當你在一個 `Container` 上執行某些操作（例如繪製、調整大小、事件處理），這些操作通常會**遞迴地**應用到它所包含的所有子 `Component` 上。例如，當一個 `JFrame` 需要重新繪製時，它會要求其內部所有的 `JPanel` 和其他子 `Component` 進行繪製。

**範例：**

```java
import javax.swing.*;
import java.awt.*;

public class ContainerCompositeExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Container Composite Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton button1 = new JButton("Button 1");
        JLabel label1 = new JLabel("Label 1");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(new JButton("Button A"));
        panel1.add(new JTextField(10));

        frame.add(button1);
        frame.add(label1);
        frame.add(panel1);

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
```

在這個例子中，`JFrame` 是一個 `Container` (Composite)，它包含了 `JButton` 和 `JLabel` (Leaf)，以及另一個 `JPanel` (Composite)。`JPanel` 又包含了 `JButton` 和 `JTextField` (Leaf)。當 `JFrame` 需要繪製其內容時，它會要求其所有的子 `Component`（包括 `JPanel`）進行繪製，而 `JPanel` 又會進一步要求其子 `Component` 進行繪製。

### 17.4.4 `JTree` 與 `TreeNode`

在 Swing 的 `JTree` 組件中，樹狀結構的建立和管理正是 Composite 設計模式的典型應用。

1.  **`TreeNode` 介面 (Component):**
    * `TreeNode` 介面扮演了 Composite 模式中 **Component** 的角色。
    * 它定義了樹中所有節點（無論是葉節點還是包含其他節點的容器節點）都需要實現的通用介面。
    * `TreeNode` 介面聲明了以下關鍵方法，這些方法對於遍歷和管理樹結構至關重要：
        * `getChildAt(int childIndex)`: 取得指定索引的子節點。
        * `getChildCount()`: 取得子節點的數量。
        * `getParent()`: 取得父節點。
        * `getIndex(TreeNode node)`: 取得指定子節點在其父節點中的索引。
        * `getAllowsChildren()`: 判斷該節點是否允許擁有子節點。
        * `isLeaf()`: 判斷該節點是否為葉節點（沒有子節點）。
        * `children()`: 返回一個列舉 (Enumeration) 或迭代器 (Iterator)，用於遍歷子節點。

2.  **`DefaultMutableTreeNode` 類別 (Leaf 和 Composite):**
    * `DefaultMutableTreeNode` 類別扮演了 Composite 模式中 **Leaf** 和 **Composite** 的角色。這是 Composite 模式的一個常見實現方式，相同的類別既可以表示葉節點，也可以表示包含子節點的容器節點。
    * **作為 Composite:** `DefaultMutableTreeNode` 類別實作了 `TreeNode` 介面，並且提供了管理子節點的功能：
        * `add(MutableTreeNode newChild)`: 新增一個子節點。
        * `remove(MutableTreeNode aChild)`: 移除一個子節點。
        * `remove(int index)`: 移除指定索引的子節點。
        * `removeAllChildren()`: 移除所有子節點。
        * 它內部維護了一個儲存子節點的資料結構（通常是一個 `Vector` 或 `ArrayList`）。
    * **作為 Leaf:** 當一個 `DefaultMutableTreeNode` 物件沒有子節點時，它就扮演了葉節點的角色。`getChildCount()` 會返回 0，`isLeaf()` 會返回 `true`。

3.  **`JTree` 類別 (Client 和 Context):**
    * `JTree` 類別是使用樹狀結構來顯示資料的 Swing 組件，它扮演了 Composite 模式中的 **Client** 和 **Context** 的角色。
    * `JTree` 接收一個 `TreeModel` 物件作為其資料模型。`TreeModel` 通常基於 `TreeNode` 的實作（例如 `DefaultTreeModel`，它使用 `DefaultMutableTreeNode`）。
    * `JTree` 透過 `TreeModel` 和 `TreeNode` 介面來遍歷和顯示樹狀結構。`JTree` 的繪製和事件處理邏輯可以一致地處理葉節點和容器節點，而不需要關心它們的具體類型。例如，`JTree` 可以對一個容器節點顯示展開/收縮的圖示，並在展開時顯示其子節點，而對葉節點則直接顯示其標籤。

**Composite 模式的優勢在 `JTree` 中的體現：**

* **一致性：** 客戶端程式碼（`JTree` 的內部邏輯）可以一致地對待單個節點 (`DefaultMutableTreeNode` 作為 Leaf) 和包含子節點的節點 (`DefaultMutableTreeNode` 作為 Composite)。`JTree` 無需針對不同類型的節點編寫不同的處理邏輯來獲取子節點、判斷是否為葉節點等，因為 `TreeNode` 介面提供了統一的操作介面。
* **組合性：** 你可以創建複雜的樹狀結構，其中容器節點可以包含其他的容器節點或葉節點，形成層次化的結構。`DefaultMutableTreeNode` 的設計允許節點以遞迴的方式包含子節點。
* **簡化客戶端程式碼：** `JTree` 的實作被簡化了，因為它只需要與 `TreeNode` 介面互動，而不需要知道每個節點的具體類別和內部結構。新增新的節點類型（只要實作了 `TreeNode` 介面）就可以被 `JTree` 無縫地處理。

**範例：**

```java
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeCompositeExample {
    public static void main(String[] args) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode parent1 = new DefaultMutableTreeNode("Parent 1");
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Child 1");
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Child 2");
        DefaultMutableTreeNode parent2 = new DefaultMutableTreeNode("Parent 2");
        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode("Child 3");

        root.add(parent1);
        root.add(parent2);
        parent1.add(child1);
        parent1.add(child2);
        parent2.add(child3);

        JTree tree = new JTree(root);

        JFrame frame = new JFrame("JTree Composite Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(tree));
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
```

在這個例子中，`DefaultMutableTreeNode` 實作了 `TreeNode` 介面。`root`、`parent1` 和 `parent2` 是 Composite 節點，因為它們包含其他的 `TreeNode`。`child1`、`child2` 和 `child3` 是 Leaf 節點，因為在這個簡單的例子中它們沒有子節點。`JTree` 可以統一地處理這些節點，例如在展開 `parent1` 時，它會顯示其子節點 `child1` 和 `child2`。

## 17.5 Check

1. 在 Composite 設計樣式中，不包含哪一個角色
	- component 
	- leaf 
	- composite 
	- decorator
2. 在 Composite 設計樣式中，Composite 角色（複選）
	- 是 Component 的子類別
	- 可以包含許多 Component
	- 具備 addComponent() 的功能
	- 收到 client 請求後，通常會將請求轉送給他所包含的物件
3. 若 Component 有 addComponent() 等元件管理等功能，此方法稱為 
	- Transparent 
	- Safe	
4. 關於 Composite, 何者對？
	- 對於 client, 複合物件與單元物件具備共通性，因此偶合力低
	- 不論是複合物件與單元物件都可以透過 addComponent() 加入元件 
	- 複合物件可以包含單元物件，不可包含複合物件
	- 其觀念與遞迴概念相仿
5. 若 Component 內的方法為 update()，寫出 Composite 內的 update()

```java=
class Composite ?  {
    ? 
}
```

**參考解答**

**1. 在 Composite 設計樣式中，不包含哪一個角色**

答案：**- decorator**

Decorator 設計模式是另一種結構型模式，它動態地給一個物件添加額外的職責。它與 Composite 模式的概念和目的不同。

**2. 在 Composite 設計樣式中，Composite 角色（複選）**

答案：
* **- 是 Component 的子類別**
* **- 可以包含許多 Component**
* **- 具備 addComponent() 的功能**
* **- 收到 client 請求後，通常會將請求轉送給他所包含的物件**

**說明：**

* Composite 繼承自 Component，因此可以被視為 Component 的一種。
* Composite 的核心功能是管理其子 Component，因此可以包含多個 Component 實例。
* 為了管理子 Component，Composite 通常會提供 `addComponent()`（以及 `removeComponent()`、`getChild()` 等）方法。
* Composite 收到客戶端的請求時，通常會將這個請求委派給它所包含的子 Component 進行處理，或者在自身處理一部分後再委派給子 Component。

**3. 若 Component 有 addComponent() 等元件管理等功能，此方法稱為**

答案：**- Transparent**

**說明：**

* **Transparent Interface:** 指的是 Component 介面中定義了所有管理子節點的方法（例如 `addComponent()`、`removeComponent()`、`getChild()`）。這樣做的好處是，無論是 Leaf 還是 Composite 物件，都具有相同的介面，用戶端無需區分對待。但缺點是 Leaf 節點可能不需要這些管理子節點的方法，導致介面臃腫。
* **Safe Interface:** 指的是只有 Composite 介面中定義了管理子節點的方法，而 Leaf 介面中沒有。這樣做的好處是介面更清晰，Leaf 物件不會有不適用的方法。但缺點是用戶端在操作時需要判斷物件是 Leaf 還是 Composite，增加了複雜性。

**4. 關於 Composite, 何者對？**

答案：**- 對於 client, 複合物件與單元物件具備共通性，因此偶合力低**
答案：**- 其觀念與遞迴概念相仿**

**說明：**

* **對於 client, 複合物件與單元物件具備共通性，因此偶合力低：** 這是 Composite 模式的核心優點。由於 Leaf 和 Composite 都實現了 Component 介面，用戶端可以以一致的方式操作它們，無需關心具體類型，從而降低了耦合度。
* **不論是複合物件與單元物件都可以透過 addComponent() 加入元件：** 這是 Transparent Interface 的特性，並非 Composite 模式的必要條件。在 Safe Interface 的情況下，只有 Composite 物件才有 `addComponent()` 等方法。
* **複合物件可以包含單元物件，不可包含複合物件：** 這是錯誤的。Composite 物件可以遞迴地包含其他的 Composite 物件，形成樹狀結構。
* **其觀念與遞迴概念相仿：** Composite 模式在實現某些操作時（例如遍歷整個結構、執行某個操作），常常會使用遞迴的方式處理 Composite 節點及其子節點。

**5. 若 Component 內的方法為 update()，寫出 Composite 內的 update()**

```java=
import java.util.ArrayList;
import java.util.List;

class Composite extends Component {
    private List<Component> children = new ArrayList<>();

    public void addComponent(Component component) {
        children.add(component);
    }

    public void removeComponent(Component component) {
        children.remove(component);
    }

    public List<Component> getChildren() {
        return children;
    }

    @Override
    public void update() {
        System.out.println("Composite: Updating its children...");
        for (Component child : children) {
            child.update(); // 遞迴地呼叫子 Component 的 update() 方法
        }
        System.out.println("Composite: Finished updating its children.");
        // Composite 本身可能也會有一些更新邏輯
    }
}

// 假設 Component 是一個介面或抽象類別
interface Component {
    void update();
}

// 簡單的 Leaf 節點範例
class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("Leaf " + name + ": Updating...");
        // Leaf 節點的具體更新邏輯
        System.out.println("Leaf " + name + ": Finished updating.");
    }
}
```

**說明：**

在 `Composite` 類別中，`update()` 方法會遍歷其包含的所有子 `Component`，並對每個子 `Component` 呼叫其自身的 `update()` 方法。這樣就實現了將更新操作遞迴地應用到整個 Composite 結構中。Composite 本身在遍歷子節點前後也可以包含一些自身的更新邏輯。


## 17.6 Exercise

### 17.6.1 目錄與檔案
目錄（Folder）與檔案（File）可以用 Composite 來設計，每一個 File 檔案有檔案大小（size），每一個 Folder 可以加上很多 Folder 或是 File，其檔案大小為其所包含的檔案大小之總和。FileManager 可以對 AbstractFile 詢問其檔案總大小（如下）。請撰寫程式實驗之。

```java=
class FileManager {
   public int getFileSize(AbstractFile f) {
      ...
   }
}
class File ? {
   private int size;
   ?
}
interface AbstractFile {
   ?
}
class Folder ? {
   ?
}               
```

### 17.6.2 MLB 棒球大聯盟

用 Composite 設計模式實作 MLB 聯盟與球隊結構。

請設計一套類別架構，使用 **Composite 設計樣式**，能表示 **聯盟 (League)、分區 (Division)、球隊 (Team)** 三層結構，並使用 `JTree` 顯示出 MLB 完整資料。  

1. 設計一個共同的抽象類別或介面，例如 `MLBComponent`，並定義基本操作如 `add()`、`remove()`、`getChild()`、`getName()`。
2. `League`、`Division` 必須能夠包含其他 `MLBComponent`（Composite 角色）。其結構如圖一。
3. `Team` 只代表個別球隊（Leaf 角色），不能再新增子節點。
4. 使用 `JTree` 把整個 MLB 結構視覺化。樣子如下圖。
5. 延伸題：點選球隊節點時，能顯示所屬聯盟與分區資訊。

圖一：MLB 的樹狀結構
```
MLB
 ├── American League
 │    ├── East
 │    │    ├── Baltimore Orioles
 │    │    ├── Boston Red Sox
 │    │    └── ...
 │    └── Central
 │         └── ...
 └── National League
      └── East
           └── ...
```

圖二：使用 JTree 來做視窗化呈現：
![image](https://hackmd.io/_uploads/HkqUR-Tkgx.png)

---

提示：

Composite 典型架構是：

```java
abstract class MLBComponent {
    String name;
    public MLBComponent(String name) { this.name = name; }
    public String getName() { return name; }
    public void add(MLBComponent component) { throw new UnsupportedOperationException(); }
    public void remove(MLBComponent component) { throw new UnsupportedOperationException(); }
    public MLBComponent getChild(int i) { throw new UnsupportedOperationException(); }
}

class League extends MLBComponent { ... }
class Division extends MLBComponent { ... }
class Team extends MLBComponent { ... }
```

最後可以把 `MLBComponent` 結構轉成 `DefaultMutableTreeNode` 加到 `JTree` 裡。

#### Hint

1. 先定義抽象類別 `MLBComponent`

```java
// 相當於 Composite 的 Component
public abstract class MLBComponent {
    protected String name;

    public MLBComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(MLBComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(MLBComponent component) {
        throw new UnsupportedOperationException();
    }

    public MLBComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }
}
```

---

2. 定義 `League` 和 `Division` (Composite)

```java
import java.util.ArrayList;
import java.util.List;

// 相當於 Composite 的 Composite
public class League extends MLBComponent {
    private List<MLBComponent> children = new ArrayList<>();

    public League(String name) {
        super(name);
    }

    @Override
    public void add(MLBComponent component) {
        children.add(component);
    }

    @Override
    public MLBComponent getChild(int i) {
        return children.get(i);
    }

    public List<MLBComponent> getChildren() {
        return children;
    }
}
```

`Division` 跟 `League` 幾乎一樣，可以直接繼承同樣邏輯：

```java
// 也是相當於 Composite 的 Component
public class Division extends League {
    public Division(String name) {
        super(name);
    }
}
```

---

3. 定義 `Team`

```java
// 相當於 Composite 的 Leaf
public class Team extends MLBComponent {
    public Team(String name) {
        super(name);
    }
}
```

---

4. 把 MLB 組成樹結構並顯示到 `JTree`

```java
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MLBTreeExample extends JFrame {
    public MLBTreeExample() {
        setTitle("MLB Teams");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MLBComponent mlb = createMLBStructure();
        DefaultMutableTreeNode root = buildTree(mlb);
        JTree tree = new JTree(root);

        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    // 建立 MLB 資料的結構，這部分和 GUI 沒有關係，和 Composite 設計有關
    private MLBComponent createMLBStructure() {
        League mlb = new League("MLB");

        League americanLeague = new League("American League");
        Division alEast = new Division("East");
        alEast.add(new Team("Baltimore Orioles"));
        alEast.add(new Team("Boston Red Sox"));
        alEast.add(new Team("New York Yankees"));
        alEast.add(new Team("Tampa Bay Rays"));
        alEast.add(new Team("Toronto Blue Jays"));

        Division alCentral = new Division("Central");
        alCentral.add(new Team("Chicago White Sox"));
        alCentral.add(new Team("Cleveland Guardians"));
        alCentral.add(new Team("Detroit Tigers"));
        alCentral.add(new Team("Kansas City Royals"));
        alCentral.add(new Team("Minnesota Twins"));

        Division alWest = new Division("West");
        alWest.add(new Team("Houston Astros"));
        alWest.add(new Team("Los Angeles Angels"));
        alWest.add(new Team("Oakland Athletics"));
        alWest.add(new Team("Seattle Mariners"));
        alWest.add(new Team("Texas Rangers"));

        // 把三個分區加到美國聯盟
        // 請完成此部分程式：
        // ...

        League nationalLeague = new League("National League");
        Division nlEast = new Division("East");
        nlEast.add(new Team("Atlanta Braves"));
        nlEast.add(new Team("Miami Marlins"));
        nlEast.add(new Team("New York Mets"));
        nlEast.add(new Team("Philadelphia Phillies"));
        nlEast.add(new Team("Washington Nationals"));

        Division nlCentral = new Division("Central");
        nlCentral.add(new Team("Chicago Cubs"));
        nlCentral.add(new Team("Cincinnati Reds"));
        nlCentral.add(new Team("Milwaukee Brewers"));
        nlCentral.add(new Team("Pittsburgh Pirates"));
        nlCentral.add(new Team("St. Louis Cardinals"));

        Division nlWest = new Division("West");
        nlWest.add(new Team("Arizona Diamondbacks"));
        nlWest.add(new Team("Colorado Rockies"));
        nlWest.add(new Team("Los Angeles Dodgers"));
        nlWest.add(new Team("San Diego Padres"));
        nlWest.add(new Team("San Francisco Giants"));

        // 把三個分區加到國家聯盟
        // 請完成此部分程式：
        // ...


        // 把美國聯盟和國家聯盟都加入大聯盟
        // 請完成此部分程式：
        // ...

        return mlb;
    }

    // 跑遞迴把每一個節點用一個 DefaultMutableTreeNode 來對應
    // DefaultMutableTreeNode 如果有 child 就是 Composite, 沒有就是 Leaf
    private DefaultMutableTreeNode buildTree(MLBComponent component) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(component.getName());

        if (component instanceof League) {
            League league = (League) component;
            for (MLBComponent child : league.getChildren()) {
                node.add(buildTree(child));
            }
        }
        return node;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MLBTreeExample().setVisible(true);
        });
    }
}
```

