package mvc.chess;

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

    // 💡 課堂練習提示 1：使用 Java 8 的 Consumer 介面儲存所有觀察者
    // TODO: 請宣告一個儲存觀察者 (Consumer<ChessGameModel>) 的 List
    private List<Consumer<ChessGameModel>> observers = new ArrayList<>();

    public String getGameState() { return gameState; }
    public String getCurrentTurn() { return currentTurn; }
    public String getLastMove() { return lastMove; }

    // 💡 課堂練習提示 2：請實作註冊觀察者的方法
    public void addObserver(Consumer<ChessGameModel> observer) {
        // [TODO: 請將傳入的 observer 加入至 observers 列表中]
        
    }

    // 💡 課堂練習提示 3：請實作通知所有觀察者的方法
    private void notifyObservers() {
        // [TODO: 請逐一呼叫每個觀察者的 accept 方法，並將自己 (this) 傳入以發送通知]
        
    }

    public void startGame() {
        if (gameState.equals("WAITING")) {
            gameState = "STARTED";
            currentTurn = "RED";
            lastMove = "Game started! Red's turn.";
            
            // 💡 課堂練習提示 4：狀態改變時需通知觀察者更新
            // [TODO: 請呼叫您的通知方法]
            
        }
    }

    public void makeMove(String moveDetails) {
        if (gameState.equals("STARTED")) {
            lastMove = currentTurn + " moved: " + moveDetails;
            currentTurn = currentTurn.equals("RED") ? "BLACK" : "RED";
            
            // [TODO: 請呼叫您的通知方法]
            
        }
    }

    public void endGame(String winner) {
        if (gameState.equals("STARTED")) {
            gameState = "FINISHED";
            lastMove = "Game Over! Winner: " + winner;
            
            // [TODO: 請呼叫您的通知方法]
            
        }
    }

    public void resetGame() {
        gameState = "WAITING";
        currentTurn = "RED";
        lastMove = "Game reset to waiting state.";
        
        // [TODO: 請呼叫您的通知方法]
        
    }
}

/* --- VIEW (觀察者 / Observer) --- */
// 棋局狀態與日誌看板
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

    // 💡 課堂練習提示 5：實作符合 Consumer 介面規格的畫面更新方法 (以接收 Model 作為參數)
    public void updateView(ChessGameModel model) {
        // [TODO: 請讀取 model 的最新狀態，並更新至 lblState, lblTurn 和 txtLogs 的文字元件中]
        
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
        setTitle("Chinese Chess MVC Demo (Student Template)");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. 初始化 Model
        ChessGameModel model = new ChessGameModel();

        // 2. 初始化 View
        ChessBoardView view = new ChessBoardView();

        // 💡 課堂練習提示 6：將 View 註冊到 Model 中
        // [TODO: 請將 view 的 updateView 方法作為 Consumer 註冊到 model 中，可使用方法參照 (Method Reference) 或 Lambda]
        

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
