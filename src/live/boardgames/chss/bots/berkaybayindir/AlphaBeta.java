package live.boardgames.chss.bots.berkaybayindir;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.*;

import java.util.List;

public class AlphaBeta implements BoardAI {


   private EvaluatedMove ABGetMove(Board board, BoardEvaluator evaluator,int player, int maxDepth, int currDepth, float alpha, float beta){
       if(board.isGameOver() || currDepth == maxDepth){
           double score = evaluator.evaluate(board,player);
           return new EvaluatedMove(null,score);
       }

       alpha = Integer.MAX_VALUE;
       beta = Integer.MIN_VALUE;

       Move bestMove = null;
       double bestScore = Integer.MAX_VALUE;

       List<Move> moves= board.getMoves();

       for (Move m:moves) {
           Board newBoard= board.makeMove(m);

           EvaluatedMove oppMove = ABGetMove(newBoard,evaluator,player,maxDepth,currDepth+1, -beta, -alpha);

           if(oppMove.getScore() > bestScore){
               bestScore = oppMove.getScore();
               bestMove = m;
           }

           if(bestScore >= beta){
               break;
           }
       }

       return new EvaluatedMove(bestMove,bestScore);
   }



    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        EvaluatedMove em = ABGetMove(board,evaluator, board.currentPlayer(),5,0, -Integer.MAX_VALUE, Integer.MAX_VALUE);

        return em.getMove();
    }
}
