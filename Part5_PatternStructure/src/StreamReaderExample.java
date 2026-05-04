import java.io.*;

// Target interface: Reader and Writer (for characters)
// Adaptee: InputStream and OutputStream (for bytes)

public class StreamReaderExample {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("input.txt");
             // InputStreamReader is the Adapter
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(isr);
             
             FileOutputStream fos = new FileOutputStream("output.txt");
             // OutputStreamWriter is the Adapter
             OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
             BufferedWriter bw = new BufferedWriter(osw)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Reading: " + line);
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Write complete!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Internal definitions (conceptual)
/*
public abstract class Reader implements Readable, Closeable {
    public abstract int read(char cbuf[], int off, int len) throws IOException;
}

public abstract class InputStream implements Closeable {
    public abstract int read() throws IOException;
}

public class InputStreamReader extends Reader {
    public InputStreamReader(InputStream in) {
        // ...
    }
    public int read(char cbuf[], int off, int len) throws IOException {
        // internal byte-to-char conversion logic
        return ...;
    }
}
*/
