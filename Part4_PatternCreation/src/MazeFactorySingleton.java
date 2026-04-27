package singleton.maze;

public abstract class MazeFactory {
    // 宣告成 protected 這樣子類別才看得到
    protected static MazeFactory uniqueInstance = null;

    // 藏起來不讓外界的呼叫。但因為有子類別，不能宣告為 protected
    protected MazeFactory() {}

    // Return a reference to the single instance.
    public static MazeFactory instance() {
        return uniqueInstance;
    }
}

class EnchantedMazeFactory extends MazeFactory {
    // 參考到父類別的 uniqueInstance
    public static MazeFactory instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new EnchantedMazeFactory();
        }
        return uniqueInstance;
    }
    private EnchantedMazeFactory() {}
}

class AgentMazeFactory extends MazeFactory {
    // 同樣的參考到父類別的 uniqueInstance
    public static MazeFactory instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AgentMazeFactory();
        }
        return uniqueInstance;
    }
    private AgentMazeFactory() {}
}
