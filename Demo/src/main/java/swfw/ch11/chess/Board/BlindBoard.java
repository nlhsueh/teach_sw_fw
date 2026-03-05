package swfw.ch11.chess.Board;

import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Chess.ChineseChess;
import swfw.ch11.chess.Model.Side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlindBoard implements BoardInterface {
    private ChineseChess[][] board = new ChineseChess[4][8];
    private int hasRed = 16;
    private int hasBlack = 16;

    public BlindBoard() {
        createBoard();
    }

    @Override
    public int getRow() {
        return 4;
    }

    @Override
    public int getCol() {
        return 8;
    }

    @Override
    public void createBoard() {
        System.out.println("Blind Board creating...");
        List<ChineseChess> chessList = new ArrayList<>();
        chessList.add(new ChineseChess("將", 7, Side.BLACK, false));
        chessList.add(new ChineseChess("士", 6, Side.BLACK, false));
        chessList.add(new ChineseChess("士", 6, Side.BLACK, false));
        chessList.add(new ChineseChess("象", 5, Side.BLACK, false));
        chessList.add(new ChineseChess("象", 5, Side.BLACK, false));
        chessList.add(new ChineseChess("車", 4, Side.BLACK, false));
        chessList.add(new ChineseChess("車", 4, Side.BLACK, false));
        chessList.add(new ChineseChess("馬", 3, Side.BLACK, false));
        chessList.add(new ChineseChess("馬", 3, Side.BLACK, false));
        chessList.add(new ChineseChess("砲", 2, Side.BLACK, false));
        chessList.add(new ChineseChess("砲", 2, Side.BLACK, false));
        chessList.add(new ChineseChess("卒", 1, Side.BLACK, false));
        chessList.add(new ChineseChess("卒", 1, Side.BLACK, false));
        chessList.add(new ChineseChess("卒", 1, Side.BLACK, false));
        chessList.add(new ChineseChess("卒", 1, Side.BLACK, false));
        chessList.add(new ChineseChess("卒", 1, Side.BLACK, false));
        chessList.add(new ChineseChess("帥", 7, Side.RED, false));
        chessList.add(new ChineseChess("仕", 6, Side.RED, false));
        chessList.add(new ChineseChess("仕", 6, Side.RED, false));
        chessList.add(new ChineseChess("相", 5, Side.RED, false));
        chessList.add(new ChineseChess("相", 5, Side.RED, false));
        chessList.add(new ChineseChess("居", 4, Side.RED, false));
        chessList.add(new ChineseChess("居", 4, Side.RED, false));
        chessList.add(new ChineseChess("傌", 3, Side.RED, false));
        chessList.add(new ChineseChess("傌", 3, Side.RED, false));
        chessList.add(new ChineseChess("炮", 2, Side.RED, false));
        chessList.add(new ChineseChess("炮", 2, Side.RED, false));
        chessList.add(new ChineseChess("兵", 1, Side.RED, false));
        chessList.add(new ChineseChess("兵", 1, Side.RED, false));
        chessList.add(new ChineseChess("兵", 1, Side.RED, false));
        chessList.add(new ChineseChess("兵", 1, Side.RED, false));
        chessList.add(new ChineseChess("兵", 1, Side.RED, false));
        Collections.shuffle(chessList);

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 8; ++j) {
                this.board[i][j] = chessList.get(8 * i + j);
            }
        }
    }

    @Override
    public ChessInterface getChess(int row, int col) {
        return this.board[row][col];
    }

    @Override
    public void moveChess(int fromRow, int fromCol, int toRow, int toCol) {
        if(this.board[toRow][toCol] != null) {
            if (this.board[toRow][toCol].getSide() == Side.RED) hasRed--;
            else if (this.board[toRow][toCol].getSide() == Side.BLACK) hasBlack--;
        }

        this.board[toRow][toCol] = this.board[fromRow][fromCol];
        this.board[fromRow][fromCol] = null;
    }

    @Override
    public void showBoard() {
        System.out.println("   １  ２  ３  ４  ５  ６  ７  ８");

        for(int i = 0; i < 4; ++i) {
            System.out.print((char)(65 + i));

            for(int j = 0; j < 8; ++j) {
                if (this.board[i][j] != null) {
                    if (this.board[i][j].isShow()) {
                        System.out.print("  " +this.board[i][j].getName());
                    } else {
                        System.out.print("  口");
                    }
                } else {
                    System.out.print("  ＿");
                }
            }
            System.out.println();
        }
    }

    public int getLevel(Side side){
        int level=0;
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 8; ++j) {
                if (board[i][j].getSide() == side) {
                    level += board[i][j].getWeight();
                }
            }
        }
        return level;
    }

    public int getHasRed() {
        return hasRed;
    }

    public int getHasBlack() {
        return hasBlack;
    }
}
