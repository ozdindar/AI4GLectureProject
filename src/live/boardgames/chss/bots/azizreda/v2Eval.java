/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package live.boardgames.chss.bots.azizreda;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;

/**
 *
 * @author aziz
 */
public class v2Eval implements BoardEvaluator {

    public static final int pawn = 100;
    public static final int knight = 300;
    public static final int bishop = 350;
    public static final int rook = 500;
    public static final int queen = 950;
    public static final int king = 5000;
    
    
    private static final int white_pawn_position[][] = 
        {
        {0,   0,   0,   0,   0,   0,  0,  0},
        {5,  10,  10, -20, -20,  10, 10,  5},
        {5,  -5, -10,   0,   0, -10, -5,  5},
        {0,   0,   0,  20,  20,   0,  0,  0},
        {5,   5,  10,  25,  25,  10,  5,  5},		
        {10, 10,  20,  30,  30,  20, 10, 10},
        {50, 50,  50,  50,  50,  50, 50, 50},
        {0,   0,   0,   0,   0,   0,  0,  0}
        };
    
    private static final int black_pawn_position[][] = 
        {
        {0,   0,   0,   0,   0,   0,  0,  0},
        {50, 50,  50,  50,  50,  50, 50, 50},
        {10, 10,  20,  30,  30,  20, 10, 10},
        {5,   5,  10,  25,  25,  10,  5,  5},		
        {0,   0,   0,  20,  20,   0,  0,  0},
        {5,  -5, -10,   0,   0, -10, -5,  5},
        {5,  10,  10, -20, -20,  10, 10,  5},
        {0,   0,   0,   0,   0,   0,  0,  0}
        };

    private static final int white_knight_position[][] = 
        {
        {-50, -40, -30, -30, -30, -30, -40, -50},
        {-40, -20,   0,   5,   5,   0, -20, -40},
        {-30,   5,  10,  15,  15,  10,   5, -30},
        {-30,   0,  15,  20,  20,  15,   0, -30},
        {-30,   5,  15,  20,  20,  15,   5, -30},	 
        {-30,   0,  10,  15,  15,  10,   0, -30},	 
        {-40, -20,   0,   0,   0,   0, -20, -40},
        {-50, -40, -30, -30, -30, -30, -40, -50}
        };
    
    private static final int black_knight_position[][] = 
        {
        {-50, -40, -30, -30, -30, -30, -40, -50},
        {-40, -20,   0,   0,   0,   0, -20, -40},
        {-30,   0,  10,  15,  15,  10,   0, -30},	 
        {-30,   5,  15,  20,  20,  15,   5, -30},
        {-30,   0,  15,  20,  20,  15,   0, -30},
        {-30,   5,  10,  15,  15,  10,   5, -30},
        {-40, -20,   0,   5,   5,   0, -20, -40},
        {-50, -40, -30, -30, -30, -30, -40, -50}
        };
    
    private static final int white_bishop_position[][] = 
        {
        {-20, -10, -10, -10, -10, -10, -10, -20},
        {-10,   5,   0,   0,   0,   0,   5, -10},
        {-10,  10,  10,  10,  10,  10,  10, -10},     
        {-10,   0,  10,  10,  10,  10,   0, -10},     
        {-10,   5,   5,  10,  10,   5,   5, -10},     
        {-10,   0,   5,  10,  10,   5,   0, -10},     
        {-10,   0,   0,   0,   0,   0,   0, -10},     
        {-20, -10, -10, -10, -10, -10, -10, -20}
        };
    
    private static final int black_bishop_position[][] = 
        {
        {-20, -10, -10, -10, -10, -10, -10, -20},
        {-10,   0,   0,   0,   0,   0,   0, -10},
        {-10,   0,   5,  10,  10,   5,   0, -10},
        {-10,   5,   5,  10,  10,   5,   5, -10},     
        {-10,   0,  10,  10,  10,  10,   0, -10},     
        {-10,  10,  10,  10,  10,  10,  10, -10},     
        {-10,   5,   0,   0,   0,   0,   5, -10},
        {-20, -10, -10, -10, -10, -10, -10, -20}
        };

    private static final int white_rook_position[][] = 
        {
        {0,  0,  0,  5,  5,  0,  0,  0},
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {5, 10, 10, 10, 10, 10, 10,  5},
        {0,  5,  5,  5,  5,  5,  5,  0}
        };
       
    private static final int black_rook_position[][] = 
        {
        {0,  5,  5,  5,  5,  5,  5,  0},
        {5, 10, 10, 10, 10, 10, 10,  5},
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {-5, 0,  0,  0,  0,  0,  0, -5},	 
        {0,  0,  0,  5,  5,  0,  0,  0}
        };
                  
    private static final int white_queen_position[][] = 
        {
        {-20, -10, -10, -5, -5, -10, -10, -20},
        {-10,   0,   5,  0,  0,   0,   0, -10},	 
        {-10,   5,   5,  5,  5,   5,   0, -10},	 
        {0,     0,   5,  5,  5,   5,   0, -5},	 
        {-5,    0,   5,  5,  5,   5,   0, -5},	 
        {-10,   0,   5,  5,  5,   5,   0, -10},	 		
        {-10,   0,   0,  0,  0,   0,   0, -10},
        {-20, -10, -10, -5, -5, -10, -10, -20}
        };
                
