/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.azizreda;

import java.util.List;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;

/**
 *
 * @author aziz
 */
public class v2AI implements BoardAI {

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        Move bestMove = null;
        int depth = 3;
        /*int p_count = piece_count((ChessBoard) board);
        if(p_count < 8)
        {
            depth = 4;
        }*/
        
        double infinite = 100000;
        double best_val = -100000;
        
        
        List<Move> move_list = board.getMoves();
        for(Move move: move_list)
        {
            ChessBoard temp_board =(ChessBoard)board.cloneBoard();
            
            temp_board.perform(move);
            double eval_value = -negamax(temp_board,depth-1,-infinite,-best_val,evaluator);
            temp_board = (ChessBoard)board.cloneBoard();
            
            if(eval_value > best_val)
            {
                best_val = eval_value;
                bestMove = move;
            }
        }
        
        return bestMove;
    }
    
    public double negamax(ChessBoard board,int depth, double alpha, double beta,BoardEvaluator evaluator)
    {
        List<Move> move_list = board.getMoves();
        if(depth == 0)
        {
           return evaluator.evaluate(board, board.currentPlayer());
        }
        
        for(Move move: move_list)
        {
            ChessBoard temp_board =(ChessBoard)board.cloneBoard();
            
            temp_board.perform(move);
            double eval_value = -negamax(temp_board,depth-1,-beta,-alpha,evaluator);
            
            if(eval_value > alpha)
            {
                alpha = eval_value;
            }
            
            if(alpha >= beta)
            {
                break;
            }
        }
        
        return alpha;
    }
    
    public int piece_count(ChessBoard board)
    {
        int count = 0;
        for(int i=0;i<8;i++)
        {
            for(int k=0;k<8;k++)
            {
                if(board.get(i, k).toString() != "Empty")
                {
                    count ++;
                }
            }
        }
        return count;
    }
    
}
