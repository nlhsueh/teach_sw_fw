package swfw.ch11.chess.Rules;

import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Model.Player;
import swfw.ch11.chess.Model.Side;

public interface RulesInterface {
    boolean canMove(BoardInterface board, int fromRow, int fromCol, int toRow, int toCol);
    boolean canJump(BoardInterface board, int fromRow, int fromCol, int toRow, int toCol);
    boolean canEat(ChessInterface fromChess, ChessInterface toChess);
    boolean gameOver(BoardInterface board);
    Side getWinnerSide();
    boolean isValidMove(BoardInterface board, Side side, int fromRow, int fromCol, int toRow, int toCol);
    void setSide(Side side, Player p1, Player p2);
}
