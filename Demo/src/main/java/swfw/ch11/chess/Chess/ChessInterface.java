package swfw.ch11.chess.Chess;

import swfw.ch11.chess.Model.Side;

public interface ChessInterface {
    String getName();
    boolean isShow();
    Side getSide();
    int getWeight();
    void openChess();
}
