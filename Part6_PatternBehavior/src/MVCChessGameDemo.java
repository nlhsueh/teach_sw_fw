package mvc.chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/* --- MODEL --- */
class ChessGameModel extends Observable {
    private String gameState = "WAITING"; // WAITING, STARTED, FINISHED
    private String currentTurn = "RED";    // RED, BLACK
    private String lastMove = "None";

    public String getGameState() { return gameState; }
    public String getCurrentTurn() { return currentTurn; }
    public String getLastMove() { return lastMove; }

    public void startGame() {
        if (gameState.equals("WAITING")) {
            gameState = "STARTED";
            currentTurn = "RED";
            lastMove = "Game started! Red's turn.";
            setChanged();
            notifyObservers();
        }
    }

    public void makeMove(String moveDetails) {
        if (gameState.equals("STARTED")) {
            lastMove = currentTurn + " moved: " + moveDetails;
            // 交換回合
            currentTurn = currentTurn.equals("RED") ? "BLACK" : "RED";
            setChanged();
            notifyObservers();
        }
    }

    public void endGame(String winner) {
        if (gameState.equals("STARTED")) {
            gameState = "FINISHED";
            lastMove = "Game Over! Winner: " + winner;
            setChanged();
            notifyObservers();
        }
    }

    public void resetGame() {
        gameState = "WAITING";
        currentTurn = "RED";
        lastMove = "Game reset to waiting state.";
        setChanged();
        notifyObservers();
    }
}

/* --- VIEW --- */
// 棋局狀態與日誌看板
class ChessBoardView extends JPanel implements Observer {
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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ChessGameModel) {
            ChessGameModel model = (ChessGameModel) o;
            lblState.setText("遊戲狀態: " + model.getGameState());
            lblTurn.setText("目前回合: " + (model.getGameState().equals("STARTED") ? model.getCurrentTurn() : "N/A"));
            txtLogs.append(model.getLastMove() + "\n");
        }
    }
}

/* --- CONTROLLER --- */
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

        // 綁定事件監聽，將 UI 動作轉譯為對 Model 的方法呼叫
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
public class MVCChessGameDemo extends JFrame {
    public MVCChessGameDemo() {
        setTitle("Chinese Chess MVC Demo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. 初始化 Model
        ChessGameModel model = new ChessGameModel();

        // 2. 初始化 View 並且將其註冊（觀察）到 Model 上
        ChessBoardView view = new ChessBoardView();
        model.addObserver(view);

        // 3. 初始化 Controller 並且注入 Model
        ChessGameController controller = new ChessGameController(model);

        // 將元件排版到主視窗
        add(view, BorderLayout.CENTER);
        add(controller, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MVCChessGameDemo().setVisible(true);
        });
    }
}
