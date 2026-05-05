import javax.swing.*;
import java.awt.event.*;

public class EventModelExample extends JFrame {
     private JButton b1;
     public EventModelExample() {
        b1 =  new JButton("Button");
        getContentPane().add(b1);

        //相當於 ADDOBSERVER()
        b1.addActionListener(new Listener1());
        b1.addActionListener(new Listener2());
        
        setSize(200, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
     }

     public static void main(String[] args) {
         new EventModelExample();
     }
}

// OBSERVER
class Listener1 implements ActionListener {
    // UPDATE()
    public void actionPerformed(ActionEvent e) {
        System.out.println("Listener1: Event happen");
    }
}

// OBSERVER
class Listener2 implements ActionListener {
    // UPDATE()
    public void actionPerformed(ActionEvent e) {
        System.out.println("Listener2: Hello world");
    }
}
