package swfw.ch11.chess.Factory;

import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Rules.RulesInterface;
import swfw.ch11.chess.Board.StandardBoard;
import swfw.ch11.chess.Rules.StandardChessRules;

public class StandardChessFactory implements ChessFactory {

    @Override
    public BoardInterface createBoard() {
        return new StandardBoard();
    }

    @Override
    public RulesInterface createRules() {
        return new StandardChessRules();
    }
}
