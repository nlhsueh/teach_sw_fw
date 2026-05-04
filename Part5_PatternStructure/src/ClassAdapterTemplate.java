package adaptor.classadaptor;

// Target interface
interface Target {
    public void request(Object arg);
}

// Adaptee class with a different interface
class Adaptee {
    public void SpecificRequest(Object arg) {
        //...
    }
}

// Adapter using inheritance (Class Adapter)
class Adapter extends Adaptee implements Target {
    public void request(Object arg) {
        this.SpecificRequest(arg);
    }
}

class Client {
    // t can be a Target or an Adapter (which implements Target)
    public void makeRequest(Target t, Object o) {
        t.request(o);
    }
}
