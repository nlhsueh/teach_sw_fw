package swfw.ch11.chess;

import swfw.ch11.chess.Factory.BlindChessFactory;
import swfw.ch11.chess.Factory.StandardChessFactory;
import swfw.ch11.chess.Manager.GameManager;
import swfw.ch11.chess.Manager.PlayerManager;
import swfw.ch11.chess.Panel.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private GameManager gameManager;

    public MainFrame() {
        setTitle("象棋遊戲");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        gameManager = new GameManager(new StandardChessFactory());

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        PlayerPanel playerPanel = new PlayerPanel(this);
        RulesPanel rulesPanel = new RulesPanel(this);

        StartGamePanel startGamePanel = new StartGamePanel(this);
        cardPanel.add(mainMenuPanel, "MainMenuPanel");
        cardPanel.add(playerPanel, "PlayerPanel");
        cardPanel.add(rulesPanel, "RulesPanel");
        cardPanel.add(startGamePanel, "StartGamePanel");

        showPanel("MainMenuPanel");

        startGamePanel.refreshPlayers(PlayerManager.getPlayers());
    }

    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
    }

    public void showGamePanel() {
        if (gameManager.getBoard() == null) {
            JOptionPane.showMessageDialog(this, "棋盤尚未初始化", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        GamePanel gamePanel = new GamePanel(this, gameManager);
        cardPanel.add(gamePanel, "GamePanel");
        showPanel("GamePanel");
        gamePanel.refreshBoard();
    }

    public void showMainMenuPanel() {
        showPanel("MainMenuPanel");
    }

    public void showStartGamePanel() {
        StartGamePanel startGamePanel = new StartGamePanel(this);
        cardPanel.add(startGamePanel, "StartGamePanel");
        startGamePanel.refreshPlayers(PlayerManager.getPlayers());
        showPanel("StartGamePanel");
    }

    public void setGameMode(String mode) {
        if ("標準象棋".equals(mode)) {
            this.gameManager = new GameManager(new StandardChessFactory());
        } else if ("暗棋".equals(mode)) {
            this.gameManager = new GameManager(new BlindChessFactory());
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
