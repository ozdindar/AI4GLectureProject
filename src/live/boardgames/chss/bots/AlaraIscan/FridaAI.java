package live.boardgames.chss.bots.AlaraIscan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.util.RandomUtils;

import java.util.List;

public class FridaAI implements BoardAI {
// ı coldnt understand how to write this part

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        List<Move> moveList = board.getMoves();
        if (moveList.isEmpty())
            return null;
        int i = RandomUtils.randomInt(moveList.size());
        return moveList.get(i);

    }
}
