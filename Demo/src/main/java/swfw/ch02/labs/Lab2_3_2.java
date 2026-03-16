package swfw.ch02.labs;

abstract class NNEntity {
    public abstract NNEntity multiply(NNEntity other);
}

class NNInteger extends NNEntity {
    private int number;

    public NNInteger(int number) {
        this.number = number;
    }

    @Override
    public NNEntity multiply(NNEntity other) {
        if (other instanceof NNInteger) {
            return new NNInteger(this.number * ((NNInteger) other).number);
        }
        return null;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}

class Sign extends NNEntity {
    private String name;
    private int date;

    public Sign(String name, int date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public NNEntity multiply(NNEntity other) {
        if (other instanceof Sign) {
            Sign otherSign = (Sign) other;
            long val = (long) this.date * otherSign.date;
            int luckyNumber = (int) (val % 144);
            return new NNInteger(luckyNumber);
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

class TableDisplayer {
    public static void multiplyAndShow(NNEntity[] xList, NNEntity[] yList) {
        NNEntity[][] table = new NNEntity[yList.length][xList.length];
        for (int i = 0; i < yList.length; i++) {
            for (int j = 0; j < xList.length; j++) {
                table[i][j] = xList[j].multiply(yList[i]);
            }
        }
        
        System.out.println("<table>");
        
        System.out.print("  <tr><th></th>");
        for (int i = 0; i < xList.length; i++) {
            System.out.print("<th>" + xList[i] + "</th>");
        }
        System.out.println("</tr>");
        
        for (int i = 0; i < yList.length; i++) {
            System.out.print("  <tr><th>" + yList[i] + "</th>");
            for (int j = 0; j < xList.length; j++) {
                System.out.print("<td>" + table[i][j] + "</td>");
            }
            System.out.println("</tr>");
        }
        System.out.println("</table>");
    }
}

public class Lab2_3_2 {
    public static void main(String[] args) {
        NNEntity[] signsX = { new Sign("牡羊", 321), new Sign("金牛", 420), new Sign("雙子", 521), new Sign("巨蟹", 622) };
        NNEntity[] signsY = { new Sign("牡羊", 321), new Sign("金牛", 420), new Sign("雙子", 521), new Sign("巨蟹", 622) };
        TableDisplayer.multiplyAndShow(signsX, signsY);
    }
}
