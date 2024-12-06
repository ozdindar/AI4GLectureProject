package live.boardgames.chss.bots.AlaraIscan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.PieceType;

public class FridaEvaluator implements BoardEvaluator {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    //Values get from chessprogramming wikispaces
    private static final int MATE = 200000;
    private static final int pawnValue = 100;
    private static final int knightValue = 310;
    private static final int bishopValue = 330;
    private static final int rookValue = 500;
    private static final int queenValue = 900;
    private static final int kingValue = MATE;


    private static final int pawnPosWhite[][] =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {5, 10, 10, -20, -20, 10, 10, 5},
                    {5, -5, -10, 0, 0, -10, -5, 5},
                    {0, 0, 0, 20, 20, 0, 0, 0},
                    {5, 5, 10, 25, 25, 10, 5, 5},
                    {10, 10, 20, 30, 30, 20, 10, 10},
                    {50, 50, 50, 50, 50, 50, 50, 50},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };

    private static final int pawnPosBlack[][] =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {50, 50, 50, 50, 50, 50, 50, 50},
                    {10, 10, 20, 30, 30, 20, 10, 10},
                    {5, 5, 10, 25, 25, 10, 5, 5},
                    {0, 0, 0, 20, 20, 0, 0, 0},
                    {5, -5, -10, 0, 0, -10, -5, 5},
                    {5, 10, 10, -20, -20, 10, 10, 5},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };

    private static final int knightPosWhite[][] =
            {
                    {-50, -40, -30, -30, -30, -30, -40, -50},
                    {-40, -20, 0, 5, 5, 0, -20, -40},
                    {-30, 5, 10, 15, 15, 10, 5, -30},
                    {-30, 0, 15, 20, 20, 15, 0, -30},
                    {-30, 5, 15, 20, 20, 15, 5, -30},
                    {-30, 0, 10, 15, 15, 10, 0, -30},
                    {-40, -20, 0, 0, 0, 0, -20, -40},
                    {-50, -40, -30, -30, -30, -30, -40, -50}
            };

    private static final int knightPosBlack[][] =
            {
                    {-50, -40, -30, -30, -30, -30, -40, -50},
                    {-40, -20, 0, 0, 0, 0, -20, -40},
                    {-30, 0, 10, 15, 15, 10, 0, -30},
                    {-30, 5, 15, 20, 20, 15, 5, -30},
                    {-30, 0, 15, 20, 20, 15, 0, -30},
                    {-30, 5, 10, 15, 15, 10, 5, -30},
                    {-40, -20, 0, 5, 5, 0, -20, -40},
                    {-50, -40, -30, -30, -30, -30, -40, -50}
            };

    private static final int bishopPosWhite[][] =
            {
                    {-20, -10, -10, -10, -10, -10, -10, -20},
                    {-10, 5, 0, 0, 0, 0, 5, -10},
                    {-10, 10, 10, 10, 10, 10, 10, -10},
                    {-10, 0, 10, 10, 10, 10, 0, -10},
                    {-10, 5, 5, 10, 10, 5, 5, -10},
                    {-10, 0, 5, 10, 10, 5, 0, -10},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-20, -10, -10, -10, -10, -10, -10, -20}
            };

    private static final int bishopPosBlack[][] =
            {
                    {-20, -10, -10, -10, -10, -10, -10, -20},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-10, 0, 5, 10, 10, 5, 0, -10},
                    {-10, 5, 5, 10, 10, 5, 5, -10},
                    {-10, 0, 10, 10, 10, 10, 0, -10},
                    {-10, 10, 10, 10, 10, 10, 10, -10},
                    {-10, 5, 0, 0, 0, 0, 5, -10},
                    {-20, -10, -10, -10, -10, -10, -10, -20}
            };

    private static final int rookPosWhite[][] =
            {
                    {0, 0, 0, 5, 5, 0, 0, 0},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {5, 10, 10, 10, 10, 10, 10, 5},
                    {0, 5, 5, 5, 5, 5, 5, 0}
            };

    private static final int rookPosBlack[][] =
            {
                    {0, 5, 5, 5, 5, 5, 5, 0},
                    {5, 10, 10, 10, 10, 10, 10, 5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {-5, 0, 0, 0, 0, 0, 0, -5},
                    {0, 0, 0, 5, 5, 0, 0, 0}
            };

    private static final int queenPosWhite[][] =
            {
                    {-20, -10, -10, -5, -5, -10, -10, -20},
                    {-10, 0, 5, 0, 0, 0, 0, -10},
                    {-10, 5, 5, 5, 5, 5, 0, -10},
                    {0, 0, 5, 5, 5, 5, 0, -5},
                    {-5, 0, 5, 5, 5, 5, 0, -5},
                    {-10, 0, 5, 5, 5, 5, 0, -10},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-20, -10, -10, -5, -5, -10, -10, -20}
            };

    private static final int queenPosBlack[][] =
            {
                    {-20, -10, -10, -5, -5, -10, -10, -20},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-10, 0, 5, 5, 5, 5, 0, -10},
                    {-5, 0, 5, 5, 5, 5, 0, -5},
                    {0, 0, 5, 5, 5, 5, 0, -5},
                    {-10, 5, 5, 5, 5, 5, 0, -10},
                    {-10, 0, 5, 0, 0, 0, 0, -10},
                    {-20, -10, -10, -5, -5, -10, -10, -20}
            };

    private static final int kingPosWhite[][] =
            {
                    {20, 30, 10, 0, 0, 10, 30, 20},
                    {20, 20, 0, 0, 0, 0, 20, 20},
                    {-10, -20, -20, -20, -20, -20, -20, -10},
                    {-20, -30, -30, -40, -40, -30, -30, -20},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30}
            };

    private static final int kingPosBlack[][] =
            {
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-30, -40, -40, -50, -50, -40, -40, -30},
                    {-20, -30, -30, -40, -40, -30, -30, -20},
                    {-10, -20, -20, -20, -20, -20, -20, -10},
                    {20, 20, 0, 0, 0, 0, 20, 20},
                    {20, 30, 10, 0, 0, 10, 30, 20}
            };



    @Override
    public double evaluate(Board board, int player) {

        ChessBoard cb = new ChessBoard((ChessBoard) board);
        PieceType[][] pieces = cb.getBoard();



        AlphaBetaPrun abPrun = new AlphaBetaPrun();



        int val = 0;
        val = getVal(board, pieces,player);

        return val;

    }

    private int getVal(Board board, PieceType[][] pieces,int player) {
        int val = 0;
        ChessBoard cbb = new ChessBoard((ChessBoard) board);
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                PieceType p = pieces[i][j];
                if (player == WHITE) {
                    switch (p) {
                        case WPawn:
                            val += pawnPosWhite[i][j] + pawnValue;
                            break;
                        case WBishop:
                            val += bishopPosWhite[i][j]+bishopValue;
                            break;
                        case WRook:
                            val += rookPosWhite[i][j]+rookValue;
                            break;
                        case WKnight:
                            val+= knightPosWhite[i][j]+knightValue;
                            break;
                        case WQueen:
                            val+= queenPosWhite[i][j]+queenValue;
                            break;
                        case WKing:
                            val+= kingPosWhite[i][j]+kingValue;
                            break;
                    }
                }
                else{
                    switch (p) {
                        case BPawn:
                            val += pawnPosBlack[i][j] + pawnValue;
                            break;
                        case BBishop:
                            val += bishopPosBlack[i][j] + bishopValue;
                            break;
                        case BRook:
                            val += rookPosBlack[i][j] + rookValue;
                            break;
                        case BKnight:
                            val += knightPosBlack[i][j] + knightValue;
                            break;
                        case BQueen:
                            val += queenPosBlack[i][j] + queenValue;
                            break;
                        case BKing:
                            val += kingPosBlack[i][j] + kingValue;
                            break;
                    }
                }
            }
        }
        return val;
    }




    public int mate() {
        return MATE;
    }

}
