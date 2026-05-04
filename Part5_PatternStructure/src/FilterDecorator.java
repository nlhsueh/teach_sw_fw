import java.util.ArrayList;
import java.util.List;

interface ListProcessor {
    List<String> process(List<String> input);
}

class BaseProcessor implements ListProcessor {
    public List<String> process(List<String> input) {
        return new ArrayList<>(input);
    }
}

abstract class FilterDecorator implements ListProcessor {
    protected ListProcessor next;
    public FilterDecorator(ListProcessor next) { this.next = next; }
}

class LengthFilter extends FilterDecorator {
    public LengthFilter(ListProcessor next) { super(next); }
    public List<String> process(List<String> input) {
        List<String> result = new ArrayList<>();
        for (String s : next.process(input)) {
            if (s.length() > 3) result.add(s);
        }
        return result;
    }
}

class UppercaseFilter extends FilterDecorator {
    public UppercaseFilter(ListProcessor next) { super(next); }
    public List<String> process(List<String> input) {
        List<String> result = new ArrayList<>();
        for (String s : next.process(input)) {
            result.add(s.toUpperCase());
        }
        return result;
    }
}

public class FilterExample {
    public static void main(String[] args) {
        List<String> data = List.of("abc", "defg", "hi");
        ListProcessor p = new UppercaseFilter(new LengthFilter(new BaseProcessor()));
        System.out.println(p.process(data)); // Output: [DEFG]
    }
}
