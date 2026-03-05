package swfw.ch11.chess.Panel;

import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Board.StandardBoard;
import swfw.ch11.chess.MainFrame;
import swfw.ch11.chess.Manager.GameManager;
import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Model.Player;
import swfw.ch11.chess.Model.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.*;

public class GamePanel extends JPanel {
    private final GameManager gameManager;
    private final MainFrame mainFrame;

    private final Map<String, ImageIcon> chessPieceImages = new HashMap<>();
    private final JLabel turnLabel = new JLabel();
    private Point selectedPoint = null;

    public GamePanel(MainFrame mainFrame, GameManager gameManager) {
        this.mainFrame = mainFrame;
        this.gameManager = gameManager;

        loadChessPieceImages();
        setLayout(new BorderLayout());

        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(turnLabel, BorderLayout.NORTH);

        JButton exitButton = new JButton("離開遊戲");
        exitButton.addActionListener(e -> handleExit());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleExit() {
        int result = JOptionPane.showConfirmDialog(this, "確定要離開遊戲並返回主選單嗎？", "確認離開", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            mainFrame.showMainMenuPanel();
        }
    }

    private void loadChessPieceImages() {
        String[] redNames = {"帥", "仕", "相", "居", "傌", "炮", "兵"};
        String[] blackNames = {"將", "士", "象", "車", "馬", "砲", "卒"};

        for (int i=0; i<7; i++) {
            String redFile = "/image/" + "red" + "_" + redNames[i] + ".png";
            String blackFile = "/image/" + "black" + "_" + blackNames[i] + ".png";
            URL redImageURL = getClass().getResource(redFile);
            URL blackImageURL = getClass().getResource(blackFile);
            if (redImageURL != null) {
                ImageIcon icon = new ImageIcon(redImageURL);
                Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                chessPieceImages.put("red" + "_" + redNames[i], new ImageIcon(scaledImage));
            } else {
                System.err.println("找不到圖片: " + redFile);
            }

            if (blackImageURL != null) {
                ImageIcon icon = new ImageIcon(blackImageURL);
                Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                chessPieceImages.put("black" + "_" + blackNames[i], new ImageIcon(scaledImage));
            } else {
                System.err.println("找不到圖片: " + blackFile);
            }
        }

        URL backURL = getClass().getResource("/image/back.png");
        if (backURL != null) {
            ImageIcon backIcon = new ImageIcon(backURL);
            Image scaledBack = backIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            chessPieceImages.put("back", new ImageIcon(scaledBack));
        }
    }

    public void refreshBoard() {
        removeCenterPanel();

        BoardInterface board = gameManager.getBoard();
        if (board == null) return;

        Player currentPlayer = gameManager.getNowPlayer();
        turnLabel.setText("目前回合：" + currentPlayer.getName() + "（" + currentPlayer.getSide().toString() + "）");

        int row = board.getRow();
        int col = board.getCol();

        JPanel boardPanel;
        if(gameManager.getBoard() instanceof StandardBoard) boardPanel = new JPanel(new GridLayout(row+1, col));
        else boardPanel = new JPanel(new GridLayout(row, col));
        boardPanel.setPreferredSize(new Dimension(600, 600));

        for (int i = 0; i < row; i++) {

            if (gameManager.getBoard() instanceof StandardBoard && i == 5) {
                for (int j = 0; j < col; j++) {
                    JLabel riverLabel = new JLabel();
                    riverLabel.setOpaque(true);
                    riverLabel.setBackground(Color.BLACK);
                    riverLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    boardPanel.add(riverLabel);
                }
            }


            for (int j = 0; j < col; j++) {
                final int x = i, y = j;
                ChessInterface chess = board.getChess(i, j);
                JLabel cellLabel = new JLabel();
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
                cellLabel.setVerticalAlignment(SwingConstants.CENTER);
                cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if(chess != null) {
                    if (!chess.isShow()) {
                        cellLabel.setIcon(chessPieceImages.get("back"));
                    } else {
                        ImageIcon icon = chessPieceImages.get(chess.getSide().toString().toLowerCase() + "_" + chess.getName());
                        if (icon != null) {
                            cellLabel.setIcon(icon);
                        } else {
                            cellLabel.setText(chess.getName());
                        }
                    }
                }

                if (selectedPoint != null && selectedPoint.x == x && selectedPoint.y == y) {
                    cellLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                }

                cellLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(gameManager.getFirstPlayer().getName()+" ( "+gameManager.getFirstPlayer().getSide()+" ) clicked");
                        System.out.println("Clicked on: " + x + "," + y);
                        ChessInterface clickedChess = board.getChess(x, y);

                        if (clickedChess == null) {
                            System.out.println("空格");
                            if (selectedPoint != null) {
                                boolean success = gameManager.move(selectedPoint.x, selectedPoint.y, x, y);
                                if (success) {
                                    selectedPoint = null;
                                    refreshBoard();
                                }
                            }
                            return;
                        }
                        System.out.println("是棋子，是否翻開：" + clickedChess.isShow() +
                                "　陣營：" + clickedChess.getSide());

                        clickedChess = board.getChess(x, y);

                        if (!clickedChess.isShow()) {
                            clickedChess.openChess();

                            if (gameManager.getFirstPlayer().getSide() == Side.UNDEFINED) {
                                gameManager.getFirstPlayer().setSide(clickedChess.getSide());
                                gameManager.getSecondPlayer().setSide(
                                        clickedChess.getSide() == Side.RED ? Side.BLACK : Side.RED
                                );
                                System.out.println("Set " + gameManager.getFirstPlayer().getName() + "'s side to " + clickedChess.getSide());
                            }
                            selectedPoint = null;

                            gameManager.changePlayer();
                            refreshBoard();
                            return;
                        }

                        if (selectedPoint == null) {
                            if (clickedChess.getSide() == gameManager.getNowPlayer().getSide()) {
                                selectedPoint = new Point(x, y);
                                refreshBoard();
                            }
                            return;
                        }

                        boolean success = gameManager.move(selectedPoint.x, selectedPoint.y, x, y);
                        if (success) {
                            selectedPoint = null;
                        } else {
                            if (clickedChess.getSide() == gameManager.getNowPlayer().getSide()) {
                                selectedPoint = new Point(x, y);
                            } else {
                                selectedPoint = null;
                            }
                        }

                        refreshBoard();
                    }
                });

                boardPanel.add(cellLabel);
            }
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        boardPanel.revalidate();
        boardPanel.repaint();

        // 遊戲結束?
        if (gameManager.gameOver()) {
            Player winner = gameManager.getWinner();
            String resultMsg = (winner != null)
                    ? "遊戲結束！" + winner.getName() + "（" + winner.getSide() + "）獲勝！"
                    : "遊戲結束！平手！";

            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), resultMsg, "遊戲結果", JOptionPane.INFORMATION_MESSAGE);

            mainFrame.showMainMenuPanel();
        }
    }

    private void removeCenterPanel() {
        BorderLayout layout = (BorderLayout) getLayout();
        Component center = layout.getLayoutComponent(BorderLayout.CENTER);
        if (center != null) {
            remove(center);
        }
    }
}
