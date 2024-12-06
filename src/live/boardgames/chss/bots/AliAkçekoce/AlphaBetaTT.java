/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.AliAkçekoce;

import java.util.List;
import live.boardgames.ai.TranspositionTable;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

/**
 *
 * @author ALI
 */
public class AlphaBetaTT implements BoardAI {
    int maxDepth;
    double ttAlpha;
    double ttBeta;
    int currentDepth;
    AlphaBetaEvaluatedMove alphabeta;
    
    TranspositionTable<AlphaBetaEntry> table = new TranspositionTable<>();
    private long trueHitCount=0;
    private long falseHitCount=0;
    public AlphaBetaTT(int maxDepth) {
        this.maxDepth = maxDepth;
        ttAlpha = 0;
        ttBeta = 0;
    }

   @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        for ( currentDepth = maxDepth-1; currentDepth>=0 ; currentDepth--) {
            alphabeta =getBestMove(board,evaluator, board.currentPlayer(),0,- Double.MAX_VALUE, Double.MAX_VALUE);
       }

        return alphabeta.getMove();
    }
          //entry.depth >= maxDepth-deptht,
    
//    tAlpha = alpha;
  //      ttBeta = beta;
    private AlphaBetaEvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int player, int depth,double alpha,double beta) {
        
        long key = board.getKey();
        if(table.contains(key))
        {
            AlphaBetaEntry entry = table.get(key);
      
            if(entry.alpha <= alpha && entry.beta >= beta){
                System.out.println("True Hit Count for iterative :" + trueHitCount++);
                return entry.evaluatedMove;
            }                
        }
            // else       
             //   System.out.println("False Hit Count:" + falseHitCount++);
             
        table.remove(key);
        
        if (board.isGameOver() || depth>currentDepth)
        {
            double score = evaluator.evaluate(board,player);
            AlphaBetaEvaluatedMove em = new AlphaBetaEvaluatedMove(null, score,alpha,beta);
            table.put(key, new AlphaBetaEntry(em, 0, alpha, beta));
            return em;
            
        }

        List<Move> moves= board.getMoves();

        Move bestMove = null;
        double bestScore = 0;
            if(player == board.currentPlayer()){
                bestScore = - Double.MAX_VALUE;
                for(Move m:moves) {
                    Board newBoard= board.makeMove(m);
                    AlphaBetaEvaluatedMove opponentMove = getBestMove(newBoard,evaluator,player,depth+1,alpha,beta);
                    
                    if ((bestScore<opponentMove.getScore())){
                        bestMove = m;
                        bestScore = opponentMove.getScore();
                        if(bestScore>alpha){
                            alpha = bestScore;
                        }
                    }
                    if(alpha>=beta){
                        break;
                    }
                }   
            }
            else{
                bestScore =  Double.MAX_VALUE;
                for(Move m:moves) {
                    Board newBoard= board.makeMove(m);
                    AlphaBetaEvaluatedMove opponentMove = getBestMove(newBoard,evaluator,player,depth+1,alpha,beta);
                    if (( bestScore>opponentMove.getScore())){
                        bestMove = m;
                        bestScore = opponentMove.getScore();
                        if(bestScore<=beta){
                            beta = bestScore;
                        }
                    }
                    if(alpha>=beta){
                        break;
                    }
                }
            }
        AlphaBetaEvaluatedMove em = new AlphaBetaEvaluatedMove(bestMove,bestScore,alpha,beta);
        table.put(key, new AlphaBetaEntry(em, maxDepth-depth, alpha, beta));
        return new AlphaBetaEvaluatedMove(bestMove,bestScore,alpha,beta);
    }
    
}
