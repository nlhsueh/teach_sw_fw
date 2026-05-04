interface Tree {
    void show();
}

class SimpleTree implements Tree {
    public void show() { System.out.println("Tree"); }
}

abstract class TreeDecorator implements Tree {
    protected Tree tree;
    public TreeDecorator(Tree t) { this.tree = t; }
}

class LightDecorator extends TreeDecorator {
    public LightDecorator(Tree t) { super(t); }
    public void show() {
        System.out.print("Lighted ");
        tree.show();
    }
}

class StarDecorator extends TreeDecorator {
    public StarDecorator(Tree t) { super(t); }
    public void show() {
        System.out.print("Starred ");
        tree.show();
    }
}

public class ChristmasTreeDecorator {
    public static void main(String[] args) {
        Tree myTree = new StarDecorator(new LightDecorator(new SimpleTree()));
        myTree.show(); // Output: Starred Lighted Tree
    }
}
