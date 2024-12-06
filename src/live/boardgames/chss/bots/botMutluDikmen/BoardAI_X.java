package live.boardgames.chss.bots.botMutluDikmen;

//15070006003 Mutlu Dikmen

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.*;
import live.util.RandomUtils;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class BoardAI_X implements BoardAI {

    int searchCount;
    double alpha = Double.MIN_VALUE;
    double beta = Double.MAX_VALUE;
    long startTime;

    ArrayList<Long> zobristArray;

    public BoardAI_X() {
        this.searchCount = 6;
        zobristArray = new ArrayList<>();
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        startTime = System.currentTimeMillis();
        EvaluatedMove em = AI_X((ChessBoard) board, evaluator, alpha, beta);
        return em.getMove();

    }

    private EvaluatedMove AI_X(ChessBoard board, BoardEvaluator evaluator, double alpha, double beta) {

        Move currentMove = null;
        Move bestMove = null;
        double bestScore = alpha;
        double currentScore = 0.0;
        ChessBoard newBoard = null;
        List<Move> moves = board.getMoves();
        long currentTime;

        for (int i = 0; i < searchCount; i++) {



            if (board.isGameOver()) {
                return new EvaluatedMove(null, evaluator.evaluate(board, board.currentPlayer()));
            }

            bestScore = alpha;

            for (int j = 0; j < moves.size(); j++) {

                currentTime = System.currentTimeMillis();
                currentMove = moves.get(j);
                newBoard = (ChessBoard) board.makeMove(currentMove);
                currentScore = -evaluator.evaluate(newBoard, newBoard.currentPlayer());

                if (currentTime - startTime > 9999) {
                    int x = RandomUtils.randomInt(moves.size());
                    return new EvaluatedMove(moves.get(x), evaluator.evaluate(board, board.currentPlayer()));
                }

                if (Chess.isGameOver(newBoard.getBoard(), newBoard.currentPlayer())) {
                    return new EvaluatedMove(currentMove, newBoard.currentPlayer());
                }

                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    bestMove = currentMove;
                }

                if (bestScore >= beta) break;
            }
            alpha = -beta;
            beta = -max(alpha, bestScore);

        }

        if (checkZobrist(board))
            return replaceEvaluatedMove(board, evaluator, moves);
        return new EvaluatedMove(bestMove, bestScore);


    }


    private boolean checkZobrist(ChessBoard board) {
        long key = Chess.zobristKey(board.getBoard(), board.currentPlayer());
        for (Long k : zobristArray
        ) {
            if (k.equals(key)) {
                return true;
            }
        }
        zobristArray.add(Chess.zobristKey(board.getBoard(), board.currentPlayer()));
        return false;
    }

    private EvaluatedMove replaceEvaluatedMove(ChessBoard board, BoardEvaluator evaluator, List<Move> moveList) {

        int i = RandomUtils.randomInt(moveList.size());

        return new EvaluatedMove(moveList.get(i), evaluator.evaluate(board, board.currentPlayer()));

    }

}
