package swfw.ch11.chess.Board;

import swfw.ch11.chess.Chess.ChessInterface;

public interface BoardInterface {
    int getRow();
    int getCol();
    void createBoard();
    ChessInterface getChess(int row, int col);
    void moveChess(int fromRow, int fromCol, int toRow, int toCol);
    void showBoard();
}
