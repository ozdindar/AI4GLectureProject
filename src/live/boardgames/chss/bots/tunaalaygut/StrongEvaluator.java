package live.boardgames.chss.bots.tunaalaygut;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.PieceType;

import static live.boardgames.chss.internal.knowledge.Chess.BLACK;
import static live.boardgames.chss.internal.knowledge.Chess.WHITE;

public class StrongEvaluator implements BoardEvaluator {

    static int pawnBoard[][]={                  //taken from https://www.chessprogramming.org/Simplified_Evaluation_Function
            { 0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}};
    static int rookBoard[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}};
    static int knightBoard[][]={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};
    static int bishopBoard[][]={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};
    static int queenBoard[][]={
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int kingBoard[][]={
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}};

    private int playerKingPosition[] = {0, 0};
    private final int quadrantBoundaries[][] = {{0, 3, 0, 3}, {0, 3, 4, 7}, {4, 7, 4, 7}, {4, 7, 0, 3}};

    @Override
    public double evaluate(Board chessBoard, int player) {
        return evaluateMaterial((ChessBoard)chessBoard, player)
                + evaluateKingSafety((ChessBoard)chessBoard, player)
                + evaluatePositional((ChessBoard)chessBoard, player);
    }

    private double evaluateMaterial(ChessBoard chessBoard, int player){
        double score = 0;
        PieceType currentPiece;

        for(int r=0; r<8; r++)
            for(int c=0; c<8; c++){
                currentPiece = chessBoard.get(r, c);
                double matValue = getPieceMaterialValue(currentPiece);
                if(!isEnemyPiece(currentPiece, player)){
                    if (isPlayersKing(currentPiece, player)){
                        playerKingPosition[0] = r;
                        playerKingPosition[1] = c;
                    }
                    score += matValue;
                }
                else
                    score -= matValue;
            }

        return score;
    }

    private double evaluateKingSafety(ChessBoard chessBoard, int player){
        int quadrant = getPieceQuadrant(playerKingPosition[0], playerKingPosition[1]);
        int rowMin = quadrantBoundaries[quadrant][0];
        int rowMax = quadrantBoundaries[quadrant][1];
        int colMin = quadrantBoundaries[quadrant][2];
        int colMax = quadrantBoundaries[quadrant][3];

        double enemyCount = 0;
        double allyCount = 0;

        for(int r = rowMin; r <= rowMax; r++){  //iterate every piece in the king's quadrant
            for(int c = colMin; c <= colMax; c++){
                if (isEnemyPiece(chessBoard.get(r, c), player)){
                    enemyCount++;
                    if(isEnemyQueen(chessBoard.get(r, c), player))
                        enemyCount += 2;    //queen is 3 pieces.
                }
                else
                    allyCount++;
            }
        }

        if(enemyCount > allyCount)
            return (allyCount - enemyCount) * 5;
        return 0;
    }

    private double evaluatePositional(ChessBoard chessBoard, int player){
        double score = 0;

        for(int r = 0; r < 8; r++)
            for(int c = 0; c < 8; c++){
                double posValue = getPiecePositionalValue(chessBoard.get(r, c), r,c);
                if(isEnemyPiece(chessBoard.get(r, c), player))
                    score -= posValue;
                else    //player's piece
                    score += posValue;
            }
        return score;
    }

    private boolean isEnemyQueen(PieceType pieceType, int player) {
        if (player == WHITE && (pieceType == PieceType.BQueen))
            return true;
        if (player == BLACK && (pieceType == PieceType.WQueen))
            return true;
        return false;
    }

    private double getPieceMaterialValue(PieceType piece){
        switch (piece){
            case WKing:
            case BKing:
                return 20000;
            case WQueen:
            case BQueen:
                return 900;
            case WRook:
            case BRook:
                return 500;
            case WBishop:
            case BBishop:
                return 340;
            case WKnight:
            case BKnight:
                return 325;
            case WPawn:
            case BPawn:
                return 100;
        }

        return 0;
    }

    private double getPiecePositionalValue(PieceType piece, int r, int c){
        switch (piece){
            case WKing:
                return kingBoard[r][c];
            case WQueen:
                return queenBoard[r][c];
            case WRook:
                return rookBoard[r][c];
            case WBishop:
                return bishopBoard[r][c];
            case WKnight:
                return knightBoard[r][c];
            case WPawn:
                return pawnBoard[r][c];
            case BKing:
                return kingBoard[7-r][7-c];
            case BQueen:
                return queenBoard[7-r][7-c];
            case BRook:
                return rookBoard[7-r][7-c];
            case BBishop:
                return bishopBoard[7-r][7-c];
            case BKnight:
                return knightBoard[7-r][7-c];
            case BPawn:
                return pawnBoard[7-r][7-c];
        }

        return 0;
    }

    private int getPieceQuadrant(int row, int column){
        if(row <= 3)    //0 or 1
            if (column <= 3) return 0;
            else return 1;
        else            //2 or 3
            if (column <= 3) return 3;
            else return 2;
    }

    private boolean isPlayersKing(PieceType piece, int player){
        return (player == WHITE && piece == PieceType.WKing) || (player == BLACK && piece == PieceType.BKing);
    }

    private boolean isEnemyPiece(PieceType piece, int player){
        if(player == WHITE){
            if(piece == PieceType.BBishop ||
                    piece == PieceType.BKing ||
                    piece == PieceType.BKnight ||
                    piece == PieceType.BPawn ||
                    piece == PieceType.BQueen ||
                    piece == PieceType.BRook)
                return true;
            return false;
        }
        else{
            if(piece == PieceType.WBishop ||
                    piece == PieceType.WKing ||
                    piece == PieceType.WKnight ||
                    piece == PieceType.WPawn ||
                    piece == PieceType.WQueen ||
                    piece == PieceType.WRook)
                return true;
            return false;
        }
    }
}
