import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// 💡 共同的同事行為介面（Command 樣式）
interface CourseCommand {
    void execute();
}

// 💡 抽象中介者介面
interface ICourseMediator {
    void check();
    void submit();
    
    void registerGradeField(TxtGrade g);
    void registerCheckButton(BtnCheck c);
    void registerSubmitButton(BtnSubmit s);
    void registerStatusLabel(LblStatus l);
}

// 💡 具體中介者：集中控制所有 UI 元件的啟用狀態、輸入檢查與成績評定
class CourseMediator implements ICourseMediator {
    private TxtGrade txtGrade;
    private BtnCheck btnCheck;
    private BtnSubmit btnSubmit;
    private LblStatus lblStatus;

    @Override
    public void registerGradeField(TxtGrade g) { this.txtGrade = g; }
    @Override
    public void registerCheckButton(BtnCheck c) { this.btnCheck = c; }
    @Override
    public void registerSubmitButton(BtnSubmit s) { this.btnSubmit = s; }
    @Override
    public void registerStatusLabel(LblStatus l) { this.lblStatus = l; }

    @Override
    public void check() {
        // [TODO: 1. 實作成績檢查邏輯]
        // 提示：取得 txtGrade 的文字內容，去除空白後嘗試解析為整數
        // 判斷該整數是否落在 0 至 100 的合理範圍內。
        // 若合格：啟用 btnSubmit 按鈕，更新 lblStatus 為 "檢查通過！可以提交成績。"
        // 若不合格或解析失敗：停用 btnSubmit 按鈕，更新 lblStatus 為對應錯誤提示。
    }

    @Override
    public void submit() {
        // [TODO: 2. 實作成績提交與等第評定邏輯]
        // 提示：解析分數後，依據以下評估規則換算成學術等第 (Grade Letter)
        // >=90 為 A, >=80 為 B, >=70 為 C, >=60 為 D, 60 以下為 F。
        // 換算完成後，將 btnSubmit、btnCheck 按鈕皆停用 (setEnabled(false))，
        // 並將 txtGrade 文字欄位設為不可編輯 (setEditable(false))，避免之後再次更改成績。
        // 最後更新 lblStatus 為 "成績已成功送出！最終評定為: [等第]"
    }
}

// ==================== 具體同事元件 (Colleagues) 實作 ====================

class TxtGrade extends JTextField {
    private ICourseMediator med;

    public TxtGrade(ICourseMediator m) {
        super(10);
        this.med = m;
        med.registerGradeField(this); // 向中介者註冊自己
    }
}

class BtnCheck extends JButton implements CourseCommand {
    private ICourseMediator med;

    public BtnCheck(ActionListener al, ICourseMediator m) {
        super("Check");
        addActionListener(al);
        this.med = m;
        med.registerCheckButton(this);
    }

    @Override
    public void execute() {
        med.check(); // 委託中介者執行檢查行為
    }
}

class BtnSubmit extends JButton implements CourseCommand {
    private ICourseMediator med;

    public BtnSubmit(ActionListener al, ICourseMediator m) {
        super("Submit");
        addActionListener(al);
        this.med = m;
        med.registerSubmitButton(this);
    }

    @Override
    public void execute() {
        med.submit(); // 委託中介者執行提交行為
    }
}

class LblStatus extends JLabel {
    private ICourseMediator med;

    public LblStatus(ICourseMediator m) {
        super("請輸入 0-100 的成績，並點擊 Check 進行檢查。");
        this.med = m;
        med.registerStatusLabel(this);
        setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        setHorizontalAlignment(JLabel.CENTER);
    }
}

// ==================== 測試視窗主程式 ====================

public class CourseGradingUI extends JFrame implements ActionListener {
    private ICourseMediator med = new CourseMediator();

    public CourseGradingUI() {
        super("University Course Grading System");
        
        TxtGrade txtGrade = new TxtGrade(med);
        BtnCheck btnCheck = new BtnCheck(this, med);
        BtnSubmit btnSubmit = new BtnSubmit(this, med);
        LblStatus lblStatus = new LblStatus(med);
        
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("輸入成績: "));
        inputPanel.add(txtGrade);
        inputPanel.add(btnCheck);
        inputPanel.add(btnSubmit);
        
        // 初始狀態
        btnSubmit.setEnabled(false);
        
        getContentPane().add(lblStatus, BorderLayout.NORTH);
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        
        setSize(480, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof CourseCommand) {
            CourseCommand cmd = (CourseCommand) ae.getSource();
            cmd.execute();
        }
    }

    public static void main(String[] args) {
        new CourseGradingUI();
    }
}
