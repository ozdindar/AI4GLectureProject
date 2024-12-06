package live.boardgames.chss.bots.tunaalaygut;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class AlphaBetaPruning implements BoardAI {
    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        EvaluatedMove em = getBestMove(board, evaluator, -1000000, 1000000, board.currentPlayer(), 3);
        return em.getMove();
    }

    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, double alpha, double beta, int player, int depth){
        if (board.isGameOver() || depth <= 0) {
            double score = evaluator.evaluate(board, player);   //Actual evaluation occurs at the leaf.
            return new EvaluatedMove(null, score);
        }

        List<Move> moves = board.getMoves();

        Move bestMove = null;

        boolean isCurrentPlayer = (player == board.currentPlayer());

        double bestScore = (isCurrentPlayer ? -1000000 : 1000000);

        for(Move move : moves){
            Board dummy = board.makeMove(move);
            EvaluatedMove evaluated = getBestMove(dummy, evaluator, alpha, beta, player, depth - 1);

            if( ((evaluated.getScore() >= bestScore) && isCurrentPlayer) || ((evaluated.getScore() <= bestScore) && !isCurrentPlayer) ){
                bestMove = move;
                bestScore = evaluated.getScore();
            }

            if(isCurrentPlayer)  alpha = max(alpha, evaluated.getScore());
            else beta = min(beta, evaluated.getScore());

            if(beta <= alpha) break;
        }

        return new EvaluatedMove(bestMove, bestScore);
    }

    private double max(double a, double b){
        return a > b ? a : b;
    }

    private double min(double a, double b){
        return a < b ? a : b;
    }
}
