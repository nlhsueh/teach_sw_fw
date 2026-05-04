package bridge;

// Implementor interface
interface Implementor {
    public void m1();
    public void m2();
}

// Concrete Implementations
class ConcreteImplA implements Implementor {
    public void m1() {
        // ... implementation A
    }

    public void m2() {
        // ... implementation A
    }
}

class ConcreteImplB implements Implementor {
    public void m1() {
        // ... implementation B
    }

    public void m2() {
        // ... implementation B
    }
}

// Abstraction
abstract class Abstraction {
    protected Implementor imp;

    public Abstraction(Implementor imp) {
        this.imp = imp;
    }

    protected void m1() {
        imp.m1();
    }

    protected void m2() {
        imp.m2();
    }

    abstract void operation();
}

// Refined Abstractions
class RefinedAbstraction1 extends Abstraction {
    public RefinedAbstraction1(Implementor imp) {
        super(imp);
    }

    // specific operation implementation
    @Override
    void operation() {
        m1();
        m2();
        // ...
    }
}

class RefinedAbstraction2 extends Abstraction {
    public RefinedAbstraction2(Implementor imp) {
        super(imp);
    }

    // specific operation implementation
    @Override
    void operation() {
        m2();
        m1();
        // ...
    }
}
