package swfw.ch02.interfaces;

public class NNColor implements NNEntity {
    private String color;

    public NNColor(String color) {
        this.color = color;
    }

    @Override
    public NNEntity multiply(NNEntity otherone) {
        if (otherone == null || getClass() != otherone.getClass()) {
            return null;
        }
        NNColor other = (NNColor) otherone;
        String resultColor;
        
        // Simple color mixing logic for demonstration
        if (this.color.equals(other.color)) {
            resultColor = this.color;
        } else if ((this.color.equalsIgnoreCase("Red") && other.color.equalsIgnoreCase("Green")) ||
                   (this.color.equalsIgnoreCase("Green") && other.color.equalsIgnoreCase("Red"))) {
            resultColor = "Brown";
        } else if ((this.color.equalsIgnoreCase("Red") && other.color.equalsIgnoreCase("Yellow")) ||
                   (this.color.equalsIgnoreCase("Yellow") && other.color.equalsIgnoreCase("Red"))) {
            resultColor = "Orange";
        } else if ((this.color.equalsIgnoreCase("Green") && other.color.equalsIgnoreCase("Yellow")) ||
                   (this.color.equalsIgnoreCase("Yellow") && other.color.equalsIgnoreCase("Green"))) {
            resultColor = "Yellow-Green";
        } else {
            resultColor = this.color + "-" + other.color;
        }
        
        return new NNColor(resultColor);
    }

    @Override
    public String toString() {
        return color;
    }
}
