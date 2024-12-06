package live.boardgames.chss.bots.Cenk;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.ChessMove;

import java.util.List;

public class    QuiescenceSearch {


   public static double Quiescence(Board board, BoardEvaluator eval, int player, double alpha, double beta, int depth){
       double stand_pat = eval.evaluate(board,player);
       if(depth<=0)
           return stand_pat;
       if( stand_pat >= beta )
           return beta;
       if( alpha < stand_pat )
           alpha = stand_pat;

       List<Move> moves = board.getMoves();


       for( Move move:moves )  {

           Board newBoard = board.cloneBoard().makeMove(move);
           double score = -Quiescence(newBoard,eval,(player==1?0:1), -beta, -alpha,depth-1 );

           if( score >= beta )
               return beta;
           if( score > alpha )
               alpha = score;
       }
       return alpha;

   }
}
