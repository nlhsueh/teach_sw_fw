import java.util.Observable;
import java.util.Observer;

// SUBJECT
class Fruit extends Observable {
    private String name;
    private float price;

    public Fruit(String name, float price) {
       this.name = name;
       this.price = price;
       System.out.println("Fruit created: " + name + " at " + price);
    }

    public String getName() {return name;}
    public float getPrice() {return price;}

    public void setPrice(float price) { //SETSTATE
       this.price = price;
       setChanged();
       notifyObservers(new Float(price)); //NOTIFYOBSERVER()
    }
}

class Monkey implements Observer {
    float price;
    public void update(Observable obj, Object newValue) {
       if (newValue instanceof Float) {
           price = ((Float)newValue).floatValue();
           System.out.println("Monkey: 水果價格變成" + price);
        }
     }
}

class WineMaker implements Observer {
    float originalPrice = 10.0f;
    float price;
    public void update(Observable obj, Object newValue) {
       if (newValue instanceof Float) {
           price = ((Float)newValue).floatValue();
           if ( (price-originalPrice)/price < -0.1)
              System.out.println("WineMaker: Can make wine");
           else
              System.out.println("WineMaker: too expensive");
        }
     }
}

public class FruitExample {
   public static void main(String args[]) {
      // Create the Subject and Observers.
      Fruit s = new Fruit("Grape", 1.29f);
      Monkey jj = new Monkey();
      WineMaker wm = new WineMaker();

      // Add those Observers!
      s.addObserver(jj);
      s.addObserver(wm);

      //make changes to the Subject.
      s.setPrice(4.57f);
      s.setPrice(9.22f);
   }
}
