package live.boardgames.chss.bots.atakan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;

import java.util.List;

public class AlphaBeta_2 implements BoardAI {

    private final int maxDepth;
    private Move bestMove;
    private ChessEvaluator evaluator;

    public AlphaBeta_2(int maxDepth) {
        this.maxDepth = maxDepth;
        this.bestMove = null;
    }

    public double alphaBeta(ChessBoard board, int depth, double alpha, double beta, int side){

        boolean maximize = side == Chess.BLACK;

        if(depth++ == maxDepth){
            return (int) evaluator.evaluate(board,side);
        }

        List<Move> moves = board.getMoves();

        if(maximize){
            Move localBestMove = null;
            for(Move move : moves){
                ChessBoard nb = (ChessBoard) board.makeMove(move);
                double score = alphaBeta(nb,depth,alpha,beta,Chess.opponent(side));

                if(score > alpha){
                    alpha = score;
                    localBestMove = move;
                }

                if(beta <= alpha){
                    break;
                }
            }

            if(localBestMove != null){
                bestMove = localBestMove;
            }

            return alpha;
        }
        else{

            for(Move move : moves){
                ChessBoard nb = (ChessBoard) board.makeMove(move);
                double score = alphaBeta(nb,depth,alpha,beta,Chess.opponent(side));

                if(score < beta){
                    beta = score;
                }

                if(beta <= alpha){
                    break;
                }
            }

            return beta;
        }
    }
    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        ChessBoard b = (ChessBoard) board;
        this.evaluator = (ChessEvaluator) evaluator;
        alphaBeta(b,0, Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,board.currentPlayer());
        return bestMove;
    }
}
