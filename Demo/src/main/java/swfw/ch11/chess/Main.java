package swfw.ch11.chess;

import swfw.ch11.chess.Factory.BlindChessFactory;
import swfw.ch11.chess.Factory.ChessFactory;
import swfw.ch11.chess.Factory.StandardChessFactory;
import swfw.ch11.chess.Manager.GameManager;
import swfw.ch11.chess.Model.Gender;
import swfw.ch11.chess.Model.Player;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

//    public static void main(String[] args) {
//        int game = 0;
//        Scanner keyboard = new Scanner(System.in);
//        ChessFactory factory;
//        GameManager manager;
//
//        Player sophie = new Player("Sophie", Gender.FEMALE);
//        Player jason = new Player("Jason", Gender.MALE);
//
//
//        while (game != -1){
//
//            System.out.println("Enter number to start a game");
//            System.out.println("1: Standard Chess");
//            System.out.println("2: Blind Chess");
//            System.out.println("-1: Exit");
//            System.out.print(">> ");
//            game = keyboard.nextInt();
//
//            switch (game){
//                case 1:
//                    factory = new StandardChessFactory();
//                    break;
//
//                case 2:
//                    factory = new BlindChessFactory();
//                    break;
//
//                default:
//                    continue;
//            }
//
//            manager = new GameManager(factory);
//            manager.setPlayer(sophie, jason);
//            manager.createGame();
//            manager.showBoard();
//            manager.startGame();
//            manager.getResult();
//        }
//    }
}