import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// 1. 同事共同介面：Command 樣式
interface Command {
    void execute();
}

// 2. 抽象中介者介面
interface IMediator {
    void book();
    void view();
    void search();
    
    void registerView(BtnView v);
    void registerSearch(BtnSearch s);
    void registerBook(BtnBook b);
    void registerDisplay(LblDisplay d);
}

// 3. 具體中介者：集中控制所有按鈕狀態與標籤更新
class BookStoreMediator implements IMediator {
    private BtnView btnView;
    private BtnSearch btnSearch;
    private BtnBook btnBook;
    private LblDisplay show;

    @Override
    public void registerView(BtnView v) { btnView = v; }
    @Override
    public void registerSearch(BtnSearch s) { btnSearch = s; }
    @Override
    public void registerBook(BtnBook b) { btnBook = b; }
    @Override
    public void registerDisplay(LblDisplay d) { show = d; }

    @Override
    public void book() {
        btnBook.setEnabled(false);
        btnView.setEnabled(true);
        btnSearch.setEnabled(true);
        show.setText("Booking...");
    }

    @Override
    public void view() {
        btnView.setEnabled(false);
        btnSearch.setEnabled(true);
        btnBook.setEnabled(true);
        show.setText("Viewing...");
    }

    @Override
    public void search() {
        btnSearch.setEnabled(false);
        btnView.setEnabled(true);
        btnBook.setEnabled(true);
        show.setText("Searching...");
    }
}

// 4. 各個具體 Colleagues 元件 (只與 Mediator 對話)
class BtnView extends JButton implements Command {
    private IMediator med;

    BtnView(ActionListener al, IMediator m) {
        super("View");
        addActionListener(al);
        med = m;
        med.registerView(this); // 向中介者註冊
    }

    @Override
    public void execute() {
        med.view(); // 委託中介者決定後續動作
    }
}

class BtnSearch extends JButton implements Command {
    private IMediator med;

    BtnSearch(ActionListener al, IMediator m) {
        super("Search");
        addActionListener(al);
        med = m;
        med.registerSearch(this);
    }

    @Override
    public void execute() {
        med.search();
    }
}

class BtnBook extends JButton implements Command {
    private IMediator med;

    BtnBook(ActionListener al, IMediator m) {
        super("Book");
        addActionListener(al);
        med = m;
        med.registerBook(this);
    }

    @Override
    public void execute() {
        med.book();
    }
}

class LblDisplay extends JLabel {
    private IMediator med;

    LblDisplay(IMediator m) {
        super("Just start...");
        med = m;
        med.registerDisplay(this);
        setFont(new Font("Arial", Font.BOLD, 24));
        setHorizontalAlignment(JLabel.CENTER);
    }
}

// 5. Client 視窗主類別
public class BookStoreDemo extends JFrame implements ActionListener {
    private IMediator med = new BookStoreMediator();

    public BookStoreDemo() {
        super("BookStore GUI Mediator");
        JPanel p = new JPanel();
        
        // 建立同事元件並傳入共同的中介者
        p.add(new BtnView(this, med));
        p.add(new BtnBook(this, med));
        p.add(new BtnSearch(this, med));
        
        getContentPane().add(new LblDisplay(med), "North");
        getContentPane().add(p, "South");
        
        setSize(350, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 💡 所有按鈕統一在此被攔截，並調用對應 Command 介面
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof Command) {
            Command comd = (Command) ae.getSource();
            comd.execute();
        }
    }

    public static void main(String[] args) {
        new BookStoreDemo();
    }
}
