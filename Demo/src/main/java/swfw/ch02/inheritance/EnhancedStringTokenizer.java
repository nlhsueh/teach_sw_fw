package swfw.ch02.inheritance;

import java.util.StringTokenizer;

/**
 * EnhancedStringTokenizerDemo 示範如何繼承 StringTokenizer 並擴充其功能。
 */
public class EnhancedStringTokenizer extends StringTokenizer {
    private String[] a;
    private int count;

    // enhance the constructor
    public EnhancedStringTokenizer(String theString) {
        super(theString);
        a = new String[countTokens()];
        count = 0;
    }

    // enhance nextToken
    @Override
    public String nextToken() {
        String token = super.nextToken();
        // 以下是添增的功能
        a[count++] = token;
        return token;
    }

    // new method
    public String[] tokensSoFar() {
        String[] arrayToReturn = new String[count];
        for (int i = 0; i < count; i++)
            arrayToReturn[i] = a[i];
        return arrayToReturn;
    }
}

class EnhancedStringTokenizerDemo {
    public static void main(String args[]) {
        String s = "I love apple";
        EnhancedStringTokenizer tokenizer = new EnhancedStringTokenizer(s);

        System.out.println("開始解析字串: " + s);
        while (tokenizer.hasMoreTokens()) { // call parent's function
            String token = tokenizer.nextToken(); // call child's function
            System.out.println("當前 Token: " + token);

            System.out.print("目前為止的所有 Tokens: ");
            printSoFar(tokenizer.tokensSoFar()); // call child's (new defined) function
            System.out.println();
        }
    }

    static void printSoFar(String[] ss) {
        System.out.print("[");
        for (int i = 0; i < ss.length; i++) {
            System.out.print(ss[i] + (i == ss.length - 1 ? "" : ", "));
        }
        System.out.print("]");
    }
}
