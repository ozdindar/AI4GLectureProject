package live.boardgames.chss.internal.knowledge;

import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.tictactoe.TicTacToeCell;
import live.boardgames.tictactoe.TicTacToeMove;
import live.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;


public class Chess {


    static long zobristBoardValues[][][];
    static long zobristTurnValues[];



    public static long zobristKey(PieceType[][] board, int player) {
        if (zobristBoardValues == null)
            initializeZobrist();

        long key = 0;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                key ^= zobristBoardValues[board[r][c].ordinal()][r][c];
            }
        }
        key ^= zobristTurnValues[player];
        return key;
    }

    private static void initializeZobrist() {

        zobristBoardValues = new long[13][8][8];

        for (int i = 0; i < 13; i++) {
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++)
                {

                    zobristBoardValues[i][r][c] = RandomUtils.randomLong();
                }
            }
        }

        zobristTurnValues = new long[2];
        for (int i = 0; i < zobristTurnValues.length; i++) {
            zobristTurnValues[i] = RandomUtils.randomLong();
        }
    }

    public static long updateKey(long key, ChessMove m, PieceType piece, PieceType oldTarget, int player) {
        key ^= zobristBoardValues[piece.ordinal()][m.frow][m.fcol];
        key ^= zobristBoardValues[oldTarget.ordinal()][m.trow][m.tcol];
        key ^= zobristBoardValues[piece.ordinal()][m.trow][m.tcol];

        key ^= zobristTurnValues[player];
        key ^= zobristTurnValues[opponent(player)];
        return key;
    }


    public static final int WHITE =0;
    public static final int BLACK =1;
    public static final int EMPTY =2;

    private final static PieceType InitialBoard[][] =
            {       { PieceType.WRook, PieceType.WKnight, PieceType.WBishop, PieceType.WQueen, PieceType.WKing, PieceType.WBishop, PieceType.WKnight, PieceType.WRook},
                    { PieceType.WPawn, PieceType.WPawn, PieceType.WPawn, PieceType.WPawn, PieceType.WPawn, PieceType.WPawn, PieceType.WPawn, PieceType.WPawn},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.BPawn, PieceType.BPawn, PieceType.BPawn, PieceType.BPawn, PieceType.BPawn, PieceType.BPawn, PieceType.BPawn, PieceType.BPawn},
                    { PieceType.BRook, PieceType.BKnight, PieceType.BBishop, PieceType.BQueen, PieceType.BKing, PieceType.BBishop, PieceType.BKnight, PieceType.BRook}
            };

    private final static PieceType EmptyBoard[][] =
            {       { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
            };

    public static PieceType[][] initialBoard() {
        return cloneBoard(InitialBoard);
    }

    public static PieceType[][] emptyBoard() {
        return cloneBoard(EmptyBoard);
    }

    private static PieceType[][] cloneBoard(PieceType[][] board) {
        PieceType newBoard[][] = new PieceType[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                newBoard[r][c]= board[r][c];
            }
        }
        return newBoard;
    }

    public static List<Move> getMoves(PieceType[][] board, int player) {

        List<Move> moves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c].toPlayer()!= player)
                    continue;
                ChessPiece piece = PieceFactory.pieces[board[r][c].ordinal()];
                moves.addAll(piece.getMoves(board,player,r,c));
            }

        }

        /*todo*/
        return moves;
    }

    public static boolean isValid(PieceType[][] board, int player, ChessMove m) {
        PieceType fromPiece = board[m.frow][m.fcol];
        PieceType target = board[m.trow][m.tcol];
        if (fromPiece == PieceType.Empty || fromPiece.toPlayer()!= player)
            return false;
        if (target.toPlayer()== player)
            return false;

        ChessPiece piece = PieceFactory.create(fromPiece);

        if (!piece.canMove(board,player,m.frow,m.fcol,m.trow,m.tcol))
            return false;

        PieceType[][] boardAfter = boardAfter(board,m);

        return !kingIsInCheck(boardAfter,player);

    }

    public static boolean kingIsInCheck(PieceType[][] board, int player) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c].toPlayer()==opponent(player))
                {
                    ChessPiece opponentPiece = PieceFactory.create(board[r][c]);
                    List<Move>moves = opponentPiece.getMovesWithoutKingCheckControl(board,opponent(player),r,c);
                    for (Move m:moves) {
                        ChessMove cm = (ChessMove) m;
                        if (board[cm.trow][cm.tcol]==PieceType.WKing || board[cm.trow][cm.tcol]==PieceType.BKing) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static PieceType[][] boardAfter(PieceType[][] board, ChessMove move) {
        PieceType[][] boardAfter = cloneBoard(board);
        boardAfter[move.frow][move.fcol]= PieceType.Empty;
        boardAfter[move.trow][move.tcol]= board[move.frow][move.fcol];
        return boardAfter;
    }


    public static int opponent(int player) {
        if (player == WHITE)
            return BLACK;
        else if (player == BLACK)
            return WHITE;
        else return EMPTY;
    }

    public static boolean isGameOver(PieceType[][] board, int player) {
        return getMoves(board,player).isEmpty();
    }

    static boolean clearPath(PieceType[][] board, int fr, int fc, int tr, int tc) {
        int dr = Math.abs(fr-tr);
        int dc = Math.abs(fc-tc);

        if ( !( dr==0|| dc==0 ||  dr ==dc))
            return false;

        int sr = (tr>fr) ? 1 : (tr<fr)? -1 : 0;
        int sc = (tc>fc) ? 1 : (tc<fc)? -1 : 0;


        for (int r=fr+sr, c = fc+sc; !(r==tr && c==tc ); r+=sr, c+=sc  )
        {
            if (board[r][c]!= PieceType.Empty)
                return false;
        }

        return true;
    }





    // Returns if the move is either a pawn move or a capture.
    // Used for 50-moves rule
    public static boolean isSignificantMove(PieceType[][] board,Move m) {
        ChessMove cm = (ChessMove) m;

        PieceType piece = board[cm.frow][cm.fcol];
        PieceType target = board[cm.trow][cm.tcol];
        return piece==PieceType.WPawn || piece== PieceType.BPawn || target != PieceType.Empty;
    }

    public static boolean isCaptureMove(PieceType[][] board,Move m) {
        ChessMove cm = (ChessMove) m;

        PieceType piece = board[cm.frow][cm.fcol];
        PieceType target = board[cm.trow][cm.tcol];
        return  target != PieceType.Empty;
    }

    public static void updateBoard(PieceType[][] board, ChessMove move) {

        PieceType piece = board[move.frow][move.fcol];
        board[move.frow][move.fcol]= PieceType.Empty;
        board[move.trow][move.tcol]= piece;

        // PROMOTION
        if (piece== PieceType.BPawn && move.trow==0)
            board[move.trow][move.tcol]= PieceType.BQueen;
        if (piece== PieceType.WPawn && move.trow==7)
            board[move.trow][move.tcol]= PieceType.WQueen;
    }




    private final static PieceType TestBoard[][] =
            {       { PieceType.WRook, PieceType.WKnight, PieceType.WBishop, PieceType.WQueen, PieceType.WKing, PieceType.WBishop, PieceType.WKnight, PieceType.WRook},
                    { PieceType.Empty, PieceType.WPawn, PieceType.WPawn, PieceType.Empty, PieceType.Empty, PieceType.WPawn, PieceType.WPawn, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.WPawn, PieceType.WPawn, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.BPawn, PieceType.BPawn, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty, PieceType.Empty},
                    { PieceType.Empty, PieceType.BPawn, PieceType.BPawn, PieceType.Empty, PieceType.Empty, PieceType.BPawn, PieceType.BPawn, PieceType.Empty},
                    { PieceType.BRook, PieceType.BKnight, PieceType.BBishop, PieceType.BQueen, PieceType.BKing, PieceType.BBishop, PieceType.BKnight, PieceType.BRook}
            };

    public static void main(String[] args) {

       // speedTest();

       // testPosition();

        testZobrist();

    }


    private static void testZobrist()
    {
        ChessBoard cb1 = new ChessBoard();
        ChessBoard cb2 = new ChessBoard();

        ChessMove cm1 = new ChessMove(1,0,2,0);
        ChessMove cm2 = new ChessMove(1,1,2,1);
        ChessMove cm3 = new ChessMove(6,0,5,0);

        cb1.perform(cm1);
        cb1.perform(cm3);
        cb1.perform(cm2);

        cb2.perform(cm2);
        cb2.perform(cm3);
        cb2.perform(cm1);

        System.out.println("B1 : "+ cb1.getKey());
        System.out.println("B2 : "+ cb2.getKey());
    }

    private static void testPosition() {
        PieceType board[][] = emptyBoard().clone();

        board[5][3] = PieceType.BQueen;
        board[3][5] = PieceType.WKing;



        System.out.println(kingIsInCheck(board,WHITE));
        List<Move> moves = getMoves(board,WHITE);

        System.out.println(moves.size());




    }


    private static void speedTest() {
        PieceType board[][] = TestBoard.clone();

        for (int i = 0; i < 10000; i++) {
            List<Move> moves = getMoves(board,WHITE);
        }


        List<Move> moves = null;



        long elapsed = System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            moves = getMoves(board,WHITE);
        }
        System.out.println("Average Time:" + (double)(System.currentTimeMillis()-elapsed)/2000.0);

        System.out.println(moves.size());
    }
}
