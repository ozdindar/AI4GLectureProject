package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

import java.util.ArrayList;
import java.util.List;

class King extends ChessPiece
{
    @Override
    boolean canMove(PieceType[][] board, int player, int fr, int fc, int tr, int tc) {
        int dr = Math.abs(fr-tr);
        int dc = Math.abs(fc-tc);
        if (dr>1 || dc>1)
            return false;
        if (dr==0 && dc==0)
            return false;
        return board[tr][tc].toPlayer()!=player;
    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(PieceType[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();
        if (r<7 && board[r+1][c].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r+1,c));
        if (r>0 && board[r-1][c].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r-1,c));
        if (c<7 && board[r][c+1].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r,c+1));
        if (c>0 && board[r][c-1].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r,c-1));

        if (r<7 && c<7 && board[r+1][c+1].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r+1,c+1));
        if (r>0 && c>0&& board[r-1][c-1].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r-1,c-1));
        if (c<7 && r>0 && board[r-1][c+1].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r-1,c+1));
        if (c>0 && r<7 && board[r+1][c-1].toPlayer()!= player)
            moves.add(new ChessMove(r,c,r+1,c-1));

        return moves;
    }


}
