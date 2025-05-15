###### tags: `OOSE`

# Ch18 一法萬策：Strategy


## 18.1 目的與動機

> 定義一群演算法，將每一個封裝成一個類別且使之可互換。使用 `Strategy` 讓演算法獨立於使用者。
>> Define a **family of algorithms**, encapsulate each one, and make them **interchangeable**. `Strategy` lets the algorithm vary independently from clients that use it.

### 18.1.1 動機

在很多種情況我們都會用到策略樣式。假設我們開發一個應用程式，其中會對某個陣列做排序。也許我們一開始會用氣泡排序法，且知道以後這個演算法可以改善，例如用 quick sort, selection sort。我們不希望抽換這些排序方法的時候會對其他程式造成影響，也就是說，我們應該符合 OCP 原則。我們該怎麼設計？

Solution: 使用strategy 設計樣式，將各種不同的排序抽象出來：

![](https://i.imgur.com/uunWiwO.png)
FIG: `Strategy` 

### 18.1.2 Sort

```java=
interface SortStrategy {
    public int[] sort(int [] d);
}

class SortArray {
    int[] d;
    private SortStrategy sortStrategy;
    public SortArray(SortStrategy s) {
        this.sortStrategy = s;
    }
    public int[] doSort() {
        return this.sortStrategy.sort(d);
    }
}

class QuickSort implements SortStrategy {
    public int[] sort(int[] d) {
        //do ....
        return ...
    }
}

class SelectionSort implements SortStrategy {
    public int[] sort(int[] d) {
        //do ....
        return ...
    }
}

//main program to test
class StrategyExample {
    public static void main(String[] args) {
        SortArray context;
        context = new SortArray(new QuickSort());
        int[] resultA = context.doSort();

        context = new SortArray(new SelectionSort());
        int[] resultB = context.doSort();
    }
}
```

[gugu- `Strategy`](https://refactoring.guru/design-patterns/strategy)

## 18.2 結構與方法

### 18.2.1 結構

![](https://i.imgur.com/nT6YLwh.png)
FIG: `Strategy` Structure


### 18.2.2 程式樣板


```java=
package strategy;

class Context {
	Strategy s;
	
	public Context(Strategy s) {
		this.s = s;
	}
	public void doIt() {
		System.out.println("Doing something");
		s.execute();
	}
}

class Strategy1 implements Strategy {
	public void execute() {
		System.out.println("Using strategy 1");
	}
}

class Strategy2 implements Strategy {
	public void execute() {
		System.out.println("Using strategy 2");
	}
}

interface Strategy{
	public void execute();
}

public class StrategyTemplate {

	public static void main(String[] args) {
		Strategy s1 = new Strategy1();
		Context context = new Context(s1);
		context.doIt();
	}
}
```

執行結果如下：

<img src="https://i.imgur.com/uMvFJWe.png" width="200">


#### 優點

- 消除大量的 if-else 等判斷句。過去我們可能用 if 來改變要使用的演算法。採用策略樣式，我們透過多型來達到此目的，避免過多的判斷句。
- 演算法的動態抽換。


#### 缺點

- 此設計會造成比較多的類別。過去用 if 造成一個很大的方法，用 `Strategy` 可以避免這個大方法，但類別就會多一些。
- 所有的演算法必須符合相同的介面。因為有一個抽象的策略類別被繼承，大家都要實作相同的介面。

## 18.3 範例

### 18.3.1 LayoutManager

Java 的 GUI 容器物件也是利用策略設計樣式來改變它的排版的。

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutDemo extends JFrame implements ActionListener {

    private JPanel contentPanel;
    private JComboBox<String> layoutSelector;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label;
    private JTextField textField;

    public LayoutDemo() {
        setTitle("Layout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化主要內容面板
        contentPanel = new JPanel();
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // 初始化元件
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Longer Button");
        label = new JLabel("A Label");
        textField = new JTextField("Text Field", 10);

        // 初始化下拉式選單
        String[] layoutOptions = {"FlowLayout", "BorderLayout", "GridLayout", "BoxLayout (X_AXIS)", "BoxLayout (Y_AXIS)"};
        layoutSelector = new JComboBox<>(layoutOptions);
        layoutSelector.addActionListener(this);
        getContentPane().add(layoutSelector, BorderLayout.NORTH);

        // 初始版面配置
        switchLayout("FlowLayout");

        pack();
        setLocationRelativeTo(null); // 視窗置中
        setVisible(true);
    }

    private void switchLayout(String layoutName) {
        contentPanel.removeAll(); // 移除所有現有元件
        contentPanel.setLayout(null); // 先設定為 null，以便後續設定新的 LayoutManager

        switch (layoutName) {
            case "FlowLayout":
                contentPanel.setLayout(new FlowLayout());
                break;
            case "BorderLayout":
                contentPanel.setLayout(new BorderLayout());
                break;
            case "GridLayout":
                contentPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 行 2 列，間距 5
                break;
            case "BoxLayout (X_AXIS)":
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
                break;
            case "BoxLayout (Y_AXIS)":
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                break;
        }

        // 重新將元件加入到面板中
        if (contentPanel.getLayout() instanceof BorderLayout) {
            contentPanel.add(button1, BorderLayout.NORTH);
            contentPanel.add(button2, BorderLayout.SOUTH);
            contentPanel.add(button3, BorderLayout.EAST);
            contentPanel.add(label, BorderLayout.WEST);
            contentPanel.add(textField, BorderLayout.CENTER);
        } else if (contentPanel.getLayout() instanceof GridLayout) {
            contentPanel.add(button1);
            contentPanel.add(button2);
            contentPanel.add(button3);
            contentPanel.add(label);
            contentPanel.add(textField); // GridLayout 會自動排列，多餘的會被忽略
        } else { // FlowLayout 和 BoxLayout
            contentPanel.add(button1);
            contentPanel.add(button2);
            contentPanel.add(button3);
            contentPanel.add(label);
            contentPanel.add(textField);
        }

        contentPanel.revalidate(); // 重新驗證版面配置
        contentPanel.repaint();    // 重新繪製介面
        pack(); // 重新調整視窗大小以適應內容
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == layoutSelector) {
            String selectedLayout = (String) layoutSelector.getSelectedItem();
            switchLayout(selectedLayout);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LayoutDemo::new);
    }
}
```

### 18.3.1 Validator/Verifier

驗證器。各種不同的輸入需要做不同的驗證，我們可以把驗證器獨立於輸入元件，這樣輸入元件就可以客製化的設計驗證器了。例如生日格式的驗證、電話格式的驗證等都需要特別的驗證方式。

```java=
myTextField.setInputVerifier(new MyInputVerifier());
```

我們自己擴充 [`InputVerifier`](https://docs.oracle.com/javase/7/docs/api/javax/swing/InputVerifier.html)：

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VerifierTest extends JFrame {
    JTextField tf1 = new JTextField("Type \"pass\" here");
    JTextField tf2 = new JTextField("TextField2");

    public VerifierTest() {
        setLayout(new BorderLayout());
        getContentPane().add(tf1, BorderLayout.NORTH);
        getContentPane().add(tf2, BorderLayout.SOUTH);

        tf1.setInputVerifier(new PassVerifier());

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(l);

        pack();
        setVisible(true);
    }

    class PassVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;
            return "pass".equals(tf.getText());
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            boolean isValid = verify(input);
            if (!isValid) {
                JOptionPane.showMessageDialog(null, "密碼必須是 'pass'", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
            return isValid;
        }
    }

    public static void main(String[] args) {
        new VerifierTest();
    }
} ```

## 18.4 討論

> `Strategy` 是換骨，`Decorator` 是換皮；為什麼？


## 18.CHK

1.  關於 `Strategy` 設計樣式是把策略：
    A) 延遲到子類別決定
    B) 委託給另一個物件
    C) 包裝成一個複合物件
    D) 限制只能產生一份演算法


