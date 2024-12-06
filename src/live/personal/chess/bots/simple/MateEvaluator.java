package live.personal.chess.bots.simple;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;

public class MateEvaluator implements BoardEvaluator {
    @Override
    public double evaluate(Board board, int player) {
        ChessBoard cb = (ChessBoard) board;

        if (cb.isGameOver()&&!cb.drawnBy50MoveRule())
        {
            if (Chess.kingIsInCheck(cb.getBoard(),player))
            {
                return -100000 + cb.getMoveCount();
            }
            else if (Chess.kingIsInCheck(cb.getBoard(),Chess.opponent(player)))
                return 100000- cb.getMoveCount();

        }
        return 0;
    }
}
