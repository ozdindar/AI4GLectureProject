package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

import java.util.ArrayList;
import java.util.List;

class Rook extends ChessPiece
{
    @Override
    boolean canMove(PieceType[][] board, int player, int fr, int fc, int tr, int tc) {
        if ( fr!=tr && fc!=tc)
            return false;
        return Chess.clearPath(board,fr,fc,tr,tc) && board[tr][tc].toPlayer()!=player;
    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(PieceType[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();
        for (int i = 1; r+i<8  ; i++) {
            if ( canMove(board,player,r,c,r+i,c))
                moves.add(new ChessMove(r,c,r+i,c));
            else break;
        }
        for (int i = 1; r-i>=0  ; i++) {
            if ( canMove(board,player,r,c,r-i,c))
                moves.add(new ChessMove(r,c,r-i,c));
            else break;
        }

        for (int i = 1; c+i<8  ; i++) {
            if ( canMove(board,player,r,c,r,c+i))
                moves.add(new ChessMove(r,c,r,c+i));
            else break;
        }
        for (int i = 1; c-i>=0  ; i++) {
            if ( canMove(board,player,r,c,r,c-i))
                moves.add(new ChessMove(r,c,r,c-i));
            else break;
        }

        return moves;
    }


    protected List<Move> getMovesWithoutKingCheckControl_Old(PieceType[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();
        for (int i = 0; i < 8 ; i++) {
            if (i==r) continue;
            if ( canMove(board,player,r,c,i,c))
                moves.add(new ChessMove(r,c,i,c));
        }

        for (int i = 0; i < 8 ; i++) {
            if (i==c) continue;
            if ( canMove(board,player,r,c,r,i))
                moves.add(new ChessMove(r,c,r,i));
        }

        return moves;
    }
}
