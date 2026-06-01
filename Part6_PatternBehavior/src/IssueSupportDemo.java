// 1. 問題類型與難度列舉
enum IssueType {
    TECHNICAL, MANAGEMENT
}

enum Severity {
    EASY, MEDIUM, HARD, CRITICAL
}

// 2. 問題 Request 類別
class Issue {
    private int id;
    private IssueType type;
    private Severity severity;
    private String description;

    public Issue(int id, IssueType type, Severity severity, String description) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.description = description;
    }

    public int getId() { return id; }
    public IssueType getType() { return type; }
    public Severity getSeverity() { return severity; }
    public String getDescription() { return description; }
}

// 3. 抽象問題處理者 (Handler 介面)
interface IssueHandler {
    void handleTechnical(Issue issue);
    void handleManagement(Issue issue);
}

// 4. 具體處理者類別
class Programmer implements IssueHandler {
    private IssueHandler techSuccessor;
    private IssueHandler mgmtSuccessor;

    public Programmer(IssueHandler techSuccessor, IssueHandler mgmtSuccessor) {
        this.techSuccessor = techSuccessor;
        this.mgmtSuccessor = mgmtSuccessor;
    }

    @Override
    public void handleTechnical(Issue issue) {
        if (issue.getSeverity() == Severity.EASY) {
            System.out.println("🔧 [技術鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 Programmer 解決。");
        } else if (techSuccessor != null) {
            techSuccessor.handleTechnical(issue);
        } else {
            System.out.println("❌ [技術鏈] 無法解決問題 #" + issue.getId() + "。已達技術鏈末端。");
        }
    }

    @Override
    public void handleManagement(Issue issue) {
        if (issue.getSeverity() == Severity.EASY) {
            System.out.println("💼 [管理鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 Programmer 解決。");
        } else if (mgmtSuccessor != null) {
            mgmtSuccessor.handleManagement(issue);
        } else {
            System.out.println("❌ [管理鏈] 無法解決問題 #" + issue.getId() + "。已達管理鏈末端。");
        }
    }
}

class Designer implements IssueHandler {
    private IssueHandler techSuccessor;
    private IssueHandler mgmtSuccessor;

    public Designer(IssueHandler techSuccessor, IssueHandler mgmtSuccessor) {
        this.techSuccessor = techSuccessor;
        this.mgmtSuccessor = mgmtSuccessor;
    }

    @Override
    public void handleTechnical(Issue issue) {
        if (issue.getSeverity() == Severity.MEDIUM) {
            System.out.println("🔧 [技術鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 Designer 解決。");
        } else if (techSuccessor != null) {
            techSuccessor.handleTechnical(issue);
        } else {
            System.out.println("❌ [技術鏈] 無法解決問題 #" + issue.getId() + "。已達技術鏈末端。");
        }
    }

    @Override
    public void handleManagement(Issue issue) {
        if (mgmtSuccessor != null) {
            mgmtSuccessor.handleManagement(issue);
        } else {
            System.out.println("❌ [管理鏈] 無法解決問題 #" + issue.getId() + "。已達管理鏈末端。");
        }
    }
}

class Architect implements IssueHandler {
    public Architect() {
    }

    @Override
    public void handleTechnical(Issue issue) {
        if (issue.getSeverity() == Severity.HARD || issue.getSeverity() == Severity.CRITICAL) {
            System.out.println("🔧 [技術鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 Architect 解決。");
        } else {
            System.out.println("❌ [技術鏈] 無法解決問題 #" + issue.getId() + "。已達技術鏈末端。");
        }
    }

    @Override
    public void handleManagement(Issue issue) {
        System.out.println("❌ [管理鏈] Architect 不處理管理類別問題。");
    }
}

class Analyzer implements IssueHandler {
    private IssueHandler techSuccessor;
    private IssueHandler mgmtSuccessor;

    public Analyzer(IssueHandler techSuccessor, IssueHandler mgmtSuccessor) {
        this.techSuccessor = techSuccessor;
        this.mgmtSuccessor = mgmtSuccessor;
    }

    @Override
    public void handleTechnical(Issue issue) {
        if (techSuccessor != null) {
            techSuccessor.handleTechnical(issue);
        } else {
            System.out.println("❌ [技術鏈] 無法解決問題 #" + issue.getId() + "。已達技術鏈末端。");
        }
    }

