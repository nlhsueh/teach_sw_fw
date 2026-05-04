public class TextViewDecoratorExample {
    public static void main(String[] args) {
        // 1. Simple TextView
        TextView text = new SimpleTextView();
        
        // 2. Decorate with Border
        TextView borderText = new BorderDecorator(text);
        
        // 3. Decorate with Scroll (wrap the border text)
        TextView scrollBorderText = new ScrollDecorator(borderText);
        
        System.out.println("Drawing decorated text:");
        scrollBorderText.draw();
    }
}

abstract class TextView {
    abstract void draw();
}

class SimpleTextView extends TextView {
    @Override
    void draw() {
        System.out.println("Drawing Text");
    }
}

abstract class Decorator extends TextView {
    protected TextView component;
    public Decorator(TextView c) {
        this.component = c;
    }
}

class BorderDecorator extends Decorator {
    public BorderDecorator(TextView c) { super(c); }
    @Override
    void draw() {
        System.out.println("Drawing Border");
        component.draw();
    }
}

class ScrollDecorator extends Decorator {
    public ScrollDecorator(TextView c) { super(c); }
    @Override
    void draw() {
        System.out.println("Drawing ScrollBar");
        component.draw();
    }
}
