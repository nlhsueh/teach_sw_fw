import java.io.*;

public class InputStreamDecoratorExample {
    public static void main(String[] args) {
        String filename = "test.txt";

        // =================================================================
        // Part 1: 基礎功能展示（寫入與讀取特定字串）
        // =================================================================
        System.out.println("=== Part 1: 基礎功能展示 (展示 Decorator 鏈) ===");

        // 寫入檔案
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(filename)))) {

            dos.writeUTF("I love design pattern");
            System.out.println("成功寫入字串: 'I love design pattern'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 讀取檔案
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(filename)))) {

            String content = dis.readUTF();
            System.out.println("從檔案讀取到的內容: " + content);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // =================================================================
        // Part 2: 效能對比（真實展現 Buffered 的特性）
        // =================================================================
        System.out.println("\n=== Part 2: 效能對比 (真實展現 Buffered 的特性) ===");

        int loopCount = 100000; // 寫入 10 萬筆資料
        String fileNoBuffer = "perf_no_buffer.bin";
        String fileWithBuffer = "perf_with_buffer.bin";

        // 1. 測試「無」Buffered 的寫入速度
        System.out.println("開始測試：無 Buffered 寫入 " + loopCount + " 筆整數...");
        long startTime = System.currentTimeMillis();
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(fileNoBuffer))) {

            for (int i = 0; i < loopCount; i++) {
                dos.writeInt(i); // 每次寫入 4 bytes
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long timeNoBuffer = endTime - startTime;
        System.out.println("➔ 無 Buffered 耗時: " + timeNoBuffer + " ms");

        // 2. 測試「有」Buffered 的寫入速度
        System.out.println("\n開始測試：有 Buffered 寫入 " + loopCount + " 筆整數...");
        startTime = System.currentTimeMillis();
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(fileWithBuffer)))) {

            for (int i = 0; i < loopCount; i++) {
                dos.writeInt(i); // 資料會先填入 8KB 緩衝區
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        long timeWithBuffer = endTime - startTime;
        System.out.println("➔ 有 Buffered 耗時: " + timeWithBuffer + " ms");

        // 3. 輸出結果分析
        System.out.println("\n[結果分析]");
        System.out.println("使用 Buffered 後，速度提升了約 " + (double) timeNoBuffer / timeWithBuffer + " 倍！");
        System.out.println("這證明了 Buffered 透過減少硬碟 I/O 次數，能帶來巨大的效能優勢。");

        // 清理測試產生的檔案
        new File(fileNoBuffer).delete();
        new File(fileWithBuffer).delete();
        new File(filename).delete();
    }
}
