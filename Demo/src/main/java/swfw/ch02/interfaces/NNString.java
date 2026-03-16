package swfw.ch02.interfaces;

public class NNString implements NNEntity {
    private String words;

    public NNString(String words) {
        this.words = words;
    }

    public NNString(NNString copy) {
        this(copy.words);
    }

    // 字串相連
    @Override
    public NNEntity multiply(NNEntity otherone) {
        if (otherone == null) {
            return null;
        } else if (getClass() != otherone.getClass()) {
            return null;
        } else {
            NNString otherone2 = (NNString) otherone;
            return new NNString(this.words + otherone2.words);
        }
    }

    @Override
    public String toString() {
        return words;
    }
}
