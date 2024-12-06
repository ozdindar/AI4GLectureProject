package live.boardgames.chss.internal;

import live.boardgames.base.AbstractBoard;
import live.boardgames.base.Board;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.ChessMove;
import live.boardgames.chss.internal.knowledge.PieceType;

import java.util.List;

public class ChessBoard extends AbstractBoard {

    PieceType board[][];
    long key;

    int winner;

    @Override
    public int currentPlayer() {
        return super.currentPlayer();
    }

    int lastSignificantMove;

    public ChessBoard()
    {
        super();
        board = Chess.initialBoard();
        key = Chess.zobristKey(board,currentPlayer());
    }

    public ChessBoard(PieceType board[][], int player)
    {
        super();
        this.board = board;
        this.currentPlayer = player;

        key = Chess.zobristKey(board,currentPlayer());
    }

    public ChessBoard(ChessBoard other) {
        super(other);
        board = new PieceType[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = other.board[r][c];
            }
        }
        lastSignificantMove = other.lastSignificantMove;
        winner = other.winner;
        key = other.key;
    }

    @Override
    public List<Move> getMoves() {
        return Chess.getMoves(board,currentPlayer());
    }


    @Override
    public void perform(Move m) {
        if (Chess.isSignificantMove(board,m))
            lastSignificantMove = moveCount;
        super.perform(m);
    }

    public boolean drawnBy50MoveRule()
    {
        return (moveCount-lastSignificantMove>100);
    }

    @Override
    public boolean isGameOver() {
        if (drawnBy50MoveRule())//50 move rule
        {
            winner= -1;
            return true;
        }
        boolean gameOver= Chess.isGameOver(board,currentPlayer());

        if (gameOver)
        {
            if( Chess.kingIsInCheck(board,currentPlayer()))
            {
                winner= Chess.opponent(currentPlayer());
            }
            else winner = -1;
        }
        return gameOver;
    }

    @Override
    public boolean isValid(Move m) {
        if (!(m instanceof ChessMove))
            return false;
        return Chess.isValid(board, currentPlayer(),(ChessMove) m);
    }

    @Override
    public int playerCount() {
        return 2;
    }

    @Override
    public void init() {
        super.init();
        board = Chess.initialBoard();
        lastSignificantMove =0;
        winner=-1;
    }

    @Override
    public int winner() {
        return winner;
    }

    @Override
    public long getKey() {
        /* todo:*/

        return key;
    }


    /*Assumes the move is a valid move!
    * */
    @Override
    protected void _updateBoard(Move m) {
        ChessMove cm = (ChessMove) m;
        PieceType piece = board[cm.getFrow()][cm.getFcol()];
        PieceType target = board[cm.getTrow()][cm.getTcol()];
        Chess.updateBoard(board,cm);

        key = Chess.updateKey(key,cm,piece,target,currentPlayer());
    }

    @Override
    public Board cloneBoard() {

        return new ChessBoard(this);
    }

    public PieceType get(int r, int c) {
        return board[r][c];
    }

  public PieceType[][] getBoard()
  {
      return board;
  }
}
