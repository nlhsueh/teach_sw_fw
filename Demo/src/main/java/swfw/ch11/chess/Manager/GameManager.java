package swfw.ch11.chess.Manager;

import swfw.ch11.chess.Board.StandardBoard;
import swfw.ch11.chess.Model.Player;
import swfw.ch11.chess.Model.Side;
import swfw.ch11.chess.Rules.BlindChessRules;
import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Factory.ChessFactory;
import swfw.ch11.chess.Chess.ChessInterface;
import swfw.ch11.chess.Rules.RulesInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private final ChessFactory factory;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player nowPlayer;
    private BoardInterface board;
    private RulesInterface rules;
    private Player winner;

    public GameManager(ChessFactory factory) {
        this.factory = factory;
    }

    public void createGame() {
        this.board = factory.createBoard(); // 確保這行代碼被執行
        if (this.board == null) {
            System.out.println("Error: Board is null!");
        }
        this.rules = factory.createRules();

        this.firstPlayer.setSide(Side.UNDEFINED);
        this.secondPlayer.setSide(Side.UNDEFINED);
        this.winner = null;

        if (this.board instanceof StandardBoard) {
            this.rules.setSide(Side.RED, this.firstPlayer, this.secondPlayer);
        }
    }

    public void setPlayer(Player p1, Player p2){
        this.firstPlayer = p1;
        this.secondPlayer = p2;
        this.nowPlayer = this.firstPlayer;
    }

    public void changePlayer(){
        if(nowPlayer==firstPlayer) nowPlayer=secondPlayer;
        else nowPlayer=firstPlayer;
    }

    public void startGame(){
        Scanner keyboard = new Scanner(System.in);

        while (!gameOver()){
            System.out.println("It's "+getNowPlayer().getName()+"'s ("+getNowPlayer().getSide()+") round.");
            System.out.print("Please choose a chess>>  ");
            String position = keyboard.next().toUpperCase();
//            if(move(((int)position.charAt(0)-'A'),((int)position.charAt(1)-'1'))){
                showBoard();
                changePlayer();
//            }
        }

        setResult(rules.getWinnerSide());
    }

    public void setResult(Side winnerSide){
        if(firstPlayer.getSide() == winnerSide){
            this.winner = this.firstPlayer;
            this.firstPlayer.incrementWinTime();
            this.secondPlayer.incrementLoseTime();

        } else if(secondPlayer.getSide() == winnerSide) {
            this.winner = this.secondPlayer;
            this.firstPlayer.incrementLoseTime();
            this.secondPlayer.incrementWinTime();

        } else this.winner = null;
        System.out.println("The winner is "+winner.getName()+", side is "+winnerSide);
    }

    public Player getNowPlayer() {
        return nowPlayer;
    }

    public void showBoard(){
        this.board.showBoard();
    }

    public boolean gameOver(){
        return this.rules.gameOver(this.board);
    }

    public boolean move(int fromRow, int fromCol, int toRow, int toCol) {
        ChessInterface fromChess = this.board.getChess(fromRow, fromCol);
        ChessInterface toChess = this.board.getChess(toRow, toCol);

        if (fromChess == null || fromChess.getSide() != nowPlayer.getSide()) {
            return false; // 不是玩家的棋子或棋子不存在
        }

        if (this.rules.isValidMove(this.board, nowPlayer.getSide(), fromRow, fromCol, toRow, toCol)) {
            this.board.moveChess(fromRow, fromCol, toRow, toCol);
            if(gameOver()) setResult(getNowPlayer().getSide());
            changePlayer();
            return true; // 成功移動
        } else {
            return false; // 移動無效
        }
    }

    public void getResult(){
        if(this.winner == null) System.out.println("-TIE GAME-");
        else {
            System.out.println(this.winner.getSide()+" WIN");
            System.out.println(this.winner.getName()+" is the best!!!");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public BoardInterface getBoard() {
        return board;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
