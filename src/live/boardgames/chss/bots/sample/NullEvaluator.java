package live.boardgames.chss.bots.sample;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;

public class NullEvaluator implements BoardEvaluator {
    @Override
    public double evaluate(Board board, int player) {
        return 0;
    }
}
