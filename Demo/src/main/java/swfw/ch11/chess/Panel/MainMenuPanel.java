package swfw.ch11.chess.Panel;

import swfw.ch11.chess.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);

        JLabel title = new JLabel("象棋遊戲主選單");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JButton btnPlayerManager = new JButton("玩家管理");
        JButton btnViewRules = new JButton("查看規則");
        JButton btnStartGame = new JButton("開始遊戲");

        Dimension btnSize = new Dimension(200, 40);
        for (JButton btn : new JButton[]{btnPlayerManager, btnViewRules, btnStartGame}) {
            btn.setPreferredSize(btnSize);
        }

        btnPlayerManager.addActionListener(e -> frame.showPanel("PlayerPanel"));
        btnViewRules.addActionListener(e -> frame.showPanel("RulesPanel"));
        btnStartGame.addActionListener(e -> frame.showStartGamePanel());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy++;
        add(btnPlayerManager, gbc);

        gbc.gridy++;
        add(btnViewRules, gbc);

        gbc.gridy++;
        add(btnStartGame, gbc);
    }
}

