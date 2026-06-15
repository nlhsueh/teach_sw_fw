// 傳統無中介者設計：元件間緊密耦合
class TextBox {
    private Button button;
    private Checkbox checkbox;

    public void setReferences(Button b, Checkbox c) {
        this.button = b;
        this.checkbox = c;
    }

    public void onTextChanged(String text) {
        if (text.isEmpty()) {
            button.setEnabled(false);
        } else if (checkbox.isChecked()) {
            button.setEnabled(true);
        }
    }

    public void clear() {
        System.out.println("TextBox: 清空文字內容");
    }
}

class Checkbox {
    private Button button;
    private TextBox textBox;

    public void setReferences(Button b, TextBox t) {
        this.button = b;
        this.textBox = t;
    }

    public void onCheckedChanged(boolean checked) {
        // 直接存取並操作 Button 的狀態
        if (checked && !button.isTextBoxEmpty()) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }

    public boolean isChecked() {
        return true; // 簡化模擬
    }

    public void setChecked(boolean checked) {
        System.out.println("Checkbox: 設定勾選狀態為 " + checked);
    }
}

class Button {
    private TextBox textBox;
    private Checkbox checkbox;

    public void setReferences(TextBox t, Checkbox c) {
        this.textBox = t;
        this.checkbox = c;
    }

    public void setEnabled(boolean enabled) {
        System.out.println("Button: 啟用狀態設定為 -> " + enabled);
    }

    public boolean isTextBoxEmpty() {
        return false; // 簡化模擬
    }

    public void onClick() {
        System.out.println("Button: 執行登入程序...");
        // 直接呼叫其他元件進行聯動狀態清除
        textBox.clear();
        checkbox.setChecked(false);
    }
}

public class NoMediatorDemo {
    public static void main(String[] args) {
        TextBox textBox = new TextBox();
        Checkbox checkbox = new Checkbox();
        Button button = new Button();

        textBox.setReferences(button, checkbox);
        checkbox.setReferences(button, textBox);
        button.setReferences(textBox, checkbox);

        System.out.println("--- 測試傳統緊密耦合設計 ---");
        textBox.onTextChanged("hello");
        button.onClick();
    }
}