2.  在 `Strategy` 中，若我們要擴充一個新的演算法：
    A) 新增一個 `Strategy` 介面
    B) 新增一個實踐 `Strategy` 介面的類別
    C) 在方法中新增一個 `Strategy` 參數
    D) 宣告一個 `static` 方法


3.  `Swing` 的排版設計採用了 `Strategy` 的設計，其中 `BorderLayout` 相對於 `Strategy` 樣式中的哪一個角色？
    A) `Context`
    B) `Abstract strategy`
    C) `Concrete strategy`
    D) `execute()`

4.  關於 `InputVerifier`, 以下何者錯誤：
    A) 採用了 `Strategy` 來提升檢查欄位的彈性，降低修改程式的範圍
    B) 透過新增 `InputVerifier` 的子類別來產生新的檢查器
    C) `TextField` 透過 `setInputVerifier()` 來設定不同的檢查器
    D) `TextField` 一旦設定了檢查器，就不可更換

---

參考答案：

1.  關於 `Strategy` 設計樣式是把策略：
    **B) 委託給另一個物件**
    說明：`Strategy` 模式的重點在於將演算法的實作委託給獨立的策略物件，讓 `Context` 物件可以彈性地切換和使用不同的策略。

2.  在 `Strategy` 中，若我們要擴充一個新的演算法：
    **B) 新增一個實踐 `Strategy` 介面的類別**
    說明：擴充新的演算法需要創建一個新的 `Concrete Strategy` 類別，該類別實作了 `Strategy` 介面定義的行為。

