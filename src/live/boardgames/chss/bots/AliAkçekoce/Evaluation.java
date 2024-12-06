/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.AliAkçekoce;

import java.awt.Point;
import java.util.List;
import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;



import live.boardgames.chss.internal.knowledge.PieceType;

/**
 *
 * @author ALI
 */
public class Evaluation implements BoardEvaluator {
    final double WIN_SCORE = 100000;
    final double LOSS_SCORE = -100000;
    final double DRAW_SCORE = 0;
    
    public static  double PAWN = 10;
    public static  double ROOK = 55;
    public static  double KNIGHT = 35;
    public static  double BISHOP = 35;
    public static  double QUEEN = 90;
    
    private   int WKNIGHT[][] =
    {
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-30,  0,  20, 25, 25, 20,  0,-30},
        {-30,  5, 25, 30, 30, 25,  5,-30},
        {-30,  0, 25, 30, 30, 25,  0,-30},
        {-30,  5,  20, 25, 25, 20,  5,-30},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}
        
    };
    
    private   int BKNIGHT[][] =
    {
        {-50,-40,-30,-30,-30,-30,-40,-50},
        {-40,-20,  0,  5,  5,  0,-20,-40},
        {-30,  5, 20, 25, 25, 20,  5,-30},
        {-30,  0, 25, 30, 30, 25,  0,-30},
        {-30,  5, 25, 30, 30, 25,  5,-30},
        {-30,  0, 20, 25, 25, 20,  0,-30},
        {-40,-20,  0,  0,  0,  0,-20,-40},
        {-50,-40,-30,-30,-30,-30,-40,-50}
        
    };
    private   int WPawn[][] = 
    {
        {0 , 0,  0,  0,  0,  0,  0,  0},
        {40,40, 40, 30, 30, 40, 40, 40},
        {10,10, 20, 10, 10, 20, 10, 10},
        {10 ,10, 10, 40, 40, 10, 10, 10},
        {0 ,15, 15, 20, 20,  15, 15,  0},
        {5 ,20, 20,  0,  0,  20, 20,  5},
        {5 ,25, 25,-20,-20,  25, 25,  5},
        {0 ,30, 30,  0,  0,  30, 30,  0}
    };
    private   int BPawn[][] = 
    {
        {0 ,30, 30,  0,  0,  30, 30,  0},
        {5 ,25, 25,-20,-20,  25, 25,  5},
        {5 ,20, 20,  0,  0,  20, 20,  5},
        {0 ,15, 15, 20, 20,  15, 15,  0},
        {10 ,10, 10, 40, 40, 10, 10, 10},
        {10,10, 20, 10, 10, 20, 10, 10},
        {40,40, 40, 30, 30, 40, 40, 40},
        {0 , 0,  0,  0,  0,  0,  0,  0}
    };
    private   int WBISHOP[][] = 
    {
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}
    };
    
    private   int BBISHOP[][] = 
    {
        {-20,-10,-10,-10,-10,-10,-10,-20},
        {-10,  5,  0,  0,  0,  0,  5,-10},
        {-10, 10, 10, 10, 10, 10, 10,-10},
        {-10,  0, 10, 10, 10, 10,  0,-10},
        {-10,  5,  5, 10, 10,  5,  5,-10},
        {-10,  0,  5, 10, 10,  5,  0,-10},
        {-10,  0,  0,  0,  0,  0,  0,-10},
        {-20,-10,-10,-10,-10,-10,-10,-20}
    };
    private   int WROOK[][] = 
    {
        {0, 0,  5,  0,  0,  0,  0,  -5},
        {5, 10, 10, 10, 10, 10, 10,  5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {10,  0,  0,  5,  5,  0,  0,  10}
    };
   
    private   int BROOK[][] = 
    {
        {0,  0,  0,  5,  5,  0,  0,  0},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {-5,  0,  0,  0,  0,  0,  0, -5},
        {5, 10, 10, 10, 10, 10, 10,  5},
        {0, 0,  5,  0,  0,  0,  0,  -5}
    };
    
    private   int WQUEEN[][] = 
    {
        {-20,-10,-10,  0, -5,-10,-10,-20},
        {-10,  0,  0,  10,  0,  0,  0,-10},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        {-5,   0,  5,  5,  5,  5,  0, -5},
        { 0,   0,  5,  5,  5,  5,  0, -5},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-20,-10,-10, -5, -5,-10,-10,-20}
    };
    private   int BQUEEN[][] = 
    {
        {-20,-10,-10, -5, -5,-10,-10,-20},
        {-10,  0,  5,  0,  0,  0,  0,-10},
        {-10,  5,  5,  5,  5,  5,  0,-10},
        {  0,  0,  5,  5,  5,  5,  0, -5},
        { -5,  0,  5,  5,  5,  5,  0, -5},
        {-10,  0,  5,  5,  5,  5,  0,-10},
        {-10,  0,  0,  10,  0,  0,  0,-10},
        {-20,-10,-10,  0, -5,-10,-10,-20}
    };
    private   int BKING[][] = 
    {
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        {20, 20,  0,  0,  0,  0, 20, 20},
        {20, 40, 10,  0,  0, 10, 40, 20}
    };
    private   int WKING[][] = 
    {
        {20, 40, 10,  0,  0, 10, 40, 20},
        {20, 20,  0,  0,  0,  0, 20, 20},
        {-10,-20,-20,-20,-20,-20,-20,-10},
        {-20,-30,-30,-40,-40,-30,-30,-20},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30},
        {-30,-40,-40,-50,-50,-40,-40,-30}
    };
    
    public int[] isCaptureForWhitePawn(PieceType[][] board,int player,int r,int c){
        int a [] = new int[2];
        int opponent = Chess.opponent(player);
        if((c != 0 && c!=7)){            
                 if(board[r+1][c-1].toPlayer() == opponent ){
                     a[0]= r+1; a[1] = c-1;
                     return a;
                 }
                 else if( board[r+1][c+1].toPlayer() == opponent){
                     a[0]= r+1; a[1] = c+1;
                     return a;
                 }                      
        }
        else if(c == 0){           
                if(board[r+1][c+1].toPlayer() == opponent){
                    a[0]= r+1; a[1] = c+1;
                     return a;
                }                     
        }
        else if(c==7&&(r>0 &&r<=6)){           
                if(board[r+1][c-1].toPlayer() == opponent ){
                    a[0]= r+1; a[1] = c-1;
                     return a;
                }                       
        }
        
        return a;
    }
    
    public int[] isCaptureForBlackPawn(PieceType[][] board,int player,int r,int c){
        int a [] = new int[2];
        a[0] = -1; a[1]=-1;
        int opponent = Chess.opponent(player);
        if((c != 0 && c!=7)){
                if(board[r-1][c-1].toPlayer() == opponent ){
                    a[0]= r-1; a[1] = c-1;
                     return a;
                }
                else if( board[r-1][c+1].toPlayer() == opponent){
                     a[0]= r-1; a[1] = c+1;
                     return a;
                 }              
        }
        
        else if(c == 0&&(r>0 &&r<=7)){            
                if(board[r-1][c+1].toPlayer() == opponent){
                    a[0]= r-1; a[1] = c+1;
                     return a;
                }
            }
        
        else if(c==7){           
                if(board[r-1][c-1].toPlayer() == opponent ){
                    a[0]= r-1; a[1] = c-1;
                    return a;
                }         
            }       
        return a;
    }
    @Override
    public double evaluate(Board board, int player) {
        double total = 0;
        int oppenent = Chess.opponent(player);
        int bPawnCount = 0;
        int bBishopCount = 0;
        int bRookCount = 0;
        int bKnightCount = 0;
        int bQueenCount = 0;
        int wPawnCount = 0;
        int wBishopCount = 0;
        int wRookCount = 0;
        int wKnightCount = 0;
        int wQueenCount = 0;
        int wqueen[] =new int[2];
        int bqueen[] =new int[2];
        ChessBoard b = (ChessBoard) board.cloneBoard();
        PieceType[][] a = b.getBoard();
        
        
        //Center 
        if(b.getMoveCount()>10){
            int array[] ={a[3][3].toPlayer(),a[4][3].toPlayer(),a[3][4].toPlayer(),a[4][4].toPlayer()};
            for(int i = 0;i<4;i++){
                if(array[i] == player)
                     total += 100;
                else if(array[i] == Chess.opponent(player)) total -= 100;
            }
        }
            for(int i = 0;i<7;i++){
                for(int j = 0;j<8;j++){
                    if(a[i][j] == PieceType.Empty)
                        continue;
                    else if(a[i][j].toPlayer() == Chess.BLACK)
                        continue;
                    else if(a[i][j] ==PieceType.WPawn){
                        wPawnCount++;
                        if(player == 0){
                        int ar[] = isCaptureForWhitePawn(a, player, i, j);
                        if(a[0] != null && a[1]!=null)
                            WPawn[ar[0]][ar[1]] +=2000;
                        }
                        total += WPawn[i][j]*PAWN;
                    }
                    else if(a[i][j] ==PieceType.WKnight){
                        wKnightCount++;
                        total += WKNIGHT[i][j]*KNIGHT;
                    }
                    else if(a[i][j] ==PieceType.WBishop){
                        wBishopCount++;
                        total += WBISHOP[i][j]*BISHOP;
                    }                         
                    else if(a[i][j] ==PieceType.WQueen){
                        wQueenCount++;
                        wqueen[0]= i;
                        wqueen[1]= j;
                        total += WQUEEN[i][j]*QUEEN;
                    }
                    else if(a[i][j] ==PieceType.WRook){
                        wRookCount++;
                        total += WROOK[i][j]*ROOK;
                    }              
                    else if(a[i][j] ==PieceType.WKing){
                        total += WKING[i][j];
                       
                    }
                }
            }
            
                
            
            for(int i = 2;i<8;i++){
                for(int j = 0;j<8;j++){
                    if(a[i][j] == PieceType.Empty)
                        continue;
                    else if(a[i][j].toPlayer() == Chess.WHITE)
                        continue;
                    else if(a[i][j] ==PieceType.BPawn){
                        bPawnCount++;
                        if(player == 1){
                        int ar[] = isCaptureForBlackPawn(a, player, i, j);
                        if(ar[0] != -1 && ar[1]!=-1)
                            BPawn[ar[0]][ar[1]] +=2000;
                        }
                        total += BPawn[i][j]*PAWN;
                    }
                    else if(a[i][j] ==PieceType.BKnight){
                        bKnightCount++;
                        total += BKNIGHT[i][j]*KNIGHT;
                    }                    
                    else if(a[i][j] ==PieceType.BBishop){
                        bBishopCount++;
                        total += BBISHOP[i][j]*BISHOP;
                    }
                    else if(a[i][j] ==PieceType.BQueen){
                        bQueenCount++;
                        bqueen[0]= i;
                        bqueen[1]= j;
                        total += BQUEEN[i][j]*QUEEN;
                    }
                    else if(a[i][j] ==PieceType.BRook){
                        bRookCount++;
                        total += BROOK[i][j]*ROOK;
                    }                                     
                    else if(a[i][j] ==PieceType.BKing){
                        total += BKING[i][j];
                    }
                }
            }
        
            
        if(player == 0){
            if(bBishopCount>wBishopCount)
                total -= (bBishopCount-wBishopCount) *BISHOP;
            else if(bBishopCount<wBishopCount)
                total += (wBishopCount-bBishopCount) *BISHOP;
            
            if(bPawnCount>wPawnCount)
                total -= (bPawnCount-wPawnCount) *PAWN+200;
            else if(bPawnCount<wPawnCount)
                total += (wPawnCount-bPawnCount) *PAWN-200;
            
            if(bKnightCount>wKnightCount)
                total -= (bKnightCount-wKnightCount) *KNIGHT+1000;
            else if(bKnightCount<wKnightCount)
                total += (wKnightCount-bKnightCount) *KNIGHT-1000;
            
            if(bRookCount>wRookCount)
                total -= (bRookCount-wRookCount) *ROOK+2000;
            else if(bRookCount<wRookCount)
                total += (wRookCount-bRookCount) *ROOK-2000;
            
            if(bQueenCount>wQueenCount)
                total -= (bQueenCount-wQueenCount) *QUEEN+5000;
            else if(bQueenCount<wQueenCount) 
                total += (wQueenCount-bQueenCount) *QUEEN-5000;
            
            if(b.getMoveCount()<10 && a[wqueen[0]][wqueen[1]] != PieceType.WQueen){
                total -= 500000000;
            }
        }     
        
        if(player == 1){
            if(bBishopCount>wBishopCount)
                total += (bBishopCount-wBishopCount) *BISHOP;
            else if(bBishopCount<wBishopCount)
                total -= (wBishopCount-bBishopCount) *BISHOP;
            
            if(bPawnCount>wPawnCount)
                total += (bPawnCount-wPawnCount) *PAWN;
            else if(bPawnCount<wPawnCount)
                total -= (wPawnCount-bPawnCount) *PAWN;
            
            if(bKnightCount>wKnightCount)
                total += (bKnightCount-wKnightCount) *KNIGHT;
            else if(bKnightCount<wKnightCount)
                total -= (wKnightCount-bKnightCount) *KNIGHT;
            
            if(bRookCount>wRookCount)
                total += (bRookCount-wRookCount) *ROOK;
            else if(bRookCount<wRookCount)
                total -= (wRookCount-bRookCount) *ROOK;
            
            if(bQueenCount>wQueenCount)
                total += (bQueenCount-wQueenCount) *QUEEN;
            else if(bQueenCount<wQueenCount) 
                total -= (wQueenCount-bQueenCount) *QUEEN;
            
            if(b.getMoveCount()<10 && a[7][3] == PieceType.BQueen){
                total += 50000;
            }
        }      
            
        
        
        
        return total;
    }

    
    
}
