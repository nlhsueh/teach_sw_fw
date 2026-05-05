package org.example;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class SSNVerifier extends InputVerifier {

    private static final String TAIWAN_ID_REGEX = "^[A-Z][12]\\d{8}$";
    private static final int[] WEIGHT = {1, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final String LETTERS = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
    private static final int[] VALUES = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};

    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String id = textField.getText().trim().toUpperCase();

        if (!id.matches(TAIWAN_ID_REGEX)) {
            return false;
        }

        int letterValue = VALUES[LETTERS.indexOf(id.charAt(0))];
        int sum = (letterValue / 10) * WEIGHT[0] + (letterValue % 10) * WEIGHT[1];

        for (int i = 1; i < 10; i++) {
            sum += Character.getNumericValue(id.charAt(i)) * WEIGHT[i + 1];
        }

        return (sum % 10 == 0);
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean valid = verify(input);
        if (!valid) {
            JOptionPane.showMessageDialog(input, "台灣身分證字號格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
            ((JTextField) input).selectAll();
        }
        return valid;
    }

    public static void main(String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame("身分證驗證應用程式");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.FlowLayout());

        javax.swing.JLabel label = new javax.swing.JLabel("請輸入台灣身分證字號：");
        javax.swing.JTextField textField = new javax.swing.JTextField(10);
        textField.setInputVerifier(new SSNVerifier());

        javax.swing.JButton submitButton = new javax.swing.JButton("驗證");
        submitButton.addActionListener(e -> {
            if (textField.getInputVerifier().verify(textField)) {
                JOptionPane.showMessageDialog(frame, "身分證字號格式正確！", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.add(label);
        frame.add(textField);
        frame.add(submitButton);

        frame.setSize(300, 150);
        frame.setVisible(true);
    }
}
