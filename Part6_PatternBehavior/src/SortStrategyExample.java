interface SortStrategy {
    public int[] sort(int [] d);
}

class SortArray {
    int[] d;
    private SortStrategy sortStrategy;
    public SortArray(SortStrategy s) {
        this.sortStrategy = s;
    }
    public int[] doSort() {
        return this.sortStrategy.sort(d);
    }
}

class QuickSort implements SortStrategy {
    public int[] sort(int[] d) {
        //do ....
        return d; // Fixed for compilation
    }
}

class SelectionSort implements SortStrategy {
    public int[] sort(int[] d) {
        //do ....
        return d; // Fixed for compilation
    }
}

//main program to test
class StrategyExample {
    public static void main(String[] args) {
        SortArray context;
        context = new SortArray(new QuickSort());
        int[] resultA = context.doSort();

        context = new SortArray(new SelectionSort());
        int[] resultB = context.doSort();
    }
}
