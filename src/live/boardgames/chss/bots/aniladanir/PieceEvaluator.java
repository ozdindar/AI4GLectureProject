package live.boardgames.chss.bots.aniladanir;

import java.util.HashMap;

import live.boardgames.chss.internal.knowledge.PieceType;

public class PieceEvaluator {
	
	private static HashMap<PieceType, int[][]> psTables = new HashMap<PieceType, int[][]>(){

		{
			put(PieceType.BBishop, new int[][]
				{
					{-20,-10,-10,-10,-10,-10,-10,-20},
				    {-10,  0,  0,  0,  0,  0,  0,-10},
				    {-10,  0,  5, 10, 10,  5,  0,-10},
				    {-10,  5,  5, 10, 10,  5,  5,-10},
				    {-10,  0, 10, 10, 10, 10,  0,-10},
				    {-10, 10, 10, 10, 10, 10, 10,-10},
				    {-10,  5,  0,  0,  0,  0,  5,-10},
				    {-20,-10,-40,-10,-10,-40,-10,-20}
				}
			);
			put(PieceType.WBishop, new int[][] 
				{
				    {-20,-10,-40,-10,-10,-40,-10,-20},
					{-10,  5,  0,  0,  0,  0,  5,-10},
					{-10, 10, 10, 10, 10, 10, 10,-10},
					{-10,  0, 10, 10, 10, 10,  0,-10},
					{-10,  5,  5, 10, 10,  5,  5,-10},
					{-10,  0,  5, 10, 10,  5,  0,-10},
					{-10,  0,  0,  0,  0,  0,  0,-10},
					{-20,-10,-10,-10,-10,-10,-10,-20}
				}
			);
			put(PieceType.BPawn, new int[][] 
				{
					{0,  0,  0,  0,  0,  0,  0,  0 },
					{50, 50, 50, 50, 50, 50, 50, 50 },
					{10, 10, 20, 30, 30, 20, 10, 10 },
					{5,  5, 10, 27, 27, 10,  5,  5},
					{0,  0,  0, 25, 25,  0,  0,  0 },
					{5, -5,-10,  0,  0,-10, -5,  5 },
					{5, 10, 10,-25,-25, 10, 10,  5 },
					{0,  0,  0,  0,  0,  0,  0,  0 }
					
				}
			);
			put(PieceType.WPawn, new int[][] 
				{
					{0,  0,  0,  0,  0,  0,  0,  0 },
					{5, 10, 10,-25,-25, 10, 10,  5 },
					{5, -5,-10,  0,  0,-10, -5,  5 },
					{0,  0,  0, 25, 25,  0,  0,  0 },
					{5,  5, 10, 27, 27, 10,  5,  5},
					{10, 10, 20, 30, 30, 20, 10, 10 },
					{50, 50, 50, 50, 50, 50, 50, 50 },
					{0,  0,  0,  0,  0,  0,  0,  0 }
				}
			);
			put(PieceType.BKnight, new int[][] 
				{
					{-50,-40,-30,-30,-30,-30,-40,-50},
				    {-40,-20,  0,  0,  0,  0,-20,-40},
				    {-30,  0, 10, 15, 15, 10,  0,-30},
				    {-30,  5, 15, 20, 20, 15,  5,-30},
				    {-30,  0, 15, 20, 20, 15,  0,-30},
				    {-30,  5, 10, 15, 15, 10,  5,-30},
				    {-40,-20,  0,  5,  5,  0,-20,-40},
				    {-50,-40,-20,-30,-30,-20,-40,-50}
				}
			);
			put(PieceType.WKnight, new int[][] 
				{
				    {-50,-40,-20,-30,-30,-20,-40,-50},
					{-40,-20,  0,  5,  5,  0,-20,-40},
					{-30,  5, 10, 15, 15, 10,  5,-30},
					{-30,  0, 15, 20, 20, 15,  0,-30},
					{-30,  5, 15, 20, 20, 15,  5,-30},
					{-30,  0, 10, 15, 15, 10,  0,-30},
					{-40,-20,  0,  0,  0,  0,-20,-40},
					{-50,-40,-30,-30,-30,-30,-40,-50}
				}
			);
			put(PieceType.BKing, new int[][] 
				{
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-20, -30, -30, -40, -40, -30, -30, -20},
					{-10, -20, -20, -20, -20, -20, -20, -10},
					{ 20,  20,   0,   0,   0,   0,  20,  20},
					{ 20,  30,  10,   0,   0,  10,  30,  20}
				}
			);
			put(PieceType.WKing, new int[][] 
				{
					{ 20,  30,  10,   0,   0,  10,  30,  20},
					{ 20,  20,   0,   0,   0,   0,  20,  20},
					{-10, -20, -20, -20, -20, -20, -20, -10},
					{-20, -30, -30, -40, -40, -30, -30, -20},
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-30, -40, -40, -50, -50, -40, -40, -30},
					{-30, -40, -40, -50, -50, -40, -40, -30}
				}
			);
			put(PieceType.WRook, null);
			put(PieceType.BRook, null);
			put(PieceType.WQueen, null);
			put(PieceType.BQueen, null);
			
		}
	};
	
	private static boolean endGameUpdated = false;
	
	public static int getScore(PieceType piece, int r, int c) {
		int score = 0;
		
		if(!endGameUpdated && MyBoardEvaluator.getGameState() == GameState.EndGame) {
			//Update piece square tables for end game
			psTables.put(PieceType.WKing, new int[][] {
				{-50,-40,-30,-20,-20,-30,-40,-50},
			    {-30,-20,-10,  0,  0,-10,-20,-30},
			    {-30,-10, 20, 30, 30, 20,-10,-30},
			    {-30,-10, 30, 40, 40, 30,-10,-30},
			    {-30,-10, 30, 40, 40, 30,-10,-30},
			    {-30,-10, 20, 30, 30, 20,-10,-30},
			    {-30,-30,  0,  0,  0,  0,-30,-30},
			    {-50,-30,-30,-30,-30,-30,-30,-50}
			});
			psTables.put(PieceType.BKing, new int[][] {
				{-50,-30,-30,-30,-30,-30,-30,-50},
				{-30,-30,  0,  0,  0,  0,-30,-30},
			    {-30,-10, 20, 30, 30, 20,-10,-30},
			    {-30,-10, 30, 40, 40, 30,-10,-30},
			    {-30,-10, 30, 40, 40, 30,-10,-30},
			    {-30,-10, 20, 30, 30, 20,-10,-30},
			    {-30,-20,-10,  0,  0,-10,-20,-30},
			    {-50,-40,-30,-20,-20,-30,-40,-50}
			});
			
			endGameUpdated = true;
		}
		
		score = getPieceScore(piece) + (psTables.get(piece) == null ? 0 : psTables.get(piece)[r][c]);
		
		
		return score;
	}
	
	public static int getPieceScore(PieceType piece) {
		switch(piece) {
			case WPawn:
			case BPawn:
				return 100;
			case WKnight:
			case BKnight:
				if(MyBoardEvaluator.getGameState() == GameState.EndGame)
					return 305;
				return 320;
			case WBishop:
			case BBishop:
				if(MyBoardEvaluator.getGameState() == GameState.EndGame)
					return 340;
				return 325;
			case WRook:
			case BRook:
				return 500;
			case WQueen:
			case BQueen:
				return 975;
			case WKing:
			case BKing:
				return 0;
			default:
				return 0;
		}
	}
	
	
	
}
