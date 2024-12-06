package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

import java.util.List;
import java.util.function.Predicate;

abstract class ChessPiece{
    abstract boolean canMove(PieceType board[][], int player, int fr, int fc, int tr, int tc);

    List<Move> getMoves(PieceType[][] board, int player, int r, int c)
    {
        List<Move> moves= getMovesWithoutKingCheckControl(board,player,r,c);

        moves.removeIf(new Predicate<Move>() {
            @Override
            public boolean test(Move move) {
                ChessMove m = (ChessMove) move;
                PieceType[][] boardAfter = Chess.boardAfter(board,m);
                return Chess.kingIsInCheck(boardAfter,player);

            }
        });
        return moves;
    }

    protected abstract List<Move> getMovesWithoutKingCheckControl(PieceType[][] board, int player, int r, int c);
}
