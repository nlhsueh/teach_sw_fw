abstract class AbsApp {
    public final void tm() {           // Template Method
        pm01();
        pm02();
        pm03();
    }

    abstract void pm01();              // Primitive Method
    abstract void pm02();              // Primitive Method
    abstract void pm03();              // Primitive Method
}

class App extends AbsApp {
    void pm01() {
        // ...
    }
    void pm02() {
        // ...
    }
    void pm03() {
        // ...
    }
}
