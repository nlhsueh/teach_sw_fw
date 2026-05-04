import java.io.*;

public class InputStreamDecoratorExample {
    public static void main(String[] args) {
        try {
            // 1. Concrete Component: FileInputStream (reads bytes from file)
            InputStream fis = new FileInputStream("test.txt");

            // 2. Decorator 1: BufferedInputStream (adds buffering)
            InputStream bis = new BufferedInputStream(fis);

            // 3. Decorator 2: DataInputStream (adds methods to read primitives)
            DataInputStream dis = new DataInputStream(bis);

            // Now dis has all the behaviors combined
            // dis.readInt();
            // dis.readDouble();
            
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Conceptual Structure:
// Component: java.io.InputStream
// ConcreteComponent: FileInputStream, ByteArrayInputStream...
// Decorator: FilterInputStream
// ConcreteDecorator: BufferedInputStream, DataInputStream, LineNumberInputStream...
