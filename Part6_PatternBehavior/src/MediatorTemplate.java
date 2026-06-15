import java.util.Vector;

// 1. 抽象中介者介面
interface IMediator {
    void registerColleague1(Colleague1 c);
    void registerColleague2(Colleague2 c);
    
    // 💡 Colleagues 發生事件時呼叫此方法通知中介者
    void changed(Colleague colleague);
}

// 2. 具體中介者實作
class ConcreteMediator implements IMediator {
    private Colleague1 c1;
    private Colleague2 c2;

    @Override
    public void registerColleague1(Colleague1 c) {
        this.c1 = c;
    }

    @Override
    public void registerColleague2(Colleague2 c) {
        this.c2 = c;
    }

    // 💡 集中控制邏輯：在此決定物件間如何影響
    @Override
    public void changed(Colleague colleague) {
        if (colleague == c1) {
            System.out.println("★ 中介者收到 Colleague1 的狀態變更 -> 通知 Colleague2 執行對應動作");
            c2.receiveAction();
        } else if (colleague == c2) {
            System.out.println("★ 中介者收到 Colleague2 的狀態變更 -> 通知 Colleague1 執行對應動作");
            c1.receiveAction();
        }
    }
}

// 3. 抽象同儕基類
abstract class Colleague {
    protected IMediator mediator; // 持有中介者參考

    public Colleague(IMediator mediator) {
        this.mediator = mediator;
    }
}

// 4. 具體同儕 1
class Colleague1 extends Colleague {
    public Colleague1(IMediator mediator) {
        super(mediator);
        mediator.registerColleague1(this); // 主動向中介者註冊
    }

    // 自身發生的業務行為
    public void triggerEvent() {
        System.out.println("Colleague1: 我被觸發了！通知中介者。");
        mediator.changed(this);
    }

    // 由中介者代為調用的聯動行為
    public void receiveAction() {
        System.out.println("Colleague1: 收到中介者通知，更新狀態。");
    }
}

// 5. 具體同儕 2
class Colleague2 extends Colleague {
    public Colleague2(IMediator mediator) {
        super(mediator);
        mediator.registerColleague2(this);
    }

    public void triggerEvent() {
        System.out.println("Colleague2: 我被觸發了！通知中介者。");
        mediator.changed(this);
    }

    public void receiveAction() {
        System.out.println("Colleague2: 收到中介者通知，更新狀態。");
    }
}

// 6. 測試主程式
public class MediatorTemplate {
    public static void main(String[] args) {
        IMediator med = new ConcreteMediator();

        Colleague1 c1 = new Colleague1(med);
        Colleague2 c2 = new Colleague2(med);

        // c1 與 c2 互不知道對方，但動作依然產生了聯動
        c1.triggerEvent();
        System.out.println("-------------------------------------");
        c2.triggerEvent();
    }
}
