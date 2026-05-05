import java.util.Observable;
import java.util.Observer;

class Plant {}

// A subclass of Observable that allows delegation.
class DelegatedOBS extends Observable {
    // from PROTECTED to PUBLIC
    public void clearChanged() {
        super.clearChanged();
    }

    // from PROTECTED to PUBLIC
    public void setChanged() {
        super.setChanged();
    }
}

// 水果是植物
class FruitDelegated extends Plant {
    private String name;
    private float price;
    private DelegatedOBS observable;

    public FruitDelegated(String name, float price) {
       this.name = name;
       this.price = price;
       System.out.println("Fruit created: " + name + " at " + price);
       observable = new DelegatedOBS();
    }

    public String getName() {return name;}
    public float getPrice() {return price;}

    public void setPrice(float price) {
       this.price = price;
       observable.setChanged();
       observable.notifyObservers(new Float(price));
    }
    
    public void addObserver(Observer o) {
        observable.addObserver(o);
    }

    public Observable getObservable() {
       return observable;
    }
}    

public class FruitDelegationExample {
   public static void main(String args[]) {
      // Create the Subject and Observers.
      FruitDelegated s = new FruitDelegated("Grape", 1.29f);
      
      // Mock observers for this example
      Observer jj = (o, arg) -> System.out.println("Observer JJ notified: " + arg);
      Observer wm = (o, arg) -> System.out.println("Observer WM notified: " + arg);

      // Add those Observers!
      s.addObserver(jj);
      s.getObservable().addObserver(wm);

       //make changes to the Subject.
       s.setPrice(4.57f);
       s.setPrice(9.22f);
    }
 }
