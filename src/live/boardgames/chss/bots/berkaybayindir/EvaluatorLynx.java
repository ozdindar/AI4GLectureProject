package live.boardgames.chss.bots.berkaybayindir;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;

public class EvaluatorLynx implements BoardEvaluator {

    ChessBoard chessBoard = new ChessBoard();

    //Taşların değerleri var. Taşa göre değerlendirme yapabilmek için bu değerleri yazdım.
    int pawn = 100;
    int knight,bishop = 300;
    int rook = 500;
    int queen = 900;
    int king = 100000000;

    //Burda taşların tahta üzerindeki değerler var. Bu tabloları yere göre değerlendirmek için kullanıyorum.
    static int pawnBoard[][]={
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
    static int kingMidBoard[][]={
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}};
    static int kingEndBoard[][]={
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}};

    private int ratePositional(int player){//Taşların pozisyonlarına göre bir sayı dönüyor.
        int counter = 0;
        if(player == 0){
            for (int i=0;i<64;i++) {
                switch (chessBoard.get(i/8, i%8)) {
                    case WPawn: counter+=pawnBoard[i/8][i%8];
                        break;
                    case WRook: counter+=rookBoard[i/8][i%8];
                        break;
                    case WKnight: counter+=knightBoard[i/8][i%8];
                        break;
                    case WBishop: counter+=bishopBoard[i/8][i%8];
                        break;
                    case WQueen: counter+=queenBoard[i/8][i%8];
                        break;
                }
            }
        }
        if(player == 1){
            for (int i=0;i<64;i++) {
                switch (chessBoard.get(i/8, i%8)) {
                    case BPawn: counter+=pawnBoard[i/8][i%8];
                        break;
                    case BRook: counter+=rookBoard[i/8][i%8];
                        break;
                    case BKnight: counter+=knightBoard[i/8][i%8];
                        break;
                    case BBishop: counter+=bishopBoard[i/8][i%8];
                        break;
                    case BQueen: counter+=queenBoard[i/8][i%8];
                        break;
                }
            }
        }
        return counter;
    }
    private int ratePiece(int player) {//Taşların çeşitlerine göre bir sayı dönüyor.
        int counter = 0;
        if(player == 0){
            for (int i=0;i<64;i++) {
                switch (chessBoard.get(i/8, i%8)) {
                    case WPawn: counter+=pawn;
                        break;
                    case WRook: counter+=rook;
                        break;
                    case WKnight: counter+=knight;
                        break;
                    case WBishop: counter+=bishop;
                        break;
                    case WQueen: counter+=queen;
                        break;
                }
            }
        }
        if(player == 1){
            for (int i=0;i<64;i++) {
                switch (chessBoard.get(i/8, i%8)) {
                    case BPawn: counter+=pawn;
                        break;
                    case BRook: counter+=rook;
                        break;
                    case BKnight: counter+=knight;
                        break;
                    case BBishop: counter+=bishop;
                        break;
                    case BQueen: counter+=queen;
                        break;
                }
            }
        }
        return counter;
    }
    @Override
    public double evaluate(Board board, int player) {
        int posRating = ratePositional(board.currentPlayer());
        int pieceRating = ratePiece(board.currentPlayer());

        return posRating + pieceRating;
    }


}
