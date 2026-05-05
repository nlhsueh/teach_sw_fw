import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutDemo extends JFrame implements ActionListener {

    private JPanel contentPanel;
    private JComboBox<String> layoutSelector;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label;
    private JTextField textField;

    public LayoutDemo() {
        setTitle("Layout Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化主要內容面板
        contentPanel = new JPanel();
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // 初始化元件
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Longer Button");
        label = new JLabel("A Label");
        textField = new JTextField("Text Field", 10);

        // 初始化下拉式選單
        String[] layoutOptions = {"FlowLayout", "BorderLayout", "GridLayout", "BoxLayout (X_AXIS)", "BoxLayout (Y_AXIS)"};
        layoutSelector = new JComboBox<>(layoutOptions);
        layoutSelector.addActionListener(this);
        getContentPane().add(layoutSelector, BorderLayout.NORTH);

        // 初始版面配置
        switchLayout("FlowLayout");

        pack();
        setLocationRelativeTo(null); // 視窗置中
        setVisible(true);
    }

    private void switchLayout(String layoutName) {
        contentPanel.removeAll(); // 移除所有現有元件
        contentPanel.setLayout(null); // 先設定為 null，以便後續設定新的 LayoutManager

        switch (layoutName) {
            case "FlowLayout":
                contentPanel.setLayout(new FlowLayout());
                break;
            case "BorderLayout":
                contentPanel.setLayout(new BorderLayout());
                break;
            case "GridLayout":
                contentPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 2 行 2 列，間距 5
                break;
            case "BoxLayout (X_AXIS)":
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
                break;
            case "BoxLayout (Y_AXIS)":
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                break;
        }

        // 重新將元件加入到面板中
        if (contentPanel.getLayout() instanceof BorderLayout) {
            contentPanel.add(button1, BorderLayout.NORTH);
            contentPanel.add(button2, BorderLayout.SOUTH);
            contentPanel.add(button3, BorderLayout.EAST);
            contentPanel.add(label, BorderLayout.WEST);
            contentPanel.add(textField, BorderLayout.CENTER);
        } else if (contentPanel.getLayout() instanceof GridLayout) {
            contentPanel.add(button1);
            contentPanel.add(button2);
            contentPanel.add(button3);
            contentPanel.add(label);
            contentPanel.add(textField); // GridLayout 會自動排列，多餘的會被忽略
        } else { // FlowLayout 和 BoxLayout
            contentPanel.add(button1);
            contentPanel.add(button2);
            contentPanel.add(button3);
            contentPanel.add(label);
            contentPanel.add(textField);
        }

        contentPanel.revalidate(); // 重新驗證版面配置
        contentPanel.repaint();    // 重新繪製介面
        pack(); // 重新調整視窗大小以適應內容
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == layoutSelector) {
            String selectedLayout = (String) layoutSelector.getSelectedItem();
            switchLayout(selectedLayout);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LayoutDemo::new);
    }
}
