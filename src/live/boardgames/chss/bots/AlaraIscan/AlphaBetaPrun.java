package live.boardgames.chss.bots.AlaraIscan;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class AlphaBetaPrun implements BoardAI {
    private static final int TIMEOUT_MILISECONDS = 1000;
    private static final int INITIAL_DEPTH = 1;
    private int depth=0;

    int maxDepth=5;
    private long start;
    private boolean timeout;
    Move bestMove = null;
    private Move gBestMove;


    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        EvaluatedMove em =getBestMove(board,evaluator, board.currentPlayer());

        return em.getMove();
    }


    public EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator,int player) {
        timeout = false;
        start = System.currentTimeMillis();

        for (int i = 0;; i++)
        {
            if (i > 0)
            {
                gBestMove = bestMove;
            }
            depth = INITIAL_DEPTH + i;
            abPrun(board,evaluator,player,depth, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (timeout)
            {
                System.out.println();
                return (EvaluatedMove) gBestMove;
            }
        }
    }


    private EvaluatedMove abPrun(Board board, BoardEvaluator evaluator, int player, int depth, double alpha, double beta) {


        if (board.isGameOver() || depth == maxDepth)
        {
            double score = evaluator.evaluate(board,player);
            return new EvaluatedMove(null, score);
        }


        double bestScore = - Double.MAX_VALUE;
        List<Move> abMoves= board.getMoves();

        for (Move m:abMoves)
        {
            Board newBoard= board.makeMove(m);
            EvaluatedMove opponentMove = abPrun(newBoard,evaluator,player,depth+1,-beta,-(Math.max(alpha,bestScore)));

            if ( ( player == board.currentPlayer() && bestScore<opponentMove.getScore()))
            {
                bestMove = m;
                bestScore = opponentMove.getScore();
            }

            if(bestScore>=beta){
                break;
            }


        }

        return new EvaluatedMove(bestMove, bestScore);
    }

}
