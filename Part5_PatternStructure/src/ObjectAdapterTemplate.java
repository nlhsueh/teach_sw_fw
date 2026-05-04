package adaptor.objectadaptor;

// Target class
class Target {
    public void request(Object arg) {
        // ...
    }
}

// Adaptee class with a different interface
class Adaptee {
    public void specificRequest(Object arg) {
        // ...
    }
}

// Adapter using delegation (Object Adapter)
class Adapter extends Target {
    private Adaptee adaptee;

    public Adapter(Adaptee a) {
        this.adaptee = a;
    }

    public void request(Object arg) {
        adaptee.specificRequest(arg);
    }
}

class Client {
    public void main(String args[]) {
        Target t = new Adapter(new Adaptee());
        t.request(Integer.valueOf(1));
    }
}
