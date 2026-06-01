// 1. 抽象中介者介面
interface ISmartHomeMediator {
    // 💡 裝置狀態改變或偵測到事件時，呼叫此方法向中介者回報
    void coordinate(SmartDevice device, String event);
}

// 2. 抽象同儕裝置類別
abstract class SmartDevice {
    protected ISmartHomeMediator mediator;

    public SmartDevice(ISmartHomeMediator mediator) {
        this.mediator = mediator;
    }
}

// 3. 具體同儕類別：溫度感測器
class TemperatureSensor extends SmartDevice {
    private float lastTemp;

    public TemperatureSensor(ISmartHomeMediator mediator) {
        super(mediator);
    }

    public void checkTemperature(float temp) {
        this.lastTemp = temp;
        System.out.println("🌡️ 溫度計感測到當前溫度為: " + temp + "°C");
        if (temp >= 30.0f) {
            mediator.coordinate(this, "HIGH_TEMP");
        } else if (temp <= 22.0f) {
            mediator.coordinate(this, "LOW_TEMP");
        }
    }
}

// 4. 具體同儕類別：冷氣機
class AirConditioner extends SmartDevice {
    public AirConditioner(ISmartHomeMediator mediator) {
        super(mediator);
    }

    public void turnOn() {
        System.out.println("❄️ 冷氣機：自動開啟，並設定為 [強風降溫模式]");
    }

    public void turnOff() {
        System.out.println("🔌 冷氣機：溫度適宜，自動關閉節能");
    }
}

// 5. 智慧窗簾
class SmartCurtain extends SmartDevice {
    public SmartCurtain(ISmartHomeMediator mediator) {
        super(mediator);
    }

    public void close() {
        System.out.println("🪟 智慧窗簾：自動拉上 [遮擋強烈陽光]");
        mediator.coordinate(this, "CURTAIN_CLOSED");
    }

    public void open() {
        System.out.println("🪟 智慧窗簾：自動拉開 [引入自然光線]");
        mediator.coordinate(this, "CURTAIN_OPENED");
    }
}

// 6. 具體同儕類別：智慧燈泡
class Light extends SmartDevice {
    public Light(ISmartHomeMediator mediator) {
        super(mediator);
    }

    public void turnOn() {
        System.out.println("💡 智慧燈泡：偵測到光線變暗，自動開啟照明");
    }

    public void turnOff() {
        System.out.println("💡 智慧燈泡：自然光充足，自動關閉以省電");
    }
}

// 7. 具體中介者實作：集中調度所有智慧裝置的自動化聯動邏輯
class SmartHomeMediatorImpl implements ISmartHomeMediator {
    private TemperatureSensor sensor;
    private AirConditioner ac;
    private SmartCurtain curtain;
    private Light light;

    // 設定所管理的所有智慧裝置
    public void setDevices(TemperatureSensor sensor, AirConditioner ac, SmartCurtain curtain, Light light) {
        this.sensor = sensor;
        this.ac = ac;
        this.curtain = curtain;
        this.light = light;
    }

    @Override
    public void coordinate(SmartDevice device, String event) {
        System.out.println("»» [中介者收到事件] 來自 " + device.getClass().getSimpleName() + " 觸發了 \"" + event + "\"");
        
        switch (event) {
            case "HIGH_TEMP":
                // 1. 開啟冷氣
                ac.turnOn();
                // 2. 自動關閉窗簾遮陽
                curtain.close();
                break;
                
            case "LOW_TEMP":
                // 1. 關閉冷氣
                ac.turnOff();
                break;
                
            case "CURTAIN_CLOSED":
                // 窗簾拉上了，自動開啟電燈補光
                light.turnOn();
                break;
                
            case "CURTAIN_OPENED":
                // 窗簾拉開了，自動關閉電燈節能
                light.turnOff();
                break;
                
            default:
                System.out.println("未定義的聯動事件。");
        }
    }
}

// 8. 測試主程式
public class SmartHomeDemo {
    public static void main(String[] args) {
        // 建立中介者
        SmartHomeMediatorImpl mediator = new SmartHomeMediatorImpl();

        // 建立同儕裝置並傳入中介者
        TemperatureSensor sensor = new TemperatureSensor(mediator);
        AirConditioner ac = new AirConditioner(mediator);
        SmartCurtain curtain = new SmartCurtain(mediator);
        Light light = new Light(mediator);

        // 向中介者註冊這些裝置
        mediator.setDevices(sensor, ac, curtain, light);

        System.out.println("=== 模擬情境一：夏日午後，室溫飆高至 33°C ===");
        sensor.checkTemperature(33.0f);

        System.out.println("\n=== 模擬情境二：傍晚天氣變涼，室溫降至 21°C ===");
        sensor.checkTemperature(21.0f);
        
        System.out.println("\n=== 模擬情境三：手動開啟窗簾引入午後自然光 ===");
        curtain.open();
    }
}
