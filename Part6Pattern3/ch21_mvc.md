###### tags: `OOSE`
@nlhsueh 

# Ch21 三足鼎立：MVC

## 21.1 目的與動機


> 在系統架構中，將資料模組(Model)、呈現模組(View)、控制模組(Controller)三者分離，藉此降低系統內的功能邏輯和資料的耦合，使得架構上分工明確、促使開發人員專責分工，進而提昇系統的擴充性。

> *Model–view–controller (MVC) is a software architectural pattern for implementing user interfaces. It divides a given software application into three interconnected parts, so as to separate internal representations of information from the ways that information is presented to or accepted from the user*

Model 與 View 的關係，和 Observer 設計樣式中 Subject 與 Observer 很類似，重點都是要把企業邏輯（或是資料主體）與呈現分離，並且當資料改變時，能夠即時的更新呈現。

> 問題是：誰去改變主體的狀態？

可能是使用者透過一個視窗介面輸入值變動後端的資料; 可能是滑鼠的移動觸發資料的變動; 可能是一個網路爬蟲在網路獵取資料經過計算後進行變動。像這樣的模組，它提供一個可以輸入的介面、過濾資料、轉換資料成可以變更資料主體的形式，在 MVC 架構中就是 Control 模組。你可以把它翻譯成控制器，控制所有對於資料主體進行變更的行為。

> Controller 修改 Model, Model 通知 View

## 21.2 結構

- 資料模組 (Model)：負責封裝和邏輯相關資料，以及處理資料方法，屬於業務流程處理。應用於模型的程式只需寫一次就可以被多個呈現模組重用，減少了程式碼的重複性。
- 呈現模組 (View)：負責資料的呈現。在股票系統中，資料呈現於網頁、手機、電視都是不同的呈現模組。
- 控制模組 (Controller)：定義使用者介面如何回應用戶的輸入事件，控制模組負責提供改變資料模組中的資料。在股票系統中，經由不同股票交易所，網站交易等輸入資料的動作。

