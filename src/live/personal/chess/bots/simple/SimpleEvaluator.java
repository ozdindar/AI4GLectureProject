package live.personal.chess.bots.simple;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;

import java.util.ArrayList;
import java.util.List;

public class SimpleEvaluator implements BoardEvaluator {

    List<BoardEvaluator> evaluators = new ArrayList<>();

    @Override
    public double evaluate(Board board, int player) {
        double score = 0;
        for (BoardEvaluator evaluator:evaluators)
            score += evaluator.evaluate(board,player);
        return score;
    }

    public void addEvaluator(BoardEvaluator evaluator) {
        evaluators.add(evaluator);
    }
}
