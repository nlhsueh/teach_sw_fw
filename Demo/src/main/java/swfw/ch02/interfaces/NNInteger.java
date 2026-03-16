package swfw.ch02.interfaces;

public class NNInteger implements NNEntity {
    private int number;

    public NNInteger(int number) {
        this.number = number;
    }

    public NNInteger(NNInteger copy) {
        this(copy.number);
    }

    // 數字相乘
    @Override
    public NNEntity multiply(NNEntity otherone) {
        if (otherone == null) {
            return null;
        } else if (getClass() != otherone.getClass()) {
            return null;
        } else {
            NNInteger otherone2 = (NNInteger) otherone;
            return new NNInteger(this.number * otherone2.number);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
