package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

import java.util.ArrayList;
import java.util.List;

class Knight extends ChessPiece
{
    @Override
    boolean canMove(PieceType[][] board, int player, int fr, int fc, int tr, int tc) {
        int dr = Math.abs(fr-tr);
        int dc = Math.abs(fc-tc);

        return  ((dr==2 && dc==1)||(dr==1 && dc==2)) && board[tr][tc].toPlayer()!=player;

    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(PieceType[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();

        if (r+2<8 && c+1<8 && board[r+2][c+1].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r+2,c+1));
        if (r+1<8 && c+2<8 && board[r+1][c+2].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r+1,c+2));
        if (r-2>=0 && c+1<8 && board[r-2][c+1].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r-2,c+1));
        if (r+2<8 && c-1>=0 && board[r+2][c-1].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r+2,c-1));
        if (r-1>=0 && c+2<8 && board[r-1][c+2].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r-1,c+2));
        if (r+1<8 && c-2>=0 && board[r+1][c-2].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r+1,c-2));
        if (r-1>=0 && c-2>=0 && board[r-1][c-2].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r-1,c-2));
        if (r-2>=0 && c-1>=0 && board[r-2][c-1].toPlayer()!=player )
            moves.add(new ChessMove(r,c,r-2,c-1));

        return moves;
    }
}
