package swfw.ch11.chess.Panel;

import swfw.ch11.chess.MainFrame;

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {
    public RulesPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton btnStandard = new JButton("標準");
        JButton btnBlind = new JButton("象棋");
        buttonPanel.add(btnStandard);
        buttonPanel.add(btnBlind);
        add(buttonPanel, BorderLayout.NORTH);

        JTextArea ruleArea = new JTextArea(20, 50);
        ruleArea.setLineWrap(true);
        ruleArea.setWrapStyleWord(true);
        ruleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ruleArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel backPanel = new JPanel();
        JButton backButton = new JButton("返回");
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);

        btnStandard.addActionListener(e -> {
            ruleArea.setText("""
                棋的基本配備是棋盤和棋子。棋盤有十條橫線，九條直線，組成了九十個點，棋子就放在點上。
                棋盤中間有楚河漢界隔開。棋子有三十二顆，分紅黑兩方，共有帥（將）、仕（士）、相（象）、
                俥（車）、傌（馬）、炮（包）、兵（卒）七兵種。除了帥一個，兵五個外，其餘均為一雙。
                弈棋者先吃到對方的將帥者勝，亦有和局的情況在；
                紅先黑後，一方一回合只能移動一棋子，一次一步。接下來介紹各子及其走法。
            """);
        });

        btnBlind.addActionListener(e -> {
            ruleArea.setText("""
                暗棋的棋子是放在格上，共4*8格，剛好放下所有棋子。開始時，將所有棋的背面向上，令人看不到棋子是甚麼，
                然後洗亂棋子，再放到棋盤上。未翻開的棋子稱為暗棋，翻開了的就叫明棋。
                雙方輪流行走，每次可選擇翻開暗棋、移動自己的明棋或吃對手的明棋。
                棋子除了炮都只能動一格，炮是唯一可以隔一格跳吃的棋子。

                吃棋時須考慮順序。除了帥和將不能吃卒和兵，卒和兵可吃帥和將這個特例之外，
                較大的棋子可吃較小的棋子，較小的不能吃較大的，同級的可吃同級的。
                當某一方的棋子全部死去，另一方就得勝。以下次序以紅子的名稱表示，黑子等同。

                棋子大小階級順序：帥 > 仕 > 相 > 俥 > 傌 > 炮 > 兵

                特殊規則：
                - 炮需隔過一隻棋子才可吃子，其餘皆不可隔子吃子。
                - 炮跳吃子可不論大小，可隔一子吃子，方向不限。
                - 兵吃黑將、卒吃紅帥。
            """);
        });

        backButton.addActionListener(e -> frame.showPanel("MainMenuPanel"));
    }
}

