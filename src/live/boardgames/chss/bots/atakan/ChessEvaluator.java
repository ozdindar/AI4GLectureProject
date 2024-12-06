package live.boardgames.chss.bots.atakan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.PieceType;


public class ChessEvaluator implements BoardEvaluator {

    private final double [][] Pawn = {

            {0,  0,  0,  0,  0,  0,  0,  0},
            {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
            {0.1, 0.1, 0.2, 0.3, 0.3, 0.2, 0.1, 0.1},
            {0.05,  0.05, 0.1, 0.25, 0.25, 0.1,  0.05,  0.05},
            {0,  0,  0, 0.2, 0.2,  0,  0,  0},
            {0.05, -0.05,-0.1,  0,  0,-0.1, -0.05,  0.05},
            {0.05, 0.1, 0.1,-0.2,-0.2, 0.1, 0.1,  0.05},
            {0,  0,  0,  0,  0,  0,  0,  0}
    };

    private final double [][] Knight = {

            {-0.5,-0.4,-0.3,-0.3,-0.3,-0.3,-0.4,-0.5},
            {-0.4,-0.2,  0,  0,  0,  0,-0.2,-0.4},
            {-0.3,  0, 0.1, 0.15, 0.15, 0.1,  0,-0.3},
            {-0.3,  0.05, 0.15, 0.2, 0.2, 0.15,  0.05,-0.3},
            {-0.3,  0, 0.15, 0.2, 0.2, 0.15,  0,-0.3},
            {-0.3,  0.05, 0.1, 0.15, 0.15, 0.1,  0.05,-0.3},
            {-0.4,-0.2,  0,  0.05,  0.05,  0,-0.2,-0.4},
            {-0.5,-0.4,-0.3,-0.3,-0.3,-0.3,-0.4,-0.5}
    };

    private final double [][] Bishop = {
            {-0.2,-0.1,-0.1,-0.1,-0.1,-0.1,-0.1,-0.2},
            {-0.1,  0,  0,  0,  0,  0,  0,-0.1},
            {-0.1,  0,  0.05, 0.1, 0.1,  0.05,  0,-0.1},
            {-0.1,  0.05,  0.05, 0.1, 0.1,  0.05,  0.05,-0.1},
            {-0.1,  0, 0.1, 0.1, 0.1, 0.1,  0,-0.1},
            {-0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1,-0.1},
            {-0.1,  0.05,  0,  0,  0,  0,  0.05,-0.1},
            {-0.2,-0.1,-0.1,-0.1,-0.1,-0.1,-0.1,-0.2}
    };

    private final double [][] Rook = {
            {0,  0,  0,  0,  0,  0,  0,  0},
            {0.05, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1,  0.05},
            {-0.05,  0,  0,  0,  0,  0,  0, -0.05},
            {-0.05,  0,  0,  0,  0,  0,  0, -0.05},
            {-0.05,  0,  0,  0,  0,  0,  0, -0.05},
            {-0.05,  0,  0,  0,  0,  0,  0, -0.05},
            {-0.05,  0,  0,  0,  0,  0,  0, -0.05},
            {0,  0,  0,  0.05,  0.05,  0,  0,  0}
    };

    private final double [][] Queen = {
            {-0.2,-0.1,-0.1, -0.05, -0.05,-0.1,-0.1,-0.2},
            {-0.1,  0,  0,  0,  0,  0,  0,-0.1},
            {-0.1,  0,  0.05,  0.05,  0.05,  0.05,  0,-0.1},
            {-0.05,  0,  0.05,  0.05,  0.05,  0.05,  0, -0.05},
            {0,  0,  0.05,  0.05,  0.05,  0.05,  0, -0.05},
            {-0.1,  0.05,  0.05,  0.05,  0.05,  0.05,  0,-0.1},
            {-0.1,  0,  0.05,  0,  0,  0,  0,-0.1},
            {-0.2,-0.1,-0.1, -0.05, -0.05,-0.1,-0.1,-0.2}
    };

    private final double [][] King = {

            {-0.3,-0.4,-0.4,-0.5,-0.5,-0.4,-0.4,-0.3},
            {-0.3,-0.4,-0.4,-0.5,-0.5,-0.4,-0.4,-0.3},
            {-0.3,-0.4,-0.4,-0.5,-0.5,-0.4,-0.4,-0.3},
            {-0.3,-0.4,-0.4,-0.5,-0.5,-0.4,-0.4,-0.3},
            {-0.2,-0.3,-0.3,-0.4,-0.4,-0.3,-0.3,-0.2},
            {-0.1,-0.2,-0.2,-0.2,-0.2,-0.2,-0.2,-0.1},
            { 0.2,  0.2,  0,   0,   0,   0, 0.2, 0.2},
            { 0.2,  0.3,0.1,   0,   0,  0.1,0.3, 0.2}
    };

    @Override
    public double evaluate(Board board, int player) {
        ChessBoard b = (ChessBoard) board;

        double whiteScore=0;
        double blackScore=0;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                PieceType t = b.get(r,c);
                if(t.toPlayer() == Chess.WHITE) {
                    whiteScore += getPieceValue(t,r,c);
                }
                else if(t.toPlayer() == Chess.BLACK){
                    blackScore += getPieceValue(t,r,c);
                }
            }
        }

        return  player == Chess.WHITE ?  whiteScore-blackScore :blackScore -whiteScore;
    }

    public double getPieceValue(PieceType pieceType, int row, int col){
        if(pieceType.equals(pieceType.Empty)){
            return 0;
        }

        if(pieceType.equals(pieceType.WPawn)){
            return Pawn[row][col] * 10;
        }
        else if(pieceType.equals(pieceType.BPawn)){
            return Pawn[7-row][7-col] * 10;
        }
        else if(pieceType.equals(pieceType.WRook)){
            return Rook[row][col] * 50;
        }
        else if(pieceType.equals(pieceType.BRook)){
            return Rook[7-row][7-col] * 50;
        }
        else if(pieceType.equals(pieceType.WKnight)){
            return Knight[row][col] * 30;
        }
        else if(pieceType.equals(pieceType.BKnight)){
            return Knight[7-row][7-col] * 30;
        }
        else if(pieceType.equals(pieceType.WBishop)){
            return Bishop[row][col] * 30;
        }
        else if(pieceType.equals(pieceType.BBishop)){
            return Bishop[7-row][7-col] * 30;
        }
        else if(pieceType.equals(pieceType.WQueen)){
            return Queen[row][col] * 90;
        }
        else if(pieceType.equals(pieceType.BQueen)){
            return Queen[7-row][7-col] * 90;
        }
        else if(pieceType.equals(pieceType.WKing)){
            return King[row][col] * 900;
        }
        //else if(pieceType.equals(pieceType.BKing)){
            return King[7-row][7-col] * 900;
        //}
    }

}

