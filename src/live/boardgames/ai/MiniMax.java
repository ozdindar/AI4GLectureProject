package live.boardgames.ai;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class MiniMax implements BoardAI {
    int maxDepth;

    public MiniMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        EvaluatedMove em =getBestMove(board,evaluator, board.currentPlayer(),0);

        return em.getMove();
    }

    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int player, int depth) {

        if (board.isGameOver() || depth>maxDepth)
        {
            double score = evaluator.evaluate(board,player);
            return new EvaluatedMove(null,score);
        }

        List<Move> moves= board.getMoves();

        Move bestMove = null;
        double bestScore = (player == board.currentPlayer()) ? - Double.MAX_VALUE: Double.MAX_VALUE;

        for (Move m:moves)
        {
            Board newBoard= board.makeMove(m);
            EvaluatedMove opponentMove = getBestMove(newBoard,evaluator,player,depth+1);

            if ( ( player == board.currentPlayer()&& bestScore<opponentMove.getScore())||
                 ( player != board.currentPlayer()&& bestScore>opponentMove.getScore()))
            {
                bestMove = m;
                bestScore = opponentMove.getScore();
            }


        }

        return new EvaluatedMove(bestMove,bestScore);
    }
}
