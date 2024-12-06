package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

import java.util.ArrayList;
import java.util.List;

class Queen extends ChessPiece
{
    @Override
    boolean canMove(PieceType[][] board, int player, int fr, int fc, int tr, int tc) {
        return board[tr][tc].toPlayer()!=player&& Chess.clearPath(board,fr,fc,tr,tc) ;
    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(PieceType[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();

        // ROOK MOVES
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

        // BISHOP MOVES
        for (int i = 1; i < 8 && r-i>=0 && c-i>=0 ; i++) {
            if (  canMove(board,player,r,c,r-i,c-i))
                moves.add(new ChessMove(r,c,r-i,c-i));
            else break;
        }

        for (int i = 1; i < 8 && r+i<8 && c+i<8 ; i++) {
            if (canMove(board,player,r,c,r+i,c+i))
                moves.add(new ChessMove(r,c,r+i,c+i));
            else break;
        }

        for (int i = 1; i < 8 && r+i<8 && c-i>=0 ; i++) {
            if (canMove(board,player,r,c,r+i,c-i))
                moves.add(new ChessMove(r,c,r+i,c-i));
            else break;
        }
        for (int i = 1; i < 8 && r-i>=0 && c+i<8 ; i++) {
            if (canMove(board,player,r,c,r-i,c+i))
                moves.add(new ChessMove(r,c,r-i,c+i));
            else break;
        }
        return moves;
    }
}