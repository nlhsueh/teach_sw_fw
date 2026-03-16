package swfw.ch02.ex2_3;

import java.util.StringTokenizer;

public class Exercise2_3_1 {

    // 1. Inheritance approach
    static class SuperStringTokenizer extends StringTokenizer {
        public SuperStringTokenizer(String str) {
            super(str);
        }

        public SuperStringTokenizer(String str, String delim) {
            super(str, delim);
        }

        @Override
        public String nextToken() {
            return super.nextToken().toUpperCase();
        }
    }

    // 2. Composition (Delegation) approach
    static class SuperStringTokenizerDelegate {
        private StringTokenizer tokenizer;

        public SuperStringTokenizerDelegate(String str) {
            this.tokenizer = new StringTokenizer(str);
        }

        public SuperStringTokenizerDelegate(String str, String delim) {
            this.tokenizer = new StringTokenizer(str, delim);
        }

        public boolean hasMoreTokens() {
            return tokenizer.hasMoreTokens();
        }

        public String nextToken() {
            return tokenizer.nextToken().toUpperCase();
        }
    }

    public static void main(String[] args) {
        String s = "hello world java interface";
        
        System.out.println("--- Inheritance ---");
        SuperStringTokenizer sst = new SuperStringTokenizer(s);
        while(sst.hasMoreTokens()) {
            System.out.println(sst.nextToken());
        }

        System.out.println("--- Composition ---");
        SuperStringTokenizerDelegate sstd = new SuperStringTokenizerDelegate(s);
        while(sstd.hasMoreTokens()) {
            System.out.println(sstd.nextToken());
        }
    }
}
