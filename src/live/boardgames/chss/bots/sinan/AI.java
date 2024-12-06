package live.boardgames.chss.bots.sinan;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class AI implements BoardAI {

    int maxDepth;


    public AI(int maxDepth) {
        this.maxDepth = maxDepth;
    }



    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        EvaluatedMove em = getBestMove(board, evaluator,board.currentPlayer(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return em.getMove();
    }


    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator,int player, int depth, double alpha, double beta) {


        if (board.isGameOver() || depth > maxDepth) {
            double score = evaluator.evaluate(board, player);
            return new EvaluatedMove(null, score);
        }

        List<Move> moves = board.getMoves();
        Move bestMove = null;
        double bestScore = (player == board.currentPlayer()) ? - Double.MAX_VALUE: Double.MAX_VALUE;

        for (Move m : moves) {

            Board newBoard = board.makeMove(m);
            EvaluatedMove opponentMove = getBestMove(newBoard, evaluator, player, depth + 1, alpha, beta);

            if ((board.currentPlayer() == player )) { //max

                if (opponentMove.getScore() >= bestScore) {
                    bestMove = m;
                    bestScore = opponentMove.getScore();
                }

                 alpha = Math.max(alpha, opponentMove.getScore());

                if (beta <= alpha)
                    return new EvaluatedMove(bestMove,bestScore);


            }
            else  {  //min

                    if (opponentMove.getScore() <= bestScore) {
                        bestMove = m;
                        bestScore = opponentMove.getScore();
                    }


                    beta = Math.min(beta, opponentMove.getScore());

                    if (beta <= alpha)
                        return new EvaluatedMove(bestMove, bestScore);

                }


            }

        return new EvaluatedMove(bestMove, bestScore);
        }

}







/*

if ((board.currentPlayer() == player) ) { //maximizer

            for (Move m : moves) {

                Board newBoard = board.makeMove(m);

                EvaluatedMove opponentMove = getBestMove(newBoard, evaluator, player+1, depth + 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

                if ( opponentMove.getScore() >= bestScore) {
                    bestScore = opponentMove.getScore();
                    bestMove = m;
                }

                alpha = Math.max(alpha, opponentMove.getScore());

                if(beta <= alpha)
                    return new EvaluatedMove(bestMove, bestScore);
            }
            return new EvaluatedMove(bestMove, bestScore);
        }

        else {  //minimizer



            for (Move m : moves) {

                Board newBoard = board.makeMove(m);

                EvaluatedMove opponentMove = getBestMove(newBoard, evaluator, player-1 , depth + 1, alpha, beta);

                if ( opponentMove.getScore() <= bestScore) {
                    bestScore = opponentMove.getScore();
                    bestMove = m;
                }
                beta = Math.min(beta, opponentMove.getScore());

                if(alpha <= beta)
                    return new EvaluatedMove(bestMove, bestScore);
            }
            return new EvaluatedMove(bestMove, bestScore);

        }



*/
