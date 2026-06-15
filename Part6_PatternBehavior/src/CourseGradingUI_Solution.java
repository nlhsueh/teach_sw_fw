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
interface CourseCommand_Solution {
    void execute();
}

// 💡 抽象中介者介面
interface ICourseMediator_Solution {
    void check();
    void submit();
    
    void registerGradeField(TxtGrade_Solution g);
    void registerCheckButton(BtnCheck_Solution c);
    void registerSubmitButton(BtnSubmit_Solution s);
    void registerStatusLabel(LblStatus_Solution l);
}

// 💡 具體中介者：集中控制所有 UI 元件的啟用狀態、輸入檢查與成績評定
class CourseMediator_Solution implements ICourseMediator_Solution {
    private TxtGrade_Solution txtGrade;
    private BtnCheck_Solution btnCheck;
    private BtnSubmit_Solution btnSubmit;
    private LblStatus_Solution lblStatus;

    @Override
    public void registerGradeField(TxtGrade_Solution g) { this.txtGrade = g; }
    @Override
    public void registerCheckButton(BtnCheck_Solution c) { this.btnCheck = c; }
    @Override
    public void registerSubmitButton(BtnSubmit_Solution s) { this.btnSubmit = s; }
    @Override
    public void registerStatusLabel(LblStatus_Solution l) { this.lblStatus = l; }

    @Override
    public void check() {
        String text = txtGrade.getText().trim();
        try {
            int score = Integer.parseInt(text);
            if (score >= 0 && score <= 100) {
                btnSubmit.setEnabled(true);
                lblStatus.setText("檢查通過！可以提交成績。");
            } else {
                btnSubmit.setEnabled(false);
                lblStatus.setText("錯誤！成績必須在 0 到 100 之間。");
            }
        } catch (NumberFormatException e) {
            btnSubmit.setEnabled(false);
            lblStatus.setText("錯誤！請輸入有效的整數成績。");
        }
    }

    @Override
    public void submit() {
        String text = txtGrade.getText().trim();
        try {
            int score = Integer.parseInt(text);
            String gradeLetter;
            if (score >= 90) gradeLetter = "A";
            else if (score >= 80) gradeLetter = "B";
            else if (score >= 70) gradeLetter = "C";
            else if (score >= 60) gradeLetter = "D";
            else gradeLetter = "F";

            btnSubmit.setEnabled(false);
            btnCheck.setEnabled(false);
            txtGrade.setEditable(false);
            lblStatus.setText("成績已成功送出！最終評定為: " + gradeLetter);
        } catch (NumberFormatException e) {
            lblStatus.setText("提交失敗：無效的輸入分數。");
        }
    }
}

// ==================== 具體同事元件 (Colleagues) 實作 ====================

class TxtGrade_Solution extends JTextField {
    private ICourseMediator_Solution med;

    public TxtGrade_Solution(ICourseMediator_Solution m) {
        super(10);
        this.med = m;
        med.registerGradeField(this); // 向中介者註冊自己
    }
}

class BtnCheck_Solution extends JButton implements CourseCommand_Solution {
    private ICourseMediator_Solution med;

    public BtnCheck_Solution(ActionListener al, ICourseMediator_Solution m) {
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

class BtnSubmit_Solution extends JButton implements CourseCommand_Solution {
    private ICourseMediator_Solution med;

    public BtnSubmit_Solution(ActionListener al, ICourseMediator_Solution m) {
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

class LblStatus_Solution extends JLabel {
    private ICourseMediator_Solution med;

    public LblStatus_Solution(ICourseMediator_Solution m) {
        super("請輸入 0-100 的成績，並點擊 Check 進行檢查。");
        this.med = m;
        med.registerStatusLabel(this);
        setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        setHorizontalAlignment(JLabel.CENTER);
    }
}

// ==================== 測試視窗主程式 ====================

public class CourseGradingUI_Solution extends JFrame implements ActionListener {
    private ICourseMediator_Solution med = new CourseMediator_Solution();

    public CourseGradingUI_Solution() {
        super("University Course Grading System - Solution");
        
        TxtGrade_Solution txtGrade = new TxtGrade_Solution(med);
        BtnCheck_Solution btnCheck = new BtnCheck_Solution(this, med);
        BtnSubmit_Solution btnSubmit = new BtnSubmit_Solution(this, med);
        LblStatus_Solution lblStatus = new LblStatus_Solution(med);
        
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
        if (ae.getSource() instanceof CourseCommand_Solution) {
            CourseCommand_Solution cmd = (CourseCommand_Solution) ae.getSource();
            cmd.execute();
        }
    }

    public static void main(String[] args) {
        new CourseGradingUI_Solution();
    }
}
