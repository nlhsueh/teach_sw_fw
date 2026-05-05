import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class WeatherStation {
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

        // 加入觀察者 1：印出溫度
        station.addObserver(temp -> System.out.println("Observer A: Temperature is " + temp));

        // 加入觀察者 2：根據溫度做反應
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
