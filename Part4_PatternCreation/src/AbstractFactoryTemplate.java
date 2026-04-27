package abstractfactory;

// 使用者 (Client)
class Client {
    // 不同的工廠，產生不同系列的零件
    void doSomeThing(AbstractFactory f) {
        AbstractProductA a = f.createProductA();
        AbstractProductB b = f.createProductB();
        a.m1(10);
        b.doit(20);
    }
}

// 抽象零件 A
abstract class AbstractProductA {
    public abstract void m1(int x);
}

// 系列 1 的零件 A
class ProductA1 extends AbstractProductA {
    @Override
    public void m1(int x) { /* 系列 1 的實作 */ }
}

// 系列 2 的零件 A
class ProductA2 extends AbstractProductA {
    @Override
    public void m1(int x) { /* 系列 2 的實作 */ }
}

// 抽象零件 B
abstract class AbstractProductB {
    public abstract void doit(int x);
}

// 系列 1 的零件 B
class ProductB1 extends AbstractProductB {
    @Override
    public void doit(int x) { /* 系列 1 的實作 */ }
}

// 系列 2 的零件 B
class ProductB2 extends AbstractProductB {
    @Override
    public void doit(int x) { /* 系列 2 的實作 */ }
}

// 抽象工廠介面
interface AbstractFactory {
    AbstractProductA createProductA();
    AbstractProductB createProductB();
}

// 系列 1 的工廠
class Factory1 implements AbstractFactory {
    @Override
    public AbstractProductA createProductA() { return new ProductA1(); }
    @Override
    public AbstractProductB createProductB() { return new ProductB1(); }
}

// 系列 2 的工廠
class Factory2 implements AbstractFactory {
    @Override
    public AbstractProductA createProductA() { return new ProductA2(); }
    @Override
    public AbstractProductB createProductB() { return new ProductB2(); }
}
