import java.util.ArrayList;
import java.util.Iterator;

public class CompositeTemplate {
    public static void main(String args[]) {
        // Just for demonstration
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
    abstract void op(); // OPERATION
}

// COMPOSITE
class Composite extends Component {
    private ArrayList<Component> list;
    private String name;

    public Composite(String n) {
        this.name = n;
        this.list = new ArrayList<Component>();
    }

    public void addComponent(Component c) {
        list.add(c);
    }

    // OPERATION in COMPOSITE
    @Override
    void op() {
        System.out.println("Composite: " + name);
        Iterator<Component> iterator = list.iterator();
        while (iterator.hasNext()) {
            Component c = iterator.next();
            c.op();
        }
    }
}

// LEAF
class Leaf extends Component {
    private String name;

    public Leaf(String a) {
        this.name = a;
    }

    // OPERATION in LEAF
    @Override
    void op() {
        System.out.println("Leaf: " + name);
    }
}
