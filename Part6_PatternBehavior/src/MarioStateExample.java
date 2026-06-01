// 抽象狀態介面
interface MarioState {
    void obtainMushroom(MarioContext context);
    void obtainFireFlower(MarioContext context);
    void meetEnemy(MarioContext context);
    String getName();
}

// 小瑪利歐狀態
class SmallMarioState implements MarioState {
    @Override
    public void obtainMushroom(MarioContext context) {
        context.setState(new SuperMarioState());
        System.out.println("★ 瑪利歐吃了香菇，變身為 [超級大瑪利歐]！");
    }

    @Override
    public void obtainFireFlower(MarioContext context) {
        context.setState(new FireMarioState());
        System.out.println("★ 瑪利歐吃了火焰花，變身為 [火焰瑪利歐]！");
    }

    @Override
    public void meetEnemy(MarioContext context) {
        System.out.println("💥 小瑪利歐撞到怪物！ Game Over... ☠️");
    }

    @Override
    public String getName() { return "Small Mario"; }
}

// 超級大瑪利歐狀態
class SuperMarioState implements MarioState {
    @Override
    public void obtainMushroom(MarioContext context) {
        System.out.println("瑪利歐已經是超級狀態，沒有額外變化。");
    }

    @Override
    public void obtainFireFlower(MarioContext context) {
        context.setState(new FireMarioState());
        System.out.println("★ 超級瑪利歐吃了火焰花，升級為 [火焰瑪利歐]！");
    }

    @Override
    public void meetEnemy(MarioContext context) {
        context.setState(new SmallMarioState());
        System.out.println("💥 超級瑪利歐撞到怪物！退化為 [小瑪利歐]！");
    }

    @Override
    public String getName() { return "Super Mario"; }
}

// 火焰瑪利歐狀態
class FireMarioState implements MarioState {
    @Override
    public void obtainMushroom(MarioContext context) {
        System.out.println("瑪利歐已經有火焰能力，香菇沒有效果。");
    }

    @Override
    public void obtainFireFlower(MarioContext context) {
        System.out.println("瑪利歐已經有火焰能力。");
    }

    @Override
    public void meetEnemy(MarioContext context) {
        context.setState(new SmallMarioState());
        System.out.println("💥 火焰瑪利歐撞到怪物！失去所有能力退化為 [小瑪利歐]！");
    }

    @Override
    public String getName() { return "Fire Mario"; }
}

// MarioContext (環境物件)
class MarioContext {
    private MarioState state;

    public MarioContext() {
        // 預設初始為小瑪利歐
        this.state = new SmallMarioState();
    }

    public void setState(MarioState state) {
        this.state = state;
    }

    public void obtainMushroom() {
        state.obtainMushroom(this);
    }

    public void obtainFireFlower() {
        state.obtainFireFlower(this);
    }

    public void meetEnemy() {
        state.meetEnemy(this);
    }

    public String getStateName() {
        return state.getName();
    }
}

// 遊戲測試主程式
public class MarioStateExample {
    public static void main(String[] args) {
        MarioContext mario = new MarioContext();
        System.out.println("當前狀態：" + mario.getStateName()); // Small Mario
        
        mario.obtainMushroom();   // 吃香菇
        mario.obtainFireFlower(); // 吃火焰花
        System.out.println("當前狀態：" + mario.getStateName()); // Fire Mario
        
        mario.meetEnemy();        // 碰怪物
        System.out.println("當前狀態：" + mario.getStateName()); // Small Mario
    }
}
