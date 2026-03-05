package swfw.ch11.chess.Rules;

import swfw.ch11.chess.Board.BlindBoard;
import swfw.ch11.chess.Chess.ChineseChess;
import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Model.Player;
import swfw.ch11.chess.Model.Side;

public class BlindChessRules implements RulesInterface {
    private int unMove = 0;
    private Side winnerSide;

    public void increaseUnMove(){
        this.unMove++;
    }

    public void clearUnMove(){
        this.unMove = 0;
    }

    @Override
    public boolean canMove(BoardInterface board, int fromRow, int fromCol, int toRow, int toCol) {
        int diffRow = Math.abs(fromRow-toRow);
        int diffCol = Math.abs(fromCol-toCol);

        return (diffRow+diffCol) == 1;
    }

    @Override
    public boolean canJump(BoardInterface board, int fromRow, int fromCol, int toRow, int toCol) {
        ChineseChess chess = (ChineseChess)board.getChess(fromRow, fromCol);
        if(!chess.isPao()) return false;

        int diffRow = Math.abs(fromRow-toRow);
        int diffCol = Math.abs(fromCol-toCol);
        int rowChess = 0;
        int colChess = 0;

        for(int i = Math.min(fromRow, toRow) + 1; i < Math.max(fromRow, toRow); i++) {
            if (board.getChess(i, toCol) != null) {
                rowChess++;
            }
        }

        for(int i = Math.min(fromCol, toCol) + 1; i < Math.max(fromCol, toCol); i++) {
            if (board.getChess(toRow, i) != null) {
                colChess++;
            }
        }
        return (diffRow == 0 && colChess == 1) || (diffCol == 0 && rowChess == 1);
    }

    @Override
    public boolean canEat(ChessInterface fromChess, ChessInterface toChess) {
        if(fromChess.getSide() == toChess.getSide()) return false;

        if(fromChess.getWeight() == 7 && toChess.getWeight() == 1) return false;
        if(fromChess.getWeight() == 1 && toChess.getWeight() == 7) return true;

        return fromChess.getWeight() >= toChess.getWeight();
    }

    @Override
    public boolean gameOver(BoardInterface board) {
        int red = ((BlindBoard) board).getHasRed();
        int black = ((BlindBoard) board).getHasBlack();

        if (red == 0) {
            this.winnerSide = Side.BLACK;
            return true;
        } else if (black == 0) {
            this.winnerSide = Side.RED;
            return true;
        } else if (this.unMove != 50) {
            return false;
        } else {
            if (red + black == 3) {
                int redLevel = ((BlindBoard) board).getLevel(Side.RED);
                int blackLevel = ((BlindBoard) board).getLevel(Side.BLACK);

                if (redLevel > blackLevel) {
                    this.winnerSide = Side.RED;
                } else if (redLevel < blackLevel) {
                    this.winnerSide = Side.BLACK;
                } else {
                    this.winnerSide = Side.UNDEFINED;
                }
            } else {
                this.winnerSide = Side.UNDEFINED;
            }
            return true;
        }
    }

    @Override
    public Side getWinnerSide() {
        return this.winnerSide;
    }

    @Override
    public boolean isValidMove(BoardInterface board, Side side, int fromRow, int fromCol, int toRow, int toCol) {
        ChineseChess fromChess = (ChineseChess) board.getChess(fromRow, fromCol);
        ChineseChess toChess = (ChineseChess) board.getChess(toRow, toCol);

        if(canMove(board, fromRow, fromCol, toRow, toCol) && toChess == null) return true;

        if(toChess == null || !toChess.isShow()) return false;

        if(canMove(board, fromRow, fromCol, toRow, toCol) && canEat(fromChess, toChess)) return true;

        if(canJump(board, fromRow, fromCol, toRow, toCol)) return true;

        return false;
    }

    @Override
    public void setSide(Side side, Player p1, Player p2) {
        if (side == Side.RED) {
            p1.setSide(Side.RED);
            p2.setSide(Side.BLACK);
        } else {
            p1.setSide(Side.BLACK);
            p2.setSide(Side.RED);
        }
    }
}
