package swfw.ch11.chess.Board;

import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Chess.ChineseChess;
import swfw.ch11.chess.Model.Side;

public class StandardBoard implements BoardInterface {
    private final ChineseChess[][] board = new ChineseChess[10][9];
    private boolean isGameOver = false;
    private Side winnerSide = Side.UNDEFINED;

    public StandardBoard(){
        createBoard();
    }

    @Override
    public int getRow() {
        return 10;
    }

    @Override
    public int getCol() {
        return 9;
    }

    @Override
    public void createBoard() {
        System.out.println("Standard Board creating...");
        this.board[0][0] = new ChineseChess("車", 4, Side.BLACK, true);
        this.board[0][1] = new ChineseChess("馬", 3, Side.BLACK, true);
        this.board[0][2] = new ChineseChess("象", 5, Side.BLACK, true);
        this.board[0][3] = new ChineseChess("士", 6, Side.BLACK, true);
        this.board[0][4] = new ChineseChess("將", 7, Side.BLACK, true);
        this.board[0][5] = new ChineseChess("士", 6, Side.BLACK, true);
        this.board[0][6] = new ChineseChess("象", 5, Side.BLACK, true);
        this.board[0][7] = new ChineseChess("馬", 3, Side.BLACK, true);
        this.board[0][8] = new ChineseChess("車", 4, Side.BLACK, true);

        this.board[2][1] = new ChineseChess("砲", 2, Side.BLACK, true);
        this.board[2][7] = new ChineseChess("砲", 2, Side.BLACK, true);

        this.board[3][0] = new ChineseChess("卒", 1, Side.BLACK, true);
        this.board[3][2] = new ChineseChess("卒", 1, Side.BLACK, true);
        this.board[3][4] = new ChineseChess("卒", 1, Side.BLACK, true);
        this.board[3][6] = new ChineseChess("卒", 1, Side.BLACK, true);
        this.board[3][8] = new ChineseChess("卒", 1, Side.BLACK, true);


        this.board[9][0] = new ChineseChess("居", 4, Side.RED, true);
        this.board[9][1] = new ChineseChess("傌", 3, Side.RED, true);
        this.board[9][2] = new ChineseChess("相", 5, Side.RED, true);
        this.board[9][3] = new ChineseChess("仕", 6, Side.RED, true);
        this.board[9][4] = new ChineseChess("帥", 7, Side.RED, true);
        this.board[9][5] = new ChineseChess("仕", 6, Side.RED, true);
        this.board[9][6] = new ChineseChess("相", 5, Side.RED, true);
        this.board[9][7] = new ChineseChess("傌", 3, Side.RED, true);
        this.board[9][8] = new ChineseChess("居", 4, Side.RED, true);

        this.board[7][1] = new ChineseChess("炮", 2, Side.RED, true);
        this.board[7][7] = new ChineseChess("炮", 2, Side.RED, true);

        this.board[6][0] = new ChineseChess("兵", 1, Side.RED, true);
        this.board[6][2] = new ChineseChess("兵", 1, Side.RED, true);
        this.board[6][4] = new ChineseChess("兵", 1, Side.RED, true);
        this.board[6][6] = new ChineseChess("兵", 1, Side.RED, true);
        this.board[6][8] = new ChineseChess("兵", 1, Side.RED, true);
    }

    @Override
    public ChessInterface getChess(int row, int col) {
        return this.board[row][col];
    }

    @Override
    public void moveChess(int fromRow, int fromCol, int toRow, int toCol) {
        ChineseChess chess = this.board[toRow][toCol];
        if( chess != null && chess.isJaing()) {
            this.isGameOver=true;
            winnerSide = this.board[fromRow][fromCol].getSide();
        }

        this.board[toRow][toCol] = this.board[fromRow][fromCol];
        this.board[fromRow][fromCol] = null;
    }

    @Override
    public void showBoard() {
        System.out.println("   １  ２  ３  ４  ５  ６  ７  ８  ９");

        for(int i = 0; i < 10; ++i) {
            System.out.print((char)(65 + i));

            for(int j = 0; j < 9; ++j) {
                if (this.board[i][j] != null) {
                    System.out.print("  " +this.board[i][j].getName());
                } else {
                    System.out.print("  ＿");
                }
            }
            System.out.println();
            if(i == 4) System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Side getWinnerSide() {
        return winnerSide;
    }
}
