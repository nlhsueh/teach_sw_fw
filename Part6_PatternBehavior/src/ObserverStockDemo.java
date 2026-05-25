package observer.stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

// Subject (Model)
class Stock extends Observable {
    private double yesterdayPrice;
    private double currentPrice;
    private int currentAmount;

    public Stock(double price) {
        this.yesterdayPrice = price;
        this.currentPrice = price;
        this.currentAmount = 1000;
    }

    public double getYesterdayPrice() { return yesterdayPrice; }
    public double getCurrentPrice() { return currentPrice; }
    public int getCurrentAmount() { return currentAmount; }

    public void updateStock() {
        this.yesterdayPrice = this.currentPrice; // 昨日價格更新為前一次的現價
        
        Random r = new Random();
        // 隨機變動在 7% ~ 10% 之間
        double changePercent = 0.07 + (r.nextDouble() * 0.03); 
        boolean up = r.nextBoolean();
        if (up) {
            this.currentPrice = this.currentPrice * (1.0 + changePercent);
        } else {
            this.currentPrice = this.currentPrice * (1.0 - changePercent);
        }
        
        // 成交量變動
        this.currentAmount = (int)(this.currentAmount * (0.9 + r.nextDouble() * 0.2));
        
        setChanged();
        notifyObservers();
    }
}

// 呈現 1：昨日價格 (Y)、目前價格 (C)、及波動百分比 ((C-Y)/C)
class CurrentPriceBoard extends JPanel implements Observer {
    private JLabel lblYesterday = new JLabel("昨日價格: ");
    private JLabel lblCurrent = new JLabel("目前價格: ");
    private JLabel lblPercent = new JLabel("波動比率: ");

    public CurrentPriceBoard() {
        setLayout(new GridLayout(3, 1));
        setBorder(BorderFactory.createTitledBorder("價格看板 (CurrentPriceBoard)"));
        add(lblYesterday);
        add(lblCurrent);
        add(lblPercent);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Stock) {
            Stock s = (Stock) o;
            double y = s.getYesterdayPrice();
            double c = s.getCurrentPrice();
            double percent = (c - y) / y * 100;
            
            lblYesterday.setText(String.format("昨日價格 (Y): %.2f", y));
            lblCurrent.setText(String.format("目前價格 (C): %.2f", c));
            lblPercent.setText(String.format("波動百分比: %.2f%%", percent));
        }
    }
}

// 呈現 2：呈現現價、成交量
class AmountBoard extends JPanel implements Observer {
    private JLabel lblPrice = new JLabel("目前價格: ");
    private JLabel lblAmount = new JLabel("成交量: ");

    public AmountBoard() {
        setLayout(new GridLayout(2, 1));
        setBorder(BorderFactory.createTitledBorder("量能看板 (AmountBoard)"));
        add(lblPrice);
        add(lblAmount);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Stock) {
            Stock s = (Stock) o;
            lblPrice.setText(String.format("目前價格: %.2f", s.getCurrentPrice()));
            lblAmount.setText("成交數量: " + s.getCurrentAmount());
        }
    }
}

// 呈現 3：最近三次價格，連三漲綠色，連三跌紅色，否則白色
class GreenRedBoard extends JPanel implements Observer {
    private JLabel lblHistory = new JLabel("價格歷史: ");
    private java.util.List<Double> priceHistory = new ArrayList<>();

    public GreenRedBoard() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("紅綠燈看板 (GreenRedBoard)"));
        setBackground(Color.WHITE);
        add(lblHistory, BorderLayout.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Stock) {
            Stock s = (Stock) o;
            double c = s.getCurrentPrice();
            
            // 記錄價格歷史，只保留最近三次
            priceHistory.add(c);
            if (priceHistory.size() > 3) {
                priceHistory.remove(0);
            }
            
            // 顯示歷史
            StringBuilder sb = new StringBuilder("價格歷史: ");
            for (double p : priceHistory) {
                sb.append(String.format("[%.2f] ", p));
            }
            lblHistory.setText(sb.toString());

            // 判斷紅綠燈
            if (priceHistory.size() == 3) {
                double p1 = priceHistory.get(0);
                double p2 = priceHistory.get(1);
                double p3 = priceHistory.get(2);
                
                if (p3 > p2 && p2 > p1) {
                    setBackground(Color.GREEN); // 連三漲（綠色）
                } else if (p3 < p2 && p2 < p1) {
                    setBackground(Color.RED);   // 連三跌（紅色）
                } else {
                    setBackground(Color.WHITE); // 維持原色
                }
            } else {
                setBackground(Color.WHITE);
            }
        }
    }
}

// 主程式
public class ObserverStockDemo extends JFrame {
    public ObserverStockDemo() {
        setTitle("Stock Observer Demo");
        setLayout(new GridLayout(3, 1));
        
        Stock stock = new Stock(100.0);
        
        CurrentPriceBoard board1 = new CurrentPriceBoard();
        AmountBoard board2 = new AmountBoard();
        GreenRedBoard board3 = new GreenRedBoard();
        
        stock.addObserver(board1);
        stock.addObserver(board2);
        stock.addObserver(board3);
        
        add(board1);
        add(board2);
        add(board3);
        
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 每 2 秒變動一次價格
        Timer timer = new Timer(2000, e -> stock.updateStock());
        timer.start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ObserverStockDemo().setVisible(true);
        });
    }
}
