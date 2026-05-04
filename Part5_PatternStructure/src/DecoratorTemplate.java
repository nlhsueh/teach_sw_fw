package decorator;

// 1. Component Interface
abstract class Component {
    public abstract void operation();
}

// 2. Concrete Component
class ConcreteComponent extends Component {
    @Override
    public void operation() {
        System.out.println("ConcreteComponent: Performing base operation.");
    }
}

// 3. Decorator Base Class
abstract class Decorator extends Component {
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        if (component != null) {
            component.operation(); // Delegate to wrapped component
        }
    }
}

// 4. Concrete Decorators
class ConcreteDecoratorA extends Decorator {
    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation(); // Call original operation
        addedBehavior();   // Add new behavior
    }

    private void addedBehavior() {
        System.out.println("ConcreteDecoratorA: Added behavior A.");
    }
}

class ConcreteDecoratorB extends Decorator {
    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        System.out.println("ConcreteDecoratorB: Behavior before...");
        super.operation();
    }
}

// 5. Demo
public class DecoratorTemplate {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        
        // Wrapping components
        Component decoratorA = new ConcreteDecoratorA(component);
        Component decoratorB = new ConcreteDecoratorB(decoratorA);

        System.out.println("Executing decorated operations:");
        decoratorB.operation();
    }
}
