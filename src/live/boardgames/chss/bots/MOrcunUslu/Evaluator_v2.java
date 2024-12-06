package live.boardgames.chss.bots.MOrcunUslu;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;

public class Evaluator_v2 implements BoardEvaluator {

    int Pawn = 100;
    int Knight = 320;
    int Bishop = 330;
    int Rook = 500;
    int Queen = 900;
    int King = 20000;

    /*int WhitePawn = 100;
    int WhiteKnight = 320;
    int WhiteBishop = 330;
    int WhiteRook = 500;
    int WhiteQueen = 900;
    int WhiteKing = 20000;

    int BlackPawn = -100;
    int BlackKnight = -320;
    int BlackBishop = -330;
    int BlackRook = -500;
    int BlackQueen = -900;
    int BlackKing = -20000;*/

    public static int[][] mirror(int[][] theArray) {
        for(int i = 0; i < (theArray.length/2); i++) {
            int[] temp = theArray[i];
            theArray[i] = theArray[theArray.length - i - 1];
            theArray[theArray.length - i - 1] = temp;
        }
        return theArray;
    }

    int [][] whitePawnTable = {
            {0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5,  5, 10, 25, 25, 10,  5,  5},
            {0,  0,  0, 20, 20,  0,  0,  0},
            {5, -5,-10,  0,  0,-10, -5,  5},
            {5, 10, 10,-20,-20, 10, 10,  5},
            {0,  0,  0,  0,  0,  0,  0,  0}
    };
    int [][] blackPawnTable = mirror(whitePawnTable);

    int [][] whiteBishopTable = {
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20},
    };
    int [][] blackBishopTable = mirror(whiteBishopTable);

    int [][] whiteKnightTable = {
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50},
    };

    int [][] blackKnightTable = mirror(whiteKnightTable);

    int [][] whiteRookTable = {
            {0,  0,  0,  0,  0,  0,  0,  0},
            {5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {0,  0,  0,  5,  5,  0,  0,  0}
    };
    int [][] blackRookTable = mirror(whiteRookTable);

    int [][] whiteQueenTable = {
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            {-5,  0,  5,  5,  5,  5,  0, -5},
            {0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}
    };
    int [][] blackQueenTable = mirror(whiteQueenTable);

    int [][] whiteKingTable = {
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            {20, 20,  0,  0,  0,  0, 20, 20},
            {20, 30, 10,  0,  0, 10, 30, 20}
    };

    int [][] blackKingTable = mirror(whiteKingTable);

    @Override
    public double evaluate(Board board, int player) {
        int score = 0;
        ChessBoard chessBoard = (ChessBoard) board;
        String positionedPieceName = "";
        if(player == Chess.WHITE)
        {
            for (int x = 0; x < 8; x++){
                for(int y = 0; y < 8; y++){
                    positionedPieceName = chessBoard.get(x,y).toString();

                    switch (positionedPieceName){
                        case "Empty":
                            break;
                        case "WRook":
                            score = score + Rook + whiteRookTable[x][y];
                            break;
                        case "WKnight":
                            score = score + Knight + whiteKnightTable[x][y];
                            break;
                        case "WBishop":
                            score = score + Bishop + whiteBishopTable[x][y];
                            break;
                        case "WQueen":
                            score = score + Queen + whiteQueenTable[x][y];
                            break;
                        case "WKing":
                            score = score + King + whiteKingTable[x][y];
                            break;
                        case "WPawn":
                            score = score + Pawn + whitePawnTable[x][y];
                            break;
                    }
                }
            }

        }else if(player == Chess.BLACK){
            for (int x = 0; x < 8; x++){
                for(int y = 0; y < 8; y++){
                    positionedPieceName = chessBoard.get(x,y).toString();

                    switch (positionedPieceName){
                        case "BPawn":
                            score = score - Pawn - blackPawnTable[x][y];
                            break;
                        case "BRook":
                            score = score - Rook - blackRookTable[x][y];
                            break;
                        case "BKnight":
                            score = score - Knight - blackKnightTable[x][y];
                            break;
                        case "BBishop":
                            score = score - Bishop - blackBishopTable[x][y];
                            break;
                        case "BQueen":
                            score = score - Queen - blackQueenTable[x][y];
                            break;
                        case "BKing":
                            score = score - King - blackKingTable[x][y];
                            break;
                    }
                }
            }

        }



        if(player == Chess.WHITE)
            return -score;
        return score;
    }

}
