import javax.swing.*;
import java.awt.*;

public class ContainerCompositeExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Container Composite Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Leaf components
        JButton button1 = new JButton("Button 1");
        JLabel label1 = new JLabel("Label 1");

        // Composite component (Container)
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(new JButton("Button A"));
        panel1.add(new JTextField(10));

        // Adding both Leaf and Composite to the top-level Container
        frame.add(button1);
        frame.add(label1);
        frame.add(panel1);

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