    private static final int black_queen_position[][] = 
        {
        {-20, -10, -10, -5, -5, -10, -10, -20},
        {-10,   0,   0,  0,  0,   0,   0, -10},
        {-10,   0,   5,  5,  5,   5,   0, -10},	 
        {-5,    0,   5,  5,  5,   5,   0, -5},	 
        {0,     0,   5,  5,  5,   5,   0, -5},	 
        {-10,   5,   5,  5,  5,   5,   0, -10},	 
        {-10,   0,   5,  0,  0,   0,   0, -10},	 
        {-20, -10, -10, -5, -5, -10, -10, -20}
        };

    private static final int white_king_position[][] = 
        {
        {20,   30,  10,   0,   0,  10,  30,  20},
        {20,   20,   0,   0,   0,   0,  20,  20},	 
        {-10, -20, -20, -20, -20, -20, -20, -10},	 		
        {-20, -30, -30, -40, -40, -30, -30, -20},	 
        {-30, -40, -40, -50, -50, -40, -40, -30},		
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30}
        };
    
    private static final int black_king_position[][] = 
        {
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-30, -40, -40, -50, -50, -40, -40, -30},
        {-20, -30, -30, -40, -40, -30, -30, -20},	 
        {-10, -20, -20, -20, -20, -20, -20, -10},	 
        {20,   20,   0,   0,   0,   0,  20,  20},	 
        {20,   30,  10,   0,   0,  10,  30,  20}
        };
    
    @Override
    public double evaluate(Board board, int player) {
        
        double value = 0;
        double b_value = 0;
        double w_value = 0;
        int w_pawnCount = 0;
        int w_knightCount = 0;
        int w_bishopCount = 0;
        int w_rookCount = 0;
        int w_queenCount = 0;
        int b_pawnCount = 0;
        int b_knightCount = 0;
        int b_bishopCount = 0;
        int b_rookCount = 0;
        int b_queenCount = 0;
        int w_kingCount = 0;
        int b_kingCount = 0;
        
        ChessBoard c_board = (ChessBoard)board;
        
        /*
        System.out.println("-*-*-*-*-*******************-----------**************");
        for(int i = 0; i<8 ; i++)
        {
            for (int k = 0; k<8 ; k++)
            {
                System.out.println(c_board.get(i, k));
            }}        
        
        
        System.out.println("-*-*-*-*-*******************-----------**************");
        */
        
        
        if(board.isGameOver() == true && board.winner() != player)
        {
            return -500000;
        }else if(board.isGameOver() == true && board.winner() == player)
                {
                    return 100000;
                }
        for(int i = 0; i<8 ; i++)
        {
            for (int k = 0; k<8 ; k++)
            {
                String pos = c_board.get(i, k).toString();
                    switch(pos)
                    {
                        case "Empty":
                            break;
                        case "WPawn":
                            w_pawnCount ++;
                            w_value += white_pawn_position[i][k];
                            break;
                        case "WKnight":
                            w_knightCount ++;
                            w_value += white_knight_position[i][k];
                            break;
                        case "Wbishop":
                            w_bishopCount++;
                            w_value += white_bishop_position[i][k];
                            break;
                        case "WRook":
                            w_rookCount ++;
                            w_value += white_rook_position[i][k];
                            break;
                        case "WQueen":
                            w_queenCount ++;
                            w_value += white_queen_position[i][k];
                            break;
                        case "WKing":
                            w_kingCount ++;
                            w_value += white_king_position[i][k];
                            continue;                            
                        case "BPawn":
                            b_pawnCount ++;
                            b_value += black_pawn_position[i][k];
                            break;
                        case "BKnight":
                            b_knightCount ++;
                            b_value += black_knight_position[i][k];
                            break;
                        case "Bbishop":
                            b_bishopCount++;
                            b_value += black_bishop_position[i][k];
                            break;
                        case "BRook":
                            b_rookCount ++;
                            b_value += black_rook_position[i][k];
                            break;
                        case "BQueen":
                            b_queenCount ++;
                            b_value += black_queen_position[i][k];
                            break;
                        case "BKing":
                            b_kingCount ++;
                            b_value += black_king_position[i][k];
                            continue;
            }
        }}

    w_value += (w_pawnCount*pawn)+(w_knightCount*knight)+(w_bishopCount*bishop)+(w_rookCount*rook)+(w_queenCount*queen)+(w_kingCount*king);
    b_value += (b_pawnCount*pawn)+(b_knightCount*knight)+(b_bishopCount*bishop)+(b_rookCount*rook)+(b_queenCount*queen)+(b_kingCount*king);

    //System.out.println("White value :"+w_value+" Black value :"+ b_value +" The player is :"+board.currentPlayer());

    if(player == 1)
    {
        return b_value - w_value;
    }else
    {
        return w_value-b_value;
    }    
}
}
