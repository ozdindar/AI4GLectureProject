/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.AliAkçekoce;

import java.util.List;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

/**
 *
 * @author ALI
 */
public class Alpha implements BoardAI {
    int maxDepth;

    public Alpha(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        AlphaBetaEvaluatedMove em =getBestMove(board,evaluator, board.currentPlayer(),0,- Double.MAX_VALUE, Double.MAX_VALUE);

        return em.getMove();
    }

    private AlphaBetaEvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int player, int depth,double alpha,double beta) {

        if (board.isGameOver() || depth>maxDepth)
        {
            double score = evaluator.evaluate(board,player);
            return new AlphaBetaEvaluatedMove(null,score,alpha,beta);
            
        }

        List<Move> moves= board.getMoves();

        Move bestMove = null;
        double bestScore = 0;
            if(player == board.currentPlayer()){
                bestScore = - Double.MAX_VALUE;
                for(Move m:moves) {
                    Board newBoard= board.makeMove(m);
                    AlphaBetaEvaluatedMove opponentMove = getBestMove(newBoard,evaluator,player,depth+1,alpha,beta);
                    
                    if (( player == board.currentPlayer()&& bestScore<opponentMove.getScore())){
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
                    if (( player != board.currentPlayer()&& bestScore>opponentMove.getScore())){
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
        return new AlphaBetaEvaluatedMove(bestMove,bestScore,alpha,beta);
    }
}
/*double bestScore = (player == board.currentPlayer()) ? - Double.MAX_VALUE: Double.MAX_VALUE;

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
*/