package live.boardgames.chss.bots.MOrcunUslu;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class NegaMax implements BoardAI {
    int maxDepth;

    public NegaMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        EvaluatedMove em = NegaMax(board,evaluator,maxDepth,board.currentPlayer());
        return em.getMove();
    }
    private EvaluatedMove NegaMax(Board board, BoardEvaluator evaluator, int depth, int player){

        if(depth == 0 || board.isGameOver()){
            double score = evaluator.evaluate(board,player);
            return new EvaluatedMove(null,score);
        }

        List<Move> moves= board.getMoves();
        Move bestMove = null;
        Double bestScore = -Double.MAX_VALUE;

        for (Move move:moves) {

            Board nextBoard = board.makeMove(move);
            double score = -NegaMax(nextBoard,evaluator,depth-1,player).getScore();
            if(score > bestScore){
                bestScore = score;
                bestMove = move;
            }
        }

        return new EvaluatedMove(bestMove,bestScore);
    }
}
