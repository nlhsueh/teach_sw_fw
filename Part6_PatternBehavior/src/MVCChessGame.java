package mvc.chess.solution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/* --- MODEL (被觀察者 / Subject) --- */
class ChessGameModel {
    private String gameState = "WAITING"; // WAITING, STARTED, FINISHED
    private String currentTurn = "RED";    // RED, BLACK
    private String lastMove = "None";

    private List<Consumer<ChessGameModel>> observers = new ArrayList<>();

    public String getGameState() { return gameState; }
    public String getCurrentTurn() { return currentTurn; }
    public String getLastMove() { return lastMove; }

    public void addObserver(Consumer<ChessGameModel> observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Consumer<ChessGameModel> observer : observers) {
            observer.accept(this);
        }
    }

    public void startGame() {
        if (gameState.equals("WAITING")) {
            gameState = "STARTED";
            currentTurn = "RED";
            lastMove = "Game started! Red's turn.";
            notifyObservers();
        }
    }

    public void makeMove(String moveDetails) {
        if (gameState.equals("STARTED")) {
            lastMove = currentTurn + " moved: " + moveDetails;
            currentTurn = currentTurn.equals("RED") ? "BLACK" : "RED";
            notifyObservers();
        }
    }

    public void endGame(String winner) {
        if (gameState.equals("STARTED")) {
            gameState = "FINISHED";
            lastMove = "Game Over! Winner: " + winner;
            notifyObservers();
        }
    }

    public void resetGame() {
        gameState = "WAITING";
        currentTurn = "RED";
        lastMove = "Game reset to waiting state.";
        notifyObservers();
    }
}

/* --- VIEW (觀察者 / Observer) --- */
class ChessBoardView extends JPanel {
    private JLabel lblState = new JLabel("遊戲狀態: WAITING");
    private JLabel lblTurn = new JLabel("目前回合: RED");
    private JTextArea txtLogs = new JTextArea(5, 20);

    public ChessBoardView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("棋局畫面 (View)"));
        
        JPanel pnlStatus = new JPanel(new GridLayout(2, 1));
        lblState.setFont(new Font("Arial", Font.BOLD, 14));
        lblTurn.setFont(new Font("Arial", Font.BOLD, 14));
        pnlStatus.add(lblState);
        pnlStatus.add(lblTurn);
        
        txtLogs.setEditable(false);
        txtLogs.setBackground(new Color(245, 245, 245));
        
        add(pnlStatus, BorderLayout.NORTH);
        add(txtLogs, BorderLayout.CENTER);
    }

    public void updateView(ChessGameModel model) {
        lblState.setText("遊戲狀態: " + model.getGameState());
        lblTurn.setText("目前回合: " + (model.getGameState().equals("STARTED") ? model.getCurrentTurn() : "N/A"));
        txtLogs.append(model.getLastMove() + "\n");
    }
}

/* --- CONTROLLER (控制器 / Controller) --- */
class ChessGameController extends JPanel {
    private ChessGameModel model;
    
    private JButton btnStart = new JButton("開始遊戲");
    private JButton btnMove = new JButton("模擬下棋");
    private JButton btnEnd = new JButton("結束遊戲");
    private JButton btnReset = new JButton("重新初始化");

    public ChessGameController(ChessGameModel model) {
        this.model = model;
        setLayout(new GridLayout(1, 4, 5, 5));
        setBorder(BorderFactory.createTitledBorder("控制面板 (Controller)"));

        add(btnStart);
        add(btnMove);
        add(btnEnd);
        add(btnReset);

        btnStart.addActionListener(e -> model.startGame());
        
        btnMove.addActionListener(e -> {
            if (model.getGameState().equals("STARTED")) {
                String[] sampleMoves = {"俥二進九", "馬8進7", "炮二平五", "象3進5", "兵五進一"};
                int randomIndex = (int)(Math.random() * sampleMoves.length);
                model.makeMove(sampleMoves[randomIndex]);
            }
        });
        
        btnEnd.addActionListener(e -> {
            if (model.getGameState().equals("STARTED")) {
                model.endGame(model.getCurrentTurn());
            }
        });
        
        btnReset.addActionListener(e -> model.resetGame());
    }
}

/* --- MAIN ENTRY --- */
public class MVCChessGame extends JFrame {
    public MVCChessGame() {
        setTitle("Chinese Chess MVC Solution");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ChessGameModel model = new ChessGameModel();
        ChessBoardView view = new ChessBoardView();
        
        // 註冊 View 的渲染更新方法
        model.addObserver(view::updateView);

        ChessGameController controller = new ChessGameController(model);

        add(view, BorderLayout.CENTER);
        add(controller, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MVCChessGame().setVisible(true);
        });
    }
}
