package live.boardgames.chss.bots.HüseyinArdaAşık;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class ChessAI implements BoardAI {

    int maxDepth;

    public ChessAI(int maxDepth){
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        EvaluatedMove evaluatedMove = AlphaBeta(board,evaluator, board.currentPlayer(),maxDepth,-Integer.MAX_VALUE,Integer.MAX_VALUE);

        return evaluatedMove.getMove();
    }

    private EvaluatedMove AlphaBeta(Board board, BoardEvaluator evaluator, int player, int depth, double alpha, double beta ) {

        if (board.isGameOver() || depth == 0) {
            double score = evaluator.evaluate(board,player);
            return new EvaluatedMove(null,score);
        }

        List<Move> moves= board.getMoves();

        Move bestMove = null;

        for (Move move : moves) {

            Board newBoard = board.makeMove(move);

            double val = -AlphaBeta(newBoard, evaluator, player, depth - 1,-beta , -alpha).getScore();

            if (val >= beta) {
                return new EvaluatedMove(move, beta);
            }

            if (val > alpha) {
                bestMove = move;
                alpha = val;
            }
        }

        return new EvaluatedMove(bestMove,alpha);
    }


}
