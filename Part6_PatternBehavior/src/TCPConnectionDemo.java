// 抽象 TCPState
abstract class TCPState {
    public abstract void open(TCPConnection c);
    public abstract void sync(TCPConnection c);
    public abstract void ack(TCPConnection c);
}

// Closed 狀態
class TCPClosed extends TCPState {
    @Override
    public void open(TCPConnection c) {
        System.out.println("Opening connection -> Entering Listen State.");
        c.setState(new TCPListen());
    }
    @Override
    public void sync(TCPConnection c) {
        System.out.println("Error: Cannot sync in Closed state.");
    }
    @Override
    public void ack(TCPConnection c) {
        System.out.println("Error: Cannot ack in Closed state.");
    }
}

// Listen 狀態
class TCPListen extends TCPState {
    @Override
    public void open(TCPConnection c) {
        System.out.println("Already open and listening.");
    }
    @Override
    public void sync(TCPConnection c) {
        System.out.println("Received SYN packet -> Entering Sync-Received State.");
        c.setState(new TCPSyncReceived());
    }
    @Override
    public void ack(TCPConnection c) {
        System.out.println("Error: Awaiting SYN before ACK.");
    }
}

// SyncReceived 狀態
class TCPSyncReceived extends TCPState {
    @Override
    public void open(TCPConnection c) {
        System.out.println("Connection already open.");
    }
    @Override
    public void sync(TCPConnection c) {
        System.out.println("Already received SYN. Waiting for ACK.");
    }
    @Override
    public void ack(TCPConnection c) {
        System.out.println("ACK received -> Connection Established!");
        c.setState(new TCPEstablished());
    }
}

// Established 狀態
class TCPEstablished extends TCPState {
    @Override
    public void open(TCPConnection c) {
        System.out.println("Connection already established.");
    }
    @Override
    public void sync(TCPConnection c) {
        System.out.println("Re-sync ignored. Connection is active.");
    }
    @Override
    public void ack(TCPConnection c) {
        System.out.println("ACK processed in active session.");
    }
}

// TCPConnection (環境情境類別)
class TCPConnection {
    private TCPState state;

    public TCPConnection() {
        this.state = new TCPClosed();
    }

    public void setState(TCPState state) {
        this.state = state;
    }

    public void open() {
        state.open(this);
    }

    public void sync() {
        state.sync(this);
    }

    public void ack() {
        state.ack(this);
    }
}

// 測試主程式
public class TCPConnectionDemo {
    public static void main(String[] args) {
        TCPConnection conn = new TCPConnection();
        
        System.out.println("--- 測試投送事件 ---");
        conn.sync();  // 錯誤事件
        conn.open();  // Closed -> Listen
        conn.sync();  // Listen -> SyncReceived
        conn.ack();   // SyncReceived -> Established
        
        System.out.println("\n--- 連線建立後再次投送事件 ---");
        conn.open();
        conn.sync();
        conn.ack();
    }
}
