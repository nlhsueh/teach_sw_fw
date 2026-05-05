import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VerifierTest extends JFrame {
    JTextField tf1 = new JTextField("Type \"pass\" here");
    JTextField tf2 = new JTextField("TextField2");

    public VerifierTest() {
        setLayout(new BorderLayout());
        getContentPane().add(tf1, BorderLayout.NORTH);
        getContentPane().add(tf2, BorderLayout.SOUTH);

        tf1.setInputVerifier(new PassVerifier());

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(l);

        pack();
        setVisible(true);
    }

    class PassVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;
            return "pass".equals(tf.getText());
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            boolean isValid = verify(input);
            if (!isValid) {
                JOptionPane.showMessageDialog(null, "密碼必須是 'pass'", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
            return isValid;
        }
    }

    public static void main(String[] args) {
        new VerifierTest();
    }
}
