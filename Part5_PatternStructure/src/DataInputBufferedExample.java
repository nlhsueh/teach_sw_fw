import java.io.*;

public class DataInputBufferedExample {
    public static void main(String[] args) {
        String filename = "data_example.bin";

        // 1. 寫入多種不同型態的資料 (DataOutput + Buffered)
        System.out.println("--- 開始寫入多種型態的資料 ---");
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(filename)))) {
            
            dos.writeUTF("Antigravity"); // 寫入字串
            dos.writeInt(100);            // 寫入整數
            dos.writeDouble(3.1415926);   // 寫入浮點數
            dos.writeBoolean(true);       // 寫入布林值
            
            System.out.println("資料寫入完成！");
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. 展現 DataInput 和 Buffered 的特性
        System.out.println("\n--- 開始讀取資料 ---");
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(filename)))) {
            
            // 【DataInput 的特性】
            // 可以直接讀取特定型態的資料，不需要自己處理 byte 轉換。
            // 注意：讀取的順序必須與寫入的順序完全一致！
            String name = dis.readUTF();
            int score = dis.readInt();
            double pi = dis.readDouble();
            boolean flag = dis.readBoolean();
            
            System.out.println("讀取到的字串 (readUTF): " + name);
            System.out.println("讀取到的整數 (readInt): " + score);
            System.out.println("讀取到的浮點數 (readDouble): " + pi);
            System.out.println("讀取到的布林值 (readBoolean): " + flag);
            
            System.out.println("\n[觀念解析]");
            System.out.println("1. DataInput 特性：提供了強型別的讀取方法（如 readInt, readDouble），保證了資料型態的正確性。");
            System.out.println("2. Buffered 特性：雖然我們分次讀取了不同型態的資料，但 BufferedInputStream 會在底層一次讀取一大塊 byte 到記憶體緩衝區中，避免了頻繁的硬碟 I/O，大大提升效能。");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
