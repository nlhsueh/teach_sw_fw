package swfw.ch11.chess.Chess;

import swfw.ch11.chess.Model.Side;

public class ChineseChess implements ChessInterface {
    private final String name;
    private final int weight;
    private final Side side;
    private boolean isShow;

    public ChineseChess(String name, int weight, Side side, boolean isShow) {
        this.name = name;
        this.weight = weight;
        this.side = side;
        this.isShow = isShow;
    }

    public String toString() {
        String name = this.name;
        String side = String.valueOf(this.side);
        return "Chess{name='" + name + "', weight=" + this.weight + ", side=" + side + ", isShow=" + this.isShow + "}";
    }

    public String getName() {
        return this.name;
    }

    public void openChess() {
        this.isShow = true;
        System.out.println("棋子 " + name + " 被翻開了，現在 isShow = " + isShow);
    }

    public boolean isShow() {
        return this.isShow;
    }

    public Side getSide() {
        return this.side;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isJaing(){
        return this.weight == 7;
    }
    public boolean isJu(){
        return this.weight == 4;
    }
    public boolean isPao(){
        return this.weight == 2;
    }
}
