package live.boardgames.base;

public interface BoardAI {
    Move getBestMove(Board board, BoardEvaluator evaluator);
}
