package swfw.ch11.chess.Rules;

import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Board.StandardBoard;
import swfw.ch11.chess.Chess.ChineseChess;
import swfw.ch11.chess.Model.Player;
import swfw.ch11.chess.Model.Side;

public class StandardChessRules implements RulesInterface {
    private Side winnerSide;

    private boolean inTian(int row, int col){
        return (col >= 4 && col <= 6) && (row <= 2 || row >= 7);
    }

    private boolean inSite(ChineseChess chess, int row){
        return ((chess.getSide() == Side.BLACK) && (row <= 4)) || ((chess.getSide() == Side.RED) && (row >=5));
    }

    @Override
    public boolean canMove(BoardInterface board, int fromRow, int fromCol, int toRow, int toCol) {
        ChineseChess fromChess = (ChineseChess) board.getChess(fromRow, fromCol);

        int diffRow = Math.abs(fromRow-toRow);
        int diffCol = Math.abs(fromCol-toCol);

        switch (fromChess.getWeight()){
            case 1:  //卒
                if((fromChess.getSide() == Side.BLACK) && ((toRow - fromRow) == 1)) return true;
                if((fromChess.getSide() == Side.RED) && ((toRow - fromRow) == -1)) return true;

                return !inSite(fromChess, fromRow) && (diffRow == 0 && diffCol == 1);

            case 2:  //砲
                return false;

            case 3:  //馬
                if(diffRow == 2 && diffCol == 1){
                    if((toRow-fromRow) > 0) return board.getChess(fromRow+1, fromCol) == null;
                    else return board.getChess(fromRow-1, fromCol) == null;
                }else if(diffRow == 1 && diffCol == 2){
                    if((toCol-fromCol) > 0) return board.getChess(fromRow, fromCol+1) == null;
                    else return board.getChess(fromRow, fromCol-1) == null;
                }

                return false;

            case 4:  //車
                return false;

            case 5:  //象
                int middleRow = (fromRow+toRow)/2;
                int middleCol = (fromCol+toCol)/2;
                ChineseChess middleChess = (ChineseChess) board.getChess(middleRow, middleCol);

                return ((middleChess == null) && (diffRow == 2) && (diffCol == 2) && inSite(fromChess, toRow));

            case 6:  //士
                return ((diffRow+diffCol) == 2) && inTian(toRow, toCol);

            case 7:  //將
                return ((diffRow+diffCol) == 1) && inTian(toRow, toCol);
        }
        return false;
    }

    @Override
    public boolean canJump(BoardInterface board, int fromRow, int fromCol, int toRow, int toCol) {
        ChineseChess fromChess = (ChineseChess) board.getChess(fromRow, fromCol);
        ChineseChess toChess = (ChineseChess) board.getChess(toRow, toCol);


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

        return switch (fromChess.getWeight()) {
            case 2 ->  //砲
                    (diffRow == 0 && colChess == 1) || (diffCol == 0 && rowChess == 1);
            case 4 ->  //車
                    (diffRow == 0 && colChess == 0) || (diffCol == 0 && rowChess == 0);
            case 7 ->   //將
                    (toChess != null && toChess.getWeight() == 7) && (diffCol == 0) && (rowChess == 0);
            default -> false;
        };
    }

    @Override
    public boolean canEat(ChessInterface fromChess, ChessInterface toChess) {
        if(toChess == null) return false;
        return fromChess.getSide() != toChess.getSide();
    }

    @Override
    public boolean gameOver(BoardInterface board) {
        StandardBoard nowBoard = (StandardBoard)board;
        if(nowBoard.isGameOver()){
            winnerSide = nowBoard.getWinnerSide();
            return true;
        }

        return false;
    }

    @Override
    public Side getWinnerSide() {
        return this.winnerSide;
    }

    @Override
    public boolean isValidMove(BoardInterface board, Side side, int fromRow, int fromCol, int toRow, int toCol) {
        ChineseChess fromChess = (ChineseChess) board.getChess(fromRow, fromCol);
        ChineseChess toChess = (ChineseChess) board.getChess(toRow, toCol);

        if(fromChess.isJu()) return canJump(board, fromRow, fromCol, toRow, toCol);

        if(canMove(board, fromRow, fromCol, toRow, toCol) && ((toChess == null) || (canEat(fromChess, toChess)))) return true;

        if(canJump(board, fromRow, fromCol, toRow, toCol) && canEat(fromChess, toChess)) return true;

        return false;
    }

    @Override
    public void setSide(Side side, Player p1, Player p2) {
        p1.setSide(Side.RED);
        p2.setSide(Side.BLACK);
    }
}
