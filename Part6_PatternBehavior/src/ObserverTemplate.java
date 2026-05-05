package observer;

import java.util.Observable;

public class ObserverTemplate {

  public static void main(String[] args) {
     Subject s = new Subject();

     View1 v1 = new View1();
     View2 v2 = new View2();
     s.addObserver(v1);
     s.addObserver(v2);
     s.setData(10);
  }
}

class Subject extends java.util.Observable {
   int data;

   public Subject() {
     data = 0;
   }

   public void setData(int newValue) {
     data = newValue;
     this.setChanged();
     this.notifyObservers();
   }

   public int getData() {
       return data;
   }
}

class View1 implements java.util.Observer {
   public void update(Observable arg0, Object arg1) {
      System.out.println("View1 updated: " + ((Subject)arg0).getData());
   }
}

class View2 implements java.util.Observer {
   public void update(Observable o, Object arg) {
      System.out.println("View2 updated: " + ((Subject)o).getData());
   }
}
