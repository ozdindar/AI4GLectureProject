package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;

import java.util.ArrayList;
import java.util.List;

import static live.boardgames.chss.internal.knowledge.Chess.*;

class Pawn extends ChessPiece
{
    @Override
    boolean canMove(PieceType[][] board, int player, int fr, int fc, int tr, int tc) {

        if (  tr>=8 || tc>=8 || tr<0 || tc<0 || board[tr][tc].toPlayer()==player)
            return false;

        if (player==WHITE) {
            if (tr-fr==1)
                return ( (tc==fc && board[tr][tc]== PieceType.Empty ) ||
                         (Math.abs(tc-fc)==1 && board[tr][tc].toPlayer()== opponent(player) ) );

            return (tr-fr == 2 && board[fr+1][fc]== PieceType.Empty&& board[tr][tc]==PieceType.Empty && fr ==1);
        }
        else {
            if (tr-fr==-1)
                return ( (tc==fc && board[tr][tc]== PieceType.Empty ) ||
                        (Math.abs(tc-fc)==1 && board[tr][tc].toPlayer()== opponent(player) ) );

            return (tr-fr == -2 && board[fr-1][fc]==PieceType.Empty&&  board[tr][tc]==PieceType.Empty && fr ==6);
        }


    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(PieceType[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();
        if (player==WHITE && canMove(board,player,r,c ,r+1,c))
            moves.add(new ChessMove(r,c,r+1,c));
        if (player==WHITE && canMove(board,player,r,c ,r+2,c))
            moves.add(new ChessMove(r,c,r+2,c));
        if (player==WHITE && canMove(board,player,r,c ,r+1,c+1))
            moves.add(new ChessMove(r,c,r+1,c+1));
        if (player==WHITE && canMove(board,player,r,c ,r+1,c-1))
            moves.add(new ChessMove(r,c,r+1,c-1));

        if (player==BLACK && canMove(board,player,r,c ,r-1,c))
            moves.add(new ChessMove(r,c,r-1,c));
        if (player==BLACK && canMove(board,player,r,c ,r-2,c))
            moves.add(new ChessMove(r,c,r-2,c));
        if (player==BLACK && canMove(board,player,r,c ,r-1,c-1))
            moves.add(new ChessMove(r,c,r-1,c-1));
        if (player==BLACK && canMove(board,player,r,c ,r-1,c+1))
            moves.add(new ChessMove(r,c,r-1,c+1));


        return moves;
    }
}
