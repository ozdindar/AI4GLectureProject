package live.personal.chess.bots.simple;

import live.boardgames.base.Board;
import live.boardgames.base.Move;

import java.util.List;

public interface MoveGenerator {
    List<Move> generateMoves(Board b);

    void setBestMove(Board board, Move bestMove, int depth);
}
