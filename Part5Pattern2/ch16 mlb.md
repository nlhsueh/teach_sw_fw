好的！這裡是針對你的需求 —  

### 題目：用 Composite 設計模式實作 MLB 聯盟與球隊結構

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

### 參考解答

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


