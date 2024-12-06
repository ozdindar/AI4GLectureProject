package live.personal.chess.bots.simple;


import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.personal.chess.bots.simple.MoveGenerator;

import java.util.List;

public class ABNegascout_book implements BoardAI {

    int maxDepth;


    MoveGenerator moveGenerator;


    public ABNegascout_book(int maxDepth, MoveGenerator moveGenerator) {
        this.maxDepth= maxDepth;
        this.moveGenerator = moveGenerator;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        EvaluatedMove em = getBestMove(board,evaluator,maxDepth, 0,-Double.MAX_VALUE, Double.MAX_VALUE);

        return em.getMove();

    }

    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int maxDepth, int depth, double alpha, double beta) {
        if (board.isGameOver()|| depth == maxDepth)
            return new EvaluatedMove(null,evaluator.evaluate(board,board.currentPlayer()));

        double bestScore = -Integer.MAX_VALUE;
        Move bestMove = null;
        double adaptiveBeta = beta;

        List<Move> moves = moveGenerator.generateMoves(board);

        for (Move m : moves)
        {
            Board newBoard = board.makeMove(m);

            EvaluatedMove currentChoice = abNegamax(newBoard,evaluator,maxDepth,depth+1,-adaptiveBeta,-max(alpha,bestScore));
            double currentScore = -currentChoice.getScore();

            if (currentScore>bestScore)
            {
                if (adaptiveBeta== beta || depth>= maxDepth-2)
                {
                    bestMove  = m;
                    bestScore = currentScore;
                }
                else {
                    EvaluatedMove negativeBestChoice = getBestMove(newBoard,evaluator,maxDepth,depth,-beta,-currentScore);
                    bestScore = -negativeBestChoice.getScore();
                    bestMove = negativeBestChoice.getMove();
                }
                if (bestScore>=beta)
                    return new EvaluatedMove(bestMove,bestScore);
                else adaptiveBeta = max(alpha,bestScore)+1;

            }
        }

        return new EvaluatedMove(bestMove,bestScore);
    }

    private double max(double a, double b) {
        return Math.max(a,b);
    }


    EvaluatedMove abNegamax(Board board, BoardEvaluator evaluator, int maxDepth, int depth, double alpha, double beta)
    {
        if (board.isGameOver()|| depth == maxDepth)
            return new EvaluatedMove(null,evaluator.evaluate(board,board.currentPlayer()));

        double bestScore = -Integer.MAX_VALUE;
        Move bestMove = null;

        List<Move> moves = moveGenerator.generateMoves(board);

        for (Move m : moves)
        {
            Board newBoard = board.makeMove(m);
            EvaluatedMove currentChoice = abNegamax(newBoard,evaluator,maxDepth,depth+1,-beta,-alpha);

            double currentScore = -currentChoice.getScore();

            if (currentScore>bestScore)
            {
                bestMove  = m;
                bestScore = currentScore;
            }

            if (currentScore>alpha)
            {
                alpha = currentScore;
            }
            if (alpha>=beta)
                break;;
        }

        return new EvaluatedMove(bestMove,bestScore);
    }
}
