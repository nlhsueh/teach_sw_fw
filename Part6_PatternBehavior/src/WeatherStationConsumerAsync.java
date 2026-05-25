import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

class WeatherStationAsync {
    // 使用 CopyOnWriteArrayList 確保執行緒安全，適合「讀多寫少」的 Observer 註冊與通知場景
    private List<Consumer<Double>> observers = new CopyOnWriteArrayList<>();

    public void addObserver(Consumer<Double> observer) {
        observers.add(observer);
    }

    public void removeObserver(Consumer<Double> observer) {
        observers.remove(observer);
    }

    public void setTemperature(double temperature) {
        System.out.println("\n[Thread: " + Thread.currentThread().getName() + "] Temperature changed to: " + temperature);
        notifyObservers(temperature);
    }

    private void notifyObservers(double temperature) {
        for (Consumer<Double> observer : observers) {
            // 使用 CompletableFuture.runAsync 進行非同步通知，不阻塞目前的發送者執行緒
            CompletableFuture.runAsync(() -> observer.accept(temperature));
        }
    }
}

public class WeatherStationConsumerAsync {
    public static void main(String[] args) throws InterruptedException {
        WeatherStationAsync station = new WeatherStationAsync();

        // 觀察者 A：快速處理，印出溫度與目前執行的執行緒名稱
        station.addObserver(temp -> {
            System.out.println("[Thread: " + Thread.currentThread().getName() + "] Observer A (Quick): Temperature is " + temp);
        });

        // 觀察者 B：慢速處理，模擬需要花時間處理的工作（例如 I/O、資料庫寫入或複雜計算）
        station.addObserver(temp -> {
            System.out.println("[Thread: " + Thread.currentThread().getName() + "] Observer B (Slow) started processing for " + temp + "°C...");
            try {
                Thread.sleep(1000); // 模擬耗時操作 1 秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (temp > 30) {
                System.out.println("[Thread: " + Thread.currentThread().getName() + "] Observer B (Slow): It's too hot! (" + temp + "°C)");
            }
            System.out.println("[Thread: " + Thread.currentThread().getName() + "] Observer B (Slow) finished processing for " + temp + "°C.");
        });

        // 模擬溫度變化
        // 您會發現，當 setTemperature 被呼叫時，主執行緒 (main) 不會被 Observer B 的 Thread.sleep 阻塞，
        // 兩個通知會立即被非同步分派，主執行緒也能立刻繼續往下執行！
        station.setTemperature(28.5);
        station.setTemperature(35.0);

        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Main thread continues immediately without waiting for observers!");

        // 讓主執行緒等待足夠的時間，以便非同步的線程池完成所有 Observer 的輸出，然後再結束程式
        Thread.sleep(2500);
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Main thread finished.");
    }
}
