package swfw.ch02.interfaces;

public class NNMultiplication {
    public static void main(String[] args) {
        System.out.println("--- Integers ---");
        // 整數對整數
        NNEntity[] xListA = { new NNInteger(2), new NNInteger(3), new NNInteger(5), new NNInteger(6), new NNInteger(10) };
        NNEntity[] yListA = { new NNInteger(7), new NNInteger(2), 	new NNInteger(3), new NNInteger(4), new NNInteger(8) };
        TableDisplayer.multiplyAndShow(xListA, yListA);

        System.out.println("--- Strings ---");
        // 字串對字串        
        NNEntity[] xListB = { new NNString("Q"), new NNString("D"), new NNString("T"), new NNString("H"), new NNString("Z") };
        NNEntity[] yListB = { new NNString("Y"), new NNString("D"), new NNString("Z"), new NNString("V"), new NNString("B") };
        TableDisplayer.multiplyAndShow(xListB, yListB);

        System.out.println("--- Colors ---");
        // 顏色對顏色
        NNEntity[] xListC = { new NNColor("Red"), new NNColor("Red"), new NNColor("Red"), new NNColor("Green"), new NNColor("Blue") };
        NNEntity[] yListC = { new NNColor("Green"), new NNColor("Blue"), new NNColor("Red"), new NNColor("Blue"), new NNColor("Green") };
        TableDisplayer.multiplyAndShow(xListC, yListC);
    }
}
