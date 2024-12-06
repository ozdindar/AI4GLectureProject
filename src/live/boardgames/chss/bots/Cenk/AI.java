package live.boardgames.chss.bots.Cenk;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class AI implements BoardAI {
    private static int depth = 3;
    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        EvaluatedMove move = NegaMax(board,evaluator,board.currentPlayer(), depth,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        return move.getMove();

    }

    public EvaluatedMove NegaMax(Board board, BoardEvaluator eval, int player, int depth, double alpha, double beta) {
        if (depth <= 0 || board.isGameOver()) {
            return new EvaluatedMove(null, QuiescenceSearch.Quiescence(board,eval,player,alpha,beta,2));
        }

        List<Move> moves = board.getMoves();
        double bestValue = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        for (Move currentMove : moves) {

            Board newBoard = board.makeMove(currentMove);
            double value = -NegaMax(newBoard,eval,(player==1?0:1),depth - 1, -beta, -alpha).getScore();

            if(bestValue<value){
                bestValue=value;
                bestMove = currentMove;
            }
            //bestValue=Math.max(bestValue,value);


            alpha = Math.max(alpha,bestValue);

            if (alpha >= beta) {
                break;
            }

        }
        /*if(depth==this.depth)
        System.out.println(bestValue);*///Print the value for test purposes
        return new EvaluatedMove(bestMove,bestValue);
    }
}
