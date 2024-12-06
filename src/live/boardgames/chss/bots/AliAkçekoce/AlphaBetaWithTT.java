
package live.boardgames.chss.bots.AliAkçekoce;

import live.boardgames.chss.bots.AliAkçekoce.AlphaBetaEvaluatedMove;
import live.boardgames.chss.bots.AliAkçekoce.AlphaBetaEntry;
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
public class AlphaBetaWithTT implements BoardAI {
    int maxDepth;
    int currentDepth;
   // AlphaBetaEvaluatedMove alphabeta;
    TranspositionTable<AlphaBetaEntry> table = new TranspositionTable<>();
    private long trueHitCount=0;
    private long falseHitCount=0;
    
    public AlphaBetaWithTT(int maxDepth) {
        this.maxDepth = maxDepth;
        
    }

   @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
       
        AlphaBetaEvaluatedMove  alphabeta =getBestMove(board,evaluator, board.currentPlayer(),0,- Double.MAX_VALUE, Double.MAX_VALUE);
       

        return alphabeta.getMove();
    }

    private AlphaBetaEvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int player, int depth,double alpha,double beta) {
        
        long key = board.getKey();
        if(table.contains(key))
        {
            AlphaBetaEntry entry = table.get(key);
      
            if(entry.alpha <= alpha && entry.beta >= beta){
                System.out.println("True Hit Count for TT:" + trueHitCount++);
                return entry.evaluatedMove;
            }                
        }
            // else       
             //   System.out.println("False Hit Count:" + falseHitCount++);
             
        table.remove(key);
        
        if (board.isGameOver() || depth>maxDepth)
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