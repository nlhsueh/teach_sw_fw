package swfw.ch02.interfaces;

public class TableDisplayer {
    public static void multiplyAndShow(NNEntity[] xList, NNEntity[] yList) {
        /* Multiply */
        NNEntity[][] table = new NNEntity[yList.length][xList.length];
        for (int i = 0; i < yList.length; i++) {
            for (int j = 0; j < xList.length; j++) {
                table[i][j] = xList[j].multiply(yList[i]);
            }
        }
        /* Show */
        System.out.printf("%15s", "");
        for (int i = 0; i < xList.length; i++) {
            System.out.printf("%15s", xList[i]);
        }
        System.out.println();
        System.out.println("    " + "=".repeat(15 * (xList.length + 1)));
        
        for (int i = 0; i < yList.length; i++) {
            System.out.printf("%15s", yList[i]);
            for (int j = 0; j < xList.length; j++) {
                System.out.printf("%15s", table[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
