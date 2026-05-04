import java.awt.event.*;

public class WindowAdaptorExample {
    public static void main(String arg[]) {
        // Mock application for demo
        Object application = new Object(); // Assume this is a JFrame
        
        // Using WindowAdapter (the Adapter)
        /*
        application.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        */
    }
}

// The WindowListener interface has many methods
interface WindowListener {
    public void windowActivated(WindowEvent e);
    public void windowClosed(WindowEvent e);
    public void windowClosing(WindowEvent e);
    public void windowDeactivated(WindowEvent e);
    public void windowDeiconified(WindowEvent e);
    public void windowIconified(WindowEvent e);
    public void windowOpened(WindowEvent e);
}

// WindowAdapter provides empty implementations for all methods
// This allows subclasses to only override the methods they care about.
abstract class WindowAdapter implements WindowListener {
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
}
