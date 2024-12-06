package live.boardgames.chss.bots.atakan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.ChessMove;
import live.boardgames.chss.internal.knowledge.PieceType;

import java.util.List;

public class AlphaBeta_1 implements BoardAI {

    private int maxDepth;
    private Move bestMove;
    private ChessEvaluator evaluator;


    public AlphaBeta_1(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    private double maximizer(ChessBoard board, int depth, double alpha, double beta, int player) {

        if (depth == 0 || board.isGameOver()) {
            return evaluator.evaluate(board, player);
        }

        List<Move> moves = board.getMoves();


        for (Move move : moves) {
            ChessBoard nb = (ChessBoard) board.makeMove(move);
            ChessMove cm = (ChessMove) move;

            PieceType x = board.get(cm.getTrow(), cm.getTcol());
            double pv = evaluator.getPieceValue(x, cm.getTrow(), cm.getTcol());
            double pr = evaluator.getPieceValue(x, cm.getFrow(), cm.getFcol());
            double rating = minimizer(nb, depth - 1, alpha, beta, Chess.opponent(player));
            if (pr > pv)
                rating *= 1.0001;
            if (rating > alpha) {
                alpha = rating;
                if (depth == maxDepth) {
                    bestMove = move;
                }
            }

            if (alpha >= beta) {
                return alpha;
            }

        }
        return alpha;
    }


    private double minimizer(ChessBoard board, int depth, double alpha, double beta, int player) {
        if (depth == 0 || board.isGameOver()) {
            return evaluator.evaluate(board, player);
        }

        List<Move> moves = board.getMoves();

        for (Move move : moves) {

            ChessBoard nb = (ChessBoard) board.makeMove(move);
            double rating = maximizer(nb, depth - 1, alpha, beta, Chess.opponent(player));

            if (rating <= beta) {
                beta = rating;
            }

            if (alpha >= beta) {
                return beta;
            }
        }
        return beta;
    }


    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        ChessBoard b = (ChessBoard) board;
        this.evaluator = (ChessEvaluator) evaluator;
        int player = b.currentPlayer();

        maximizer(b, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
        return bestMove;
    }


}