3.  `Swing` 的排版設計採用了 `Strategy` 的設計，其中 `BorderLayout` 相對於 `Strategy` 樣式中的哪一個角色？
    **A) Context**
    簡單解釋：`BorderLayout` 就像 `Strategy` 模式中的 `Context`，它持有並使用不同的排版策略（例如 `FlowLayout`、`GridLayout` 等）來管理元件的佈局。

4.  關於 `InputVerifier`, 以下何者錯誤：
    **D) `TextField` 一旦設定了檢查器，就不可更換**
    說明：`JTextField` 的 `setInputVerifier()` 方法可以讓你隨時設定或更換不同的 `InputVerifier` 物件，提供了動態切換驗證策略的能力。


## 18.EX

### 18.ex01 QuizApp
線上考試系統，選擇題的出題方式可以分為 (1) 按照原來項目順序出題 (2) 隨機把項目打亂出題 (3) 依據困難度排序。請問是否適合用 `Strategy` 設計？為什麼？該如何設計？

### 18.ex02 GradeBook
class GradeBook 需要排序，如何應用 `Strategy` 讓排序演算法靈活變更？

```java=
class GradeBoook {
   ?    
}

interface ? 

class ?
```

### 18.ex03 SSNVerifier
擴充 Java `InputVerifier` 設計一個台灣身分證的 `SSNVerifier`。並應用這個 `Verifier` 在一個簡單應用程式。

<!-- 
package org.example;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class SSNVerifier extends InputVerifier {

    private static final String TAIWAN_ID_REGEX = "^[A-Z][12]\\d{8}$";
    private static final int[] WEIGHT = {1, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final String LETTERS = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
    private static final int[] VALUES = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};

    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String id = textField.getText().trim().toUpperCase();

        if (!id.matches(TAIWAN_ID_REGEX)) {
            return false;
        }

        int letterValue = VALUES[LETTERS.indexOf(id.charAt(0))];
        int sum = (letterValue / 10) * WEIGHT[0] + (letterValue % 10) * WEIGHT[1];

        for (int i = 1; i < 10; i++) {
            sum += Character.getNumericValue(id.charAt(i)) * WEIGHT[i + 1];
        }

        return (sum % 10 == 0);
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean valid = verify(input);
        if (!valid) {
            JOptionPane.showMessageDialog(input, "台灣身分證字號格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
            ((JTextField) input).selectAll();
        }
        return valid;
    }

    public static void main(String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame("身分證驗證應用程式");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.FlowLayout());

        javax.swing.JLabel label = new javax.swing.JLabel("請輸入台灣身分證字號：");
        javax.swing.JTextField textField = new javax.swing.JTextField(10);
        textField.setInputVerifier(new SSNVerifier());

        javax.swing.JButton submitButton = new javax.swing.JButton("驗證");
        submitButton.addActionListener(e -> {
            if (textField.getInputVerifier().verify(textField)) {
                JOptionPane.showMessageDialog(frame, "身分證字號格式正確！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.add(label);
        frame.add(textField);
        frame.add(submitButton);

        frame.setSize(300, 150);
        frame.setVisible(true);
    }
}
 -->