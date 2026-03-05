package swfw.ch11.chess.Panel;

import swfw.ch11.chess.MainFrame;
import swfw.ch11.chess.Manager.GameManager;
import swfw.ch11.chess.Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StartGamePanel extends JPanel {
    private final JComboBox<Player> player1Combo;
    private final JComboBox<Player> player2Combo;
    private final JComboBox<String> modeCombo;

    public StartGamePanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("選擇兩位玩家開始遊戲");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridwidth = 2;
        centerPanel.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        centerPanel.add(new JLabel("先手玩家："), gbc);
        gbc.gridx = 1;
        player1Combo = new JComboBox<>();
        centerPanel.add(player1Combo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("後手玩家："), gbc);
        gbc.gridx = 1;
        player2Combo = new JComboBox<>();
        centerPanel.add(player2Combo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("選擇模式："), gbc);
        gbc.gridx = 1;

        String[] modes = {"標準象棋", "暗棋"};
        modeCombo = new JComboBox<>(modes);
        centerPanel.add(modeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton startButton = new JButton("開始遊戲");
        startButton.addActionListener(e -> {
            Player p1 = (Player) player1Combo.getSelectedItem();
            Player p2 = (Player) player2Combo.getSelectedItem();

            if (p1 == null || p2 == null || p1.equals(p2)) {
                JOptionPane.showMessageDialog(frame, "請選擇兩位不同的玩家", "錯誤", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedMode = (String) modeCombo.getSelectedItem();
            frame.setGameMode(selectedMode);
            GameManager manager = frame.getGameManager();

            manager.setPlayer(p1, p2);
            manager.createGame();

            frame.showGamePanel();
        });
        centerPanel.add(startButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> frame.showMainMenuPanel());
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refreshPlayers(List<Player> players) {
        player1Combo.removeAllItems();
        player2Combo.removeAllItems();
        for (Player p : players) {
            player1Combo.addItem(p);
            player2Combo.addItem(p);
        }
    }
}
