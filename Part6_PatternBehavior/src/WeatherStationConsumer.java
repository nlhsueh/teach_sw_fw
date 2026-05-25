import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class WeatherStation {
    // 簡單的 List 儲存觀察者
    private List<Consumer<Double>> observers = new ArrayList<>();

    public void addObserver(Consumer<Double> observer) {
        observers.add(observer);
    }

    public void removeObserver(Consumer<Double> observer) {
        observers.remove(observer);
    }

    public void setTemperature(double temperature) {
        System.out.println("Temperature changed to: " + temperature);
        notifyObservers(temperature);
    }

    private void notifyObservers(double temperature) {
        for (Consumer<Double> observer : observers) {
            observer.accept(temperature);
        }
    }
}

public class WeatherStationConsumer {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        // 觀察者 A：簡單印出溫度
        station.addObserver(temp -> {
            System.out.println("Observer A: Temperature is " + temp);
        });

        // 觀察者 B：溫度過高時發出警報
        station.addObserver(temp -> {
            if (temp > 30) {
                System.out.println("Observer B: It's too hot!");
            }
        });

        // 模擬溫度變化
        station.setTemperature(28.5);
        station.setTemperature(35.0);
    }
}
