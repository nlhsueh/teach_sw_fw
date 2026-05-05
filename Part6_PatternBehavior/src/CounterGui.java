import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CounterGui extends JFrame {
  //counter 是 MODEL
  private int counter = 0;

  //tf 是 VIEW
  private JTextField tf = new JTextField(10);

  public CounterGui(String title) {
    super(title);
    JPanel tfPanel = new JPanel();
    tf.setText("0");
    tfPanel.add(tf);
    add(tfPanel, BorderLayout.NORTH);
    JPanel buttonPanel = new JPanel();

    //CONTROLLER   
    JButton incButton = new JButton("Increment");
    incButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            counter++;
            tf.setText(counter + "");
        }
      }
    );
    buttonPanel.add(incButton);

    JButton decButton = new JButton("Decrement"); 
    decButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          counter--;
          tf.setText(counter + "");
        }
      }
    );

    buttonPanel.add(decButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  public static void main(String[] argv) {
    CounterGui cg = new CounterGui("CounterGui");
    cg.setSize(300, 100);
    cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    cg.setVisible(true);
  }
}
