package live.boardgames.chss.bots.sinan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.PieceType;


public class Evaluator implements BoardEvaluator{

    @Override
    public double evaluate(Board board, int player) {


        double totalEvaluation = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                totalEvaluation += evaluateBoard((ChessBoard) board ,i , j, player) ;
            }
        }




        return totalEvaluation;

    }



    private double evaluateBoard(ChessBoard chessBoard, int i , int j , int player) {


        double[][] WP_PositionEval = new double[][] {
                {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
                {0.5,  0.5,  0.5,  0.7,  0.7,  0.5,  0.5,  0.5},
                {0.1,  0.1,  0.2,  0.6,  0.6,  0.5,  0.1,  0.1},
                {0.05,  0.05,  0.1,  0.4,  0.4,  0.1,  0.05,  0.05},
                {-0.1,  0.0,  0.0,  0.3,  0.3,  0.0,  0.0,  -0.1},
                {0.05, -0.05, -0.01,  0.1,  0.1, -0.1, -0.5,  0.05},
                {0.05,  0.1, 0.01,  -0.1, -0.1,  0.1,  0.1,  0.05},
                {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        };

        double[][] WN_PositionEval = new double[][]{
                {-0.5, -0.4, -0.3, -0.3, -0.3, -0.3, -0.4, -0.5},
                {-0.4, -0.2,  0.0,  0.0,  0.0,  0.0, -0.2, -0.4},
                {-0.3,  0.0,  0.1,  0.15,  0.15,  0.1,  0.0, -0.3},
                {-0.3,  0.05,  0.15,  0.2,  0.2,  0.15,  0.05, -0.3},
                {-0.3,  0.0,  0.15,  0.2,  0.2,  0.15,  0.0, -0.3},
                {-0.3,  0.05,  0.16,  0.15,  0.15,  0.16,  0.05, -0.3},
                {-0.4, -0.2,  0.0,  0.05,  0.05,  0.0, -0.2, -0.4},
                {-0.5, -0.4, -0.3, -0.3, -0.3, -0.3, -0.4, -0.5}
        };


        double[][] WB_PositionEval = new double[][]{
                { -0.2, -0.1, -0.1, -0.1, -0.1, -0.1, -0.1, -0.2},
                { -0.1,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.1},
                { -0.1,  0.0,  0.05,  0.1,  0.1,  0.05,  0.0, -0.1},
                { -0.1,  0.05,  0.05,  0.1,  0.1,  0.05,  0.05, -0.1},
                { -0.1,  0.0,  0.1,  0.2,  0.2,  0.1,  0.0, -0.1},
                { -0.1,  0.1,  0.3,  0.1,  0.1,  0.3,  0.1, -0.1},
                { -0.1,  0.7,  0.0,  0.0,  0.0,  0.0,  0.7, -0.1},
                { -0.2, -0.1, -1, -0.1, -0.1, -1, -0.1, -0.2}
        };

        double[][] WR_PositionEval = new double[][]{
                {  0.05,  0.05,  0.05,  0.05,  0.05,  0.05,  0.05,  0.05},
                {  0.5,  0.1,  0.1,  0.1,  0.1,  0.1,  0.1,  0.5},
                { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
                { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
                { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
                { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
                { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
                {  0.0,   0.0, 0.0,  0.05,  0.05,  0.0,  0.0,  0.0}
        };


        double[][] WQ_PositionEval = new double[][]{
                { -0.2, -0.1, -0.1, -0.5, -0.5, -0.1, -0.1, -0.2},
                { -0.1,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.1},
                { -0.1,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.1},
                { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
                {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
                { -0.1,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -0.1},
                { -0.1,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -0.1},
                { -0.2, -0.1, -0.1, -0.5, -0.5, -0.1, -0.1, -0.2}
        };

        double[][] WK_PositionEval = new double[][]{
                { -0.3, -0.4, -0.4, -0.5, -0.5, -0.4, -0.4, -0.3},
                { -0.3, -0.4, -0.4, -0.5, -0.5, -0.4, -0.4, -0.3},
                { -0.3, -0.4, -0.4, -0.5, -0.5, -0.4, -0.4, -0.3},
                { -0.3, -0.4, -0.4, -0.5, -0.5, -0.4, -0.4, -0.3},
                { -0.2, -0.3, -0.3, -0.4, -0.4, -0.3, -0.3, -0.2},
                { -0.1, -0.2, -0.2, -0.2, -0.2, -0.2, -0.2, -0.1},
                {  0.2,  0.2,  0.0,  0.0,  0.0,  0.0,  0.2,  0.2 },
                {  0.2,  0.3,  0.1,  0.0,  0.3,  0.1,  0.3,  0.2 }
        };

        double[][] BP_PositionEval;
        BP_PositionEval =  reverseArray(WP_PositionEval);

        double[][] BN_PositionEval ;
        BN_PositionEval =  reverseArray(WN_PositionEval);

        double[][] BB_PositionEval ;
        BB_PositionEval =  reverseArray(WB_PositionEval);

        double[][] BR_PositionEval ;
        BR_PositionEval =  reverseArray(WR_PositionEval);

        double[][] BQ_PositionEval ;
        BQ_PositionEval =  reverseArray(WQ_PositionEval);

        double[][] BK_PositionEval ;
        BK_PositionEval =  reverseArray(WK_PositionEval);



        double weightEval = 0;

        if(chessBoard.get(i,j) == null){
            return 0;
        }
            if (chessBoard.get(i, j) == PieceType.WKing)
                weightEval = 200 + WK_PositionEval[i][j];

            else if (chessBoard.get(i, j) == PieceType.WQueen)
                weightEval= 9 + WQ_PositionEval[i][j];

            else if (chessBoard.get(i, j) == PieceType.WRook)
                weightEval = 5 + WR_PositionEval[i][j];

            else if (chessBoard.get(i, j) == PieceType.WBishop)
                weightEval = 3.5 + WB_PositionEval[i][j];

            else if (chessBoard.get(i, j) == PieceType.WKnight)
                weightEval = 3 + WN_PositionEval[i][j];

            else if (chessBoard.get(i, j) == PieceType.WPawn)
                weightEval = 1 + WP_PositionEval[i][j];



        else if (chessBoard.get(i, j) == PieceType.BKing)
                weightEval = -(200 + BK_PositionEval[i][j]);

            else if (chessBoard.get(i, j) == PieceType.BQueen)
                weightEval = -(9 + BQ_PositionEval[i][j]);

            else if (chessBoard.get(i, j) == PieceType.BRook)
                weightEval = -(50 + BR_PositionEval[i][j]);

            else if (chessBoard.get(i, j) == PieceType.BBishop)
                weightEval = -(3.5 + BB_PositionEval[i][j]);

            else if (chessBoard.get(i, j) == PieceType.BKnight)
                weightEval = -(3 + BN_PositionEval[i][j]);

            else if (chessBoard.get(i, j) == PieceType.BPawn)
                weightEval = -(1 + BP_PositionEval[i][j]);

        weightEval += (player == 0) ?  0.25 : - 0.25;

        return weightEval;



    }

    public static double[][]  reverseArray (double[][] array){
        int rows = array.length;
        int cols = array[0].length;

        double new_array[][] = new double[rows][cols];

        for(int i = rows-1; i >= 0; i--) {
            for(int j = cols-1; j >= 0; j--) {
                new_array[rows-1-i][cols-1-j] = array[i][j];
                new_array[i][j] = -array[i][j];
            }
        }
        return new_array;
    }



}
