// 方案 2: 使用 Strategy (策略) 樣式
// 在 TextView 建立時帶入 Border 和 Scrollbar 的策略物件，透過組合來形成各種不同的 TextView。

// 邊框策略
interface BorderStrategy {
    void drawBorder();
}

class PlainBorder implements BorderStrategy {
    public void drawBorder() { System.out.println("繪製一般型邊框 (Plain Border)"); }
}

class ThreeDBorder implements BorderStrategy {
    public void drawBorder() { System.out.println("繪製 3D 型邊框 (3D Border)"); }
}

class FancyBorder implements BorderStrategy {
    public void drawBorder() { System.out.println("繪製花俏型邊框 (Fancy Border)"); }
}

// 捲軸策略
interface ScrollbarStrategy {
    void drawScrollbar();
}

class NoScroll implements ScrollbarStrategy {
    public void drawScrollbar() { /* 無捲軸，不繪製 */ }
}

class HorizontalScroll implements ScrollbarStrategy {
    public void drawScrollbar() { System.out.println("繪製水平捲軸 (Horizontal Scrollbar)"); }
}

class VerticalScroll implements ScrollbarStrategy {
    public void drawScrollbar() { System.out.println("繪製垂直捲軸 (Vertical Scrollbar)"); }
}

class BothScroll implements ScrollbarStrategy {
    public void drawScrollbar() { System.out.println("繪製水平與垂直捲軸"); }
}

public class TextViewStrategy {
    private BorderStrategy borderStrategy;
    private ScrollbarStrategy scrollbarStrategy;

    // 在 TextView 建立的時候帶入兩個參數
    public TextViewStrategy(BorderStrategy border, ScrollbarStrategy scrollbar) {
        this.borderStrategy = border;
        this.scrollbarStrategy = scrollbar;
    }

    public void draw() {
        if (borderStrategy != null) {
            borderStrategy.drawBorder();
        }
        
        System.out.println("繪製 TextView 核心文字內容");
        
        if (scrollbarStrategy != null) {
            scrollbarStrategy.drawScrollbar();
        }
    }

    public static void main(String[] args) {
        // 透過參數的組合來形成各種不同的 TextView
        // 例如：3D 邊框 + 垂直捲軸
        System.out.println("--- 建立 3D 邊框 + 垂直捲軸 的 TextView ---");
        TextViewStrategy textView1 = new TextViewStrategy(new ThreeDBorder(), new VerticalScroll());
        textView1.draw();

        System.out.println("\n--- 建立 花俏邊框 + 無捲軸 的 TextView ---");
        TextViewStrategy textView2 = new TextViewStrategy(new FancyBorder(), new NoScroll());
        textView2.draw();
    }
}
