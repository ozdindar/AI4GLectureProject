package live.boardgames.chss.bots.HüseyinArdaAşık;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.PieceType;

public class ChessBoardEvaluater implements BoardEvaluator {

    int score;
    int pieceValue;
    int tableScore;

    private static final int pawnValue      = 100;
    private static final int queenValue     = 975;
    private static final int rookValue      = 500;
    private static final int bishopValue    = 325;
    private static final int knightValue    = 320;
    private static final int kingValue      = 32767;

    private static int blackPawnTable[][] =
            {
                    {0,  0,  0,  0,  0,  0,  0,  0},
                    {5, 10, 15, 20, 20, 15, 10,  5},
                    {4,  8, 12, 16, 16, 12,  8,  4},
                    {0,  6,  9, 10, 10,  9,  6,  0},
                    {0,  4,  6, 10, 10,  6,  4,  0},
                    {0,  2,  3,  4,  4,  3,  2,  0},
                    {0,  0,  0, -5, -5,  0,  0,  0},
                    {0,  0,  0,  0,  0,  0,  0,  0}
            };


    private static int whitePawnTable[][] =
            {
                    {0,  0,  0,  0,  0,  0,  0,  0},
                    {0,  0,  0, -5, -5,  0,  0,  0},
                    {0,  2,  3,  4,  4,  3,  2,  0},
                    {0,  4,  6, 10, 10,  6,  4,  0},
                    {0,  6,  9, 10, 10,  9,  6,  0},
                    {4,  8, 12, 16, 16, 12,  8,  4},
                    {5, 10, 15, 20, 20, 15, 10,  5},
                    {0,  0,  0,  0,  0,  0,  0,  0}
            };

    private static int knightTable[][] =
            {
                    {-10, -5, -5, -5, -5, -5, -5,-10},
                    { -8,  0,  0,  3,  3,  0,  0, -8},
                    { -8,  0, 10,  8,  8, 10,  0, -8},
                    { -8,  0,  8, 10, 10,  8,  0, -8},
                    { -8,  0,  8, 10, 10,  8,  0, -8},
                    { -8,  0, 10,  8,  8, 10,  0, -8},
                    { -8,  0,  0,  3,  3,  0,  0, -8},
                    {-10, -5, -5, -5, -5, -5, -5,-10}
            };

    private static int bishopTable[][] =
            {
                    {-5, -5, -5, -5, -5, -5, -5, -5},
                    {-5, 10,  5,  8,  8,  5, 10, -5},
                    {-5,  5,  3,  8,  8,  3,  5, -5},
                    {-5,  3, 10,  3,  3, 10,  3, -5},
                    {-5,  3, 10,  3,  3, 10,  3, -5},
                    {-5,  5,  3,  8,  8,  3,  5, -5},
                    {-5, 10,  5,  8,  8,  5, 10, -5},
                    {-5, -5, -5, -5, -5, -5, -5, -5}
            };

    public int pieceValue(PieceType pieceType){
        switch (pieceType){
            case BPawn:
            case WPawn:
                pieceValue = pawnValue;
                break;
            case WBishop:
            case BBishop:
                pieceValue = bishopValue;
                break;
            case BKnight:
            case WKnight:
                pieceValue = knightValue;
                break;
            case WRook:
            case BRook:
                pieceValue = rookValue;
                break;
            case WQueen:
            case BQueen:
                pieceValue = queenValue;
                break;
            case BKing:
            case WKing:
                pieceValue = kingValue;
                break;
        }
        return pieceValue;
    }

    @Override
    public double evaluate(Board board, int player) {

        ChessBoard chessBoard = (ChessBoard) board;

        score = 0;
        tableScore=0;

        if(player == 1){
            for(int r = 0; r < 8; r++ ){
                for(int c = 0; c < 8; c++){
                    switch (chessBoard.get(r,c)){
                        case WPawn:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c)) + whitePawnTable[r][c];
                            break;
                        case WKnight:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c)) + knightTable[r][c];
                            break;
                        case WBishop:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c)) + bishopTable[r][c];
                            break;
                        case WRook:
                        case WKing:
                        case WQueen:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c));
                            break;
                        case BPawn:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c)) - blackPawnTable[r][c];
                            break;
                        case BKnight:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c)) - knightTable[r][c];
                            break;
                        case BBishop:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c)) - bishopTable[r][c];
                            break;
                        case BRook:
                        case BKing:
                        case BQueen:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c));
                            break;
                    }
                }
            }
        }else if(player == 0) {
            for(int r = 0; r < 8; r++ ){
                for(int c = 0; c < 8; c++){
                    switch (chessBoard.get(r,c)){
                        case BPawn:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c)) + blackPawnTable[r][c];
                            break;
                        case BKnight:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c)) + knightTable[r][c];
                            break;
                        case BBishop:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c)) + bishopTable[r][c];
                            break;
                        case BRook:
                        case BQueen:
                        case BKing:
                            tableScore = tableScore + pieceValue(chessBoard.get(r,c));
                            break;
                        case WPawn:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c)) - whitePawnTable[r][c];
                            break;
                        case WKnight:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c)) - knightTable[r][c];
                            break;
                        case WBishop:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c)) - bishopTable[r][c];
                            break;
                        case WKing:
                        case WQueen:
                        case WRook:
                            tableScore = tableScore - pieceValue(chessBoard.get(r,c));
                            break;
                    }
                }
            }
        }

        return tableScore + score;
    }
}