    @Override
    public void handleManagement(Issue issue) {
        if (issue.getSeverity() == Severity.MEDIUM) {
            System.out.println("💼 [管理鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 Analyzer 解決。");
        } else if (mgmtSuccessor != null) {
            mgmtSuccessor.handleManagement(issue);
        } else {
            System.out.println("❌ [管理鏈] 無法解決問題 #" + issue.getId() + "。已達管理鏈末端。");
        }
    }
}

class Manager implements IssueHandler {
    private IssueHandler techSuccessor;
    private IssueHandler mgmtSuccessor;

    public Manager(IssueHandler techSuccessor, IssueHandler mgmtSuccessor) {
        this.techSuccessor = techSuccessor;
        this.mgmtSuccessor = mgmtSuccessor;
    }

    @Override
    public void handleTechnical(Issue issue) {
        if (techSuccessor != null) {
            techSuccessor.handleTechnical(issue);
        } else {
            System.out.println("❌ [技術鏈] 無法解決問題 #" + issue.getId() + "。已達技術鏈末端。");
        }
    }

    @Override
    public void handleManagement(Issue issue) {
        if (issue.getSeverity() == Severity.HARD) {
            System.out.println("💼 [管理鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 Manager 解決。");
        } else if (mgmtSuccessor != null) {
            mgmtSuccessor.handleManagement(issue);
        } else {
            System.out.println("❌ [管理鏈] 無法解決問題 #" + issue.getId() + "。已達管理鏈末端。");
        }
    }
}

class CEO implements IssueHandler {
    public CEO() {
    }

    @Override
    public void handleTechnical(Issue issue) {
        System.out.println("❌ [技術鏈] CEO 不處理技術類別問題。");
    }

    @Override
    public void handleManagement(Issue issue) {
        if (issue.getSeverity() == Severity.CRITICAL) {
            System.out.println("💼 [管理鏈] 問題 #" + issue.getId() + " (" + issue.getDescription() + ") 已由 CEO 解決。");
        } else {
            System.out.println("❌ [管理鏈] 無法解決問題 #" + issue.getId() + "。已達管理鏈末端。");
        }
    }
}

// 5. 測試主程式
public class IssueSupportDemo {
    public static void main(String[] args) {
        // 建立技術鏈：Programmer -> Designer -> Architect
        Architect architect = new Architect();
        Designer designer = new Designer(architect, null);
        
        // 建立管理鏈：Programmer -> Analyzer -> Manager -> CEO
        CEO ceo = new CEO();
        Manager manager = new Manager(null, ceo);
        Analyzer analyzer = new Analyzer(null, manager);
        
        // 最前端 Programmer 同時持有技術鏈與管理鏈的後繼者
        Programmer programmer = new Programmer(designer, analyzer);

        System.out.println("=== 專案問題派工與處理流程模擬 ===");

        // 測試一：簡單的技術問題 (由 Programmer 解決)
        Issue issue1 = new Issue(1, IssueType.TECHNICAL, Severity.EASY, "拼字錯誤");
        programmer.handleTechnical(issue1);

        // 測試二：中等的技術問題 (應由 Designer 解決)
        Issue issue2 = new Issue(2, IssueType.TECHNICAL, Severity.MEDIUM, "首頁跑版問題");
        programmer.handleTechnical(issue2);

        // 測試三：困難的技術問題 (應由 Architect 解決)
        Issue issue3 = new Issue(3, IssueType.TECHNICAL, Severity.HARD, "高併發資料庫鎖死");
        programmer.handleTechnical(issue3);

        System.out.println("----------------------------------------");

        // 測試四：簡單的管理問題 (由 Programmer 解決)
        Issue issue4 = new Issue(4, IssueType.MANAGEMENT, Severity.EASY, "申請下午請假 2 小時");
        programmer.handleManagement(issue4);

        // 測試五：中等管理問題 (應由 Analyzer 解決)
        Issue issue5 = new Issue(5, IssueType.MANAGEMENT, Severity.MEDIUM, "專案時程評估分析");
        programmer.handleManagement(issue5);

        // 測試六：困難管理問題 (應由 Manager 解決)
        Issue issue6 = new Issue(6, IssueType.MANAGEMENT, Severity.HARD, "部門預算超支審核");
        programmer.handleManagement(issue6);

        // 測試七：極度嚴重管理問題 (應由 CEO 解決)
        Issue issue7 = new Issue(7, IssueType.MANAGEMENT, Severity.CRITICAL, "公司被競爭對手惡意併購");
        programmer.handleManagement(issue7);
    }
}
