package live.personal.chess.bots.simple;


import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class ABNegascout implements BoardAI {

    int maxDepth;


    MoveGenerator moveGenerator;


    public ABNegascout(int maxDepth, MoveGenerator moveGenerator) {
        this.maxDepth= maxDepth;
        this.moveGenerator = moveGenerator;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        EvaluatedMove em = getBestMove(board,evaluator,maxDepth,-Double.MAX_VALUE, Double.MAX_VALUE);

        return em.getMove();

    }

    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int depth, double alpha, double beta) {
        if (board.isGameOver()|| depth == 0)
            return new EvaluatedMove(null,evaluator.evaluate(board,board.currentPlayer()));

        double score = -Integer.MAX_VALUE;
        Move bestMove = null;
        double n = beta;

        List<Move> moves = moveGenerator.generateMoves(board);

        for (Move m : moves)
        {
            Board newBoard = board.makeMove(m);

            EvaluatedMove currentChoice = getBestMove(newBoard,evaluator,depth-1,-n,-alpha);
            double currentScore = -currentChoice.getScore();

            if (currentScore>score)
            {
                if (n== beta || depth<= 2)
                {
                    bestMove  = m;
                    score = currentScore;
                }
                else {
                    EvaluatedMove negativeBestChoice = getBestMove(newBoard,evaluator,depth-1,-beta,-currentScore);
                    score = -negativeBestChoice.getScore();
                    //bestMove = negativeBestChoice.getMove();
                }
            }
            if (score>alpha)
                alpha = score;
            if (alpha>=beta)
                break;
            n = alpha+1;
        }

        return new EvaluatedMove(bestMove,score);
    }

    private double max(double a, double b) {
        return Math.max(a,b);
    }


}
