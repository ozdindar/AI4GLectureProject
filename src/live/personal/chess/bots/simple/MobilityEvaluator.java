package live.personal.chess.bots.simple;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;

public class MobilityEvaluator implements BoardEvaluator {
    private static final double MobilityCoefficient = 0.1;

    @Override
    public double evaluate(Board board, int player) {
        ChessBoard cb = (ChessBoard) board;
        int turn = cb.currentPlayer();
        int moveCount =cb.getMoves().size();
        return (turn==player)? MobilityCoefficient*moveCount:-MobilityCoefficient*moveCount;
    }
}
