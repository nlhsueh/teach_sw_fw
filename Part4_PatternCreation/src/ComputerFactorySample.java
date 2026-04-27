package abstractfactory.computer;

public class Computer {
    CPU cpu;
    Memory memory;
    MotherBoard mb;

    // 原始做法：在 make() 中直接建立具體零件
    void makePC() {
        cpu = new PC_CPU();
        memory = new PC_Memory();
        mb = new PC_MotherBoard();
    }

    // 原始做法：切換到工作站時需要修改程式碼
    void makeWorkstation() {
        cpu = new WorkstationCPU();
        memory = new WorkstationMemory();
        mb = new WorkstationMotherBoard();
    }

    // 使用 Abstract Factory：由工廠負責生產零件
    void make(ComputerFactory factory) {
        cpu = factory.createCPU();
        memory = factory.createMemory();
        mb = factory.createMotherBoard();
    }
}

interface ComputerFactory {
    CPU createCPU();
    Memory createMemory();
    MotherBoard createMotherBoard();
}

class PCFactory implements ComputerFactory {
    @Override public CPU createCPU() { return new PC_CPU(); }
    @Override public Memory createMemory() { return new PC_Memory(); }
    @Override public MotherBoard createMotherBoard() { return new PC_MotherBoard(); }
}

class WorkstationFactory implements ComputerFactory {
    @Override public CPU createCPU() { return new WorkstationCPU(); }
    @Override public Memory createMemory() { return new WorkstationMemory(); }
    @Override public MotherBoard createMotherBoard() { return new WorkstationMotherBoard(); }
}

// 零件類別
class CPU {}
class Memory {}
class MotherBoard {}
class PC_CPU extends CPU {}
class PC_Memory extends Memory {}
class PC_MotherBoard extends MotherBoard {}
class WorkstationCPU extends CPU {}
class WorkstationMemory extends Memory {}
class WorkstationMotherBoard extends MotherBoard {}
