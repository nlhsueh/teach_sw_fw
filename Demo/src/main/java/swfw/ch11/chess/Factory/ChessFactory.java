package swfw.ch11.chess.Factory;

import swfw.ch11.chess.Board.BoardInterface;
import swfw.ch11.chess.Rules.RulesInterface;

public interface ChessFactory {
    BoardInterface createBoard();
    RulesInterface createRules();
}
