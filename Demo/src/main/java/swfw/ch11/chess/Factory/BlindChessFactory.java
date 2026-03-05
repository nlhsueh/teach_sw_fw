package swfw.ch11.chess.Factory;

import swfw.ch11.chess.Board.BlindBoard;
import swfw.ch11.chess.Rules.BlindChessRules;
import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Rules.RulesInterface;

public class BlindChessFactory implements ChessFactory {

    @Override
    public BoardInterface createBoard() {
        return new BlindBoard();
    }

    @Override
    public RulesInterface createRules() {
        return new BlindChessRules();
    }
}
