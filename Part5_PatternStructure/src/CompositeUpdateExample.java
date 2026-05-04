import java.util.ArrayList;
import java.util.List;

// The unified interface
interface Component {
    void update();
}

// Leaf implementation
class Leaf implements Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("Leaf " + name + ": Updating...");
    }
}

// Composite implementation
class Composite implements Component {
    private List<Component> children = new ArrayList<>();
    private String name;

    public Composite(String name) {
        this.name = name;
    }

    public void addComponent(Component component) {
        children.add(component);
    }

    public void removeComponent(Component component) {
        children.remove(component);
    }

    @Override
    public void update() {
        System.out.println("Composite " + name + ": Updating its children...");
        for (Component child : children) {
            child.update(); // Recursive call
        }
        System.out.println("Composite " + name + ": Finished updating.");
    }
}

public class CompositeUpdateExample {
    public static void main(String[] args) {
        Composite root = new Composite("Root");
        root.addComponent(new Leaf("A"));
        
        Composite sub = new Composite("Sub");
        sub.addComponent(new Leaf("B1"));
        sub.addComponent(new Leaf("B2"));
        
        root.addComponent(sub);
        
        root.update();
    }
}
