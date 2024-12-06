package live.personal.chess.bots.simple;

import live.boardgames.base.Board;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.ChessMove;

public class SimpleChessMoveOrderer implements MoveOrderer {
    private ChessBoard board;

    @Override
    public int compare(Move o1, Move o2) {
        ChessMove cm1 = (ChessMove) o1;
        ChessMove cm2 = (ChessMove) o2;
        if (Chess.isCaptureMove(board.getBoard(),cm1))
        {
            if (Chess.isCaptureMove(board.getBoard(),cm2))
                return 0;
            else return -1;
        }
        if (Chess.isCaptureMove(board.getBoard(),cm2))
            return 1;
        return 0;
    }

    @Override
    public void setBoard(Board board) {
        this.board= (ChessBoard) board;
    }
}
