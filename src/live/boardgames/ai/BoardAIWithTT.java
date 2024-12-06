package live.boardgames.ai;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

public class BoardAIWithTT implements BoardAI {

    BoardAI boardAI;

    TranspositionTable<Move> table;

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        if (table.contains(board.getKey()))
        {
            Move m = table.get(board.getKey());
            return m;
        }

        Move m = boardAI.getBestMove(board,evaluator);

        table.put(board.getKey(),m);
        return m;
    }
}