![](https://hackmd.io/_uploads/BkD6F1H4h.png)

FIG: MVC

MVC模式的好處：

- 藉由Controller 協調，View不會直接對Model作出任何變更，View只負責呈現邏輯，即根據Model資料呈現結果，相關操作對應的企業邏輯（Business logic）不在View中。
- Controller 負責企業邏輯中請求的收集與驗證，更複雜的邏輯轉交Model處理，這使得Controller 維持簡單，Model 也具有較高的重用性。Controller也負責調配對應的View，所以Model不會與View耦合。
- Model不會知道或決定View實際呈現方式，因此View可以獨立於Model，自由變化。
- Model狀態有變化時，曾向Model註冊過的多個View，都可獲得通知而得到同步更新效果。

在這樣的定義下，MVC 與 Observer 有很大的關係，MVC 中 Model 和 View 的關係，就相當於 Observer 樣式中，Subject 和 Observer 的關係。

### 程式樣板

```java=
Model m = new Model();
View1 v1 = new View1();
View2 v2 = new View2();

m.addView(v1);
m.addView(v2);

Controller c = new Controller(m);
```

也有可能是這樣

```java=
Model m = new Model();
View1 v1 = new View1(m);
View2 v2 = new View2(m);

Controller c = new Controller();
c.setM(m);
```

## 21.3 範例

我們先從這個簡單的介面程式來看看什麼是 MVC。

### 方案1：無模組化

```java=
public class CounterGui extends JFrame {
  //counter 是 MODEL
  private int counter = 0;

  //tf 是 VIEW
  private TextField tf = new TextField(10);

  public CounterGui(String title) {
    super(title);
    Panel tfPanel = new Panel();
    tf.setText("0");
    tfPanel.add(tf);
    add("North", tfPanel);
    Panel buttonPanel = new Panel();

    //CONTROLLER   
    Button incButton = new Button("Increment");
    incButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            counter++;
            tf.setText(counter + "");
        }
      }
    );
    buttonPanel.add(incButton);

    Button decButton = new Button("Decrement"); 
    decButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          counter--;
          tf.setText(counter + "");
        }
      }
    );

    buttonPanel.add(decButton);

    add("South", buttonPanel);
  }

  public static void main(String[] argv) {
    CounterGui cg = new CounterGui("CounterGui");
    cg.setSize(300, 100);
    cg.setVisible(true);
  }
}
```

在這個例子中，Model (counter)、View (tf)、Controller (incButton) 都放在同一個類別內，並沒有分離。以下的作法，我們將 View 與 Model 分離。

### 方案2：View 與 Model 分離

首先我們把 Counter 宣告成一個類別，它扮演 Model 的角色。Model 有自己要存儲的資料，和存取的介面。Model 獨立以後，很可能有多的 View 會相依於這個 Model, 如果沒有處理好，就會出現資料不一致的狀況。


CODE: No Observer

```java=
package mvc.noobserver;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;

/* MODEL */
class Counter {
	int counter = 0;

	public void incCounter() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}
}

/* VIEW */
class CounterView extends JFrame  {
	static int id = 0; //just for setting location
	private TextField tf = new TextField(10);

	public CounterView(String title) {
		super(title);
		tf.setText("0");
		add(tf);
		this.setSize(200, 100);
		this.setLocation(100 + (id++) * 200, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void update(int v) {
		tf.setText(String.valueOf(v));
	}
}

/* CONTROLLER */
class CounterController extends JFrame implements ActionListener {
	static int id = 0; //just for setting location
	private JButton inc = new JButton("INC");
	Counter c;
	CounterView v;

	public CounterController(Counter c, CounterView v) {
		this.c = c;
		inc.addActionListener(this);
		this.v = v;
		add(inc);
		this.setTitle("Controller " + id);
		this.setSize(200, 100);
		this.setLocation(100 + (id++) * 200, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent arg0) {
		c.incCounter();
		v.update(c.getCounter());
	}
}

public class NoObserverDemo {

	public static void main(String[] args) {
		Counter c = new Counter();

		CounterView view1 = new CounterView("View0");
		view1.setVisible(true);
		CounterView view2 = new CounterView("View1");
		view2.setVisible(true);

		CounterController controller1 = new CounterController(c,view1);
		controller1.setVisible(true);
		CounterController controller2 = new CounterController(c,view2);
		controller2.setVisible(true);
	}
}
```


注意上述的做法「並不是由 MODEL 去通知 VIEW 狀態的改變」，而是由 Control 直接去改變，這會產生不一致的情況。當我們在 cv1 上一直增加 counter 的值時，cv2 的介面並不會反應。反之亦然。這個設計，雖然已經分離了 MVC，但其架構沒有使用 Observer 樣式，造成了一些問題。

![](https://hackmd.io/_uploads/SJBSqJHEh.png)

FIG: 不一致的 View


我們可以應用 Observer 來改善這個問題：

### 方案3：使用 Observer


MVCByObserver.java
```java=
package mvc;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;

/* MODEL */
class Counter extends Observable {
	int counter = 0;

	public void incCounter() {
		counter++;
		setChanged();
		notifyObservers(new Integer(counter));
	}

	public int getCounter() {
		return counter;
	}
}

/* VIEW */
class CounterView extends JFrame implements Observer {
	static int id = 0; //just for setting location
	private TextField tf = new TextField(10);

	public CounterView(String title) {
		super(title);
		tf.setText("0");
		add(tf);
		this.setSize(200, 100);
		this.setLocation(100 + (id++) * 200, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void update(Observable arg0, Object arg1) {
		int c = ((Integer) arg1).intValue();
		tf.setText(String.valueOf(c));
	}
}

/* CONTROLLER */
class CounterController extends JFrame implements ActionListener {
	static int id = 0; //just for setting location
	private JButton inc = new JButton("INC");
	Counter c;

	public CounterController(Counter c) {
		this.c = c;
		inc.addActionListener(this);

		add(inc);
		this.setTitle("Controller " + id);
		this.setSize(200, 100);
		this.setLocation(100 + (id++) * 200, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent arg0) {
		c.incCounter();
	}
}

public class MVCByObserver {
	public static void main(String[] args) {
		Counter c = new Counter();
		CounterView view1 = new CounterView("View0");
		view1.setVisible(true);
		CounterView view2 = new CounterView("View1");
		view2.setVisible(true);
		c.addObserver(view1); //add view
		c.addObserver(view2); //add view
		CounterController controller1 = new CounterController(c);
		controller1.setVisible(true);
		CounterController controller2 = new CounterController(c);
		controller2.setVisible(true);
	}
}
```

![](https://hackmd.io/_uploads/B1Tj9kSV2.png)

FIG: 應用 Observer 的 MVC 範例



## 21.4 Web MVC

雖然我們看了應用 Observer 的 MVC 架構，但現實框架並不是全部的 MVC 都採用了 Observer 的架構。實際上MVC模式本身並沒有明確定義，因此許多設計僅採納 MVC 最大特徵「將系統畫分為Model、View和Controller三個部份」，並將三個部份的互動流程因實際應用場合進行調整，其中最為人熟悉的，就是 Web 應用程式經常採用的 Model 2 \cite{JSPModel2}，又稱Web MVC，或由於過於流行所以大家就直接稱 MVC了。

Web 在先天上很自然的會被畫分為 Model、View和Controller三個部份，View通常是客戶端的瀏覽器依所取得的 HTML 所繪製的介面，Controller位於Web伺服器用來控制用戶所傳的命令；與資料庫互動的則是Model。

然而，對於基於 HTTP 的 Web 應用程式來說，由於 HTTP 基於請求/回應（Request/Response）模型，沒有請求就不會有回應，因此無法實現傳統MVC中Model主動通知View的流程，如果View想根據Model最新狀態來呈現畫面，基本作法之一，是透過持續性地主動詢問（Poll）伺服端Model來達成。

![](https://hackmd.io/_uploads/S1dpc1BNn.png)
**FIG: JSP Model 1 and Model 2**

在 JSP 的規格中，架構分為 Model 1 與 Model 2。其中 Model 2 即是對 Model 進行改良，將 Controller 獨立出來以應付較複雜的系統設計。

### 21.4.1 JSP Model 1

請求發給 JSP 網頁動態元件，由它處理請求參數收集、驗證、企業處理以及畫面回應，有些企業邏輯亦可能會封裝成Model（例如JavaBean），然而 JSP 元件亦要處理請求參數收集、驗證與畫面回應，程式碼與畫面設計勢必形成程式碼的混亂，也因而 Model 1通常應用在規模較小、任務簡單的Web應用程式。

Model 1 的流程。整個過程可以看到 JSP 元件十分的忙碌。
- Web 客戶端向 JSP 頁面發出 request 請求；
- 在 JSP 頁面取得請求所需的頁面參數；
- JSP 呼叫 JavaBean 進行業務邏輯運算後取得結果；
- 取得結果後 JSP 進行頁面的渲染（rendering）；
- JSP 向 web 客戶端返回處理結果。

### 21.4.2 JSP Model 2

將架構分為 MVC 三大模組。(1) **Controller** 的職責是接受請求、驗證請求、判斷要轉發請求給哪個模型、判斷要轉發請求給哪個畫面。(2) **Model** 的職責是 ：保存應用程式狀態、執行應用程式企業邏輯。(3) **View** 的職責是：提取模型狀態、執行呈現邏輯、組織回應畫面。

Model 2 的流程

- 瀏覽器送出請求，由扮演 Controller 的 Servlet 程式接收。
- Servlet 依據請求產生適當的 Model 物件，回傳運算結果。
- 控制權由 Servlet forward 給 JSP 呈現模組。
- JSP 程式向 Model 請求值，透過 JSP（與 HTML）語法作適當呈現。
- 將畫面回應 給 client 瀏覽器。


Model 2 中通常根據 URL樣式（urlPatterns）決定哪個 Controller 要接收 View 發送的請求參數。以下 HelloServlet.java 是程式的 Controller：

```java=
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// CONTROLLER
@WebServlet(name="Hello", urlPatterns={"/hello.do"})
public class HelloServlet extends HttpServlet {
    private Hello hello = new Hello(); // create MODEL

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("user");
        String message = hello.doHello(name);
        req.setAttribute("message", message);
        req.getRequestDispatcher("hello.jsp").forward(req, resp); // forward to VIEW
    }
}
```

上述程式可以看到 HelloServlet 扮演 Controller 的角色，第 10 行建立一個 Model 物件 Hello, 16 行向此物件請求執行特定的企業邏輯（doHelp()），執行後的結果封裝在 req 中，透過 forward 傳到介面程式 hello.jsp。

Hello.java 是資料的一部分，以下我們用一個 HashMap 模擬一個資料庫。

```java=
import java.util.*;

// MODEL
public class Hello {
    private Map<String, String> messages;
    
    public String doHello(String user) {
        String message = messages.get(user);
        return message + ", " + user + "!";
    }
    
    public Hello() {
        messages = new HashMap<String, String>();
        messages.put("Nick", "Hello");
        messages.put("Mary", "Welcome");
        messages.put("John", "Hi");
    }
}
```

依上，如果 user 請求參數是 Nick 就會取得 ``Hello"字串，如果是 Mary，就會取得 ``Welcome"字串， 在取得訊息之後，先前擔任控制器的 Servlet 會轉發給畫面。Hello.jsp: 

```java= %[language="html"]
// VIEW
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>\${param.user}</title>
  </head>
  <body>
      <h1>\${message}</h1>
  </body>
</html>
```

可以看得出來，此模組主要是 HTML 的呈現，加上適當的資料呈現。

### 21.CHK

1. Observer 樣式中的 Subject 和 Observer 分別是 MVC 中的？
	A) Controller-Model
	B) Model-Controller
	C) Model-View
	D) View-Model	

2. 關於 MVC，何者錯誤
	A) Model 不應該相依於 Controller 與 View
	B) View 會被 Model 通知狀態的改變，也可能會去讀取 Model 的狀態
	C) Controller 過濾及轉換訊息，把修改的指令給 Model，讓 Model 變更狀態
	D) Model 雖不可相依於 View, 但可相依於 Controller

### 21.EX

### 21.ex01
應用 MVC 架構改寫象棋系統
