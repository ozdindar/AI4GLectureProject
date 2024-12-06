package live.boardgames.chss.bots.aniladanir;


import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.PieceType;

public class MyBoardEvaluator implements BoardEvaluator{

	private static GameState gameState;
	
	public MyBoardEvaluator() {
		gameState = GameState.Opening;
	}
	
	public static GameState getGameState() {
		return gameState;
	}
	
	@Override
	public double evaluate(Board board, int player) {
		byte piecesLeft = 0;
		double score = 0;
		
		
		ChessBoard cBoard = (ChessBoard)board;
		PieceType[][] squares = cBoard.getBoard();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				PieceType cur = squares[r][c];
				
				//Doubled Pawns
				if(cur == PieceType.BPawn) {
					if(squares[r-1][c] == PieceType.BPawn)
						score += player == 0 ? 10 : -10;
				}
				if(cur == PieceType.WPawn) {
					if(squares[r+1][c] == PieceType.WPawn)
						score += player == 0 ? -10 : 10;
				}
				
				if(cur != PieceType.Empty) {
					piecesLeft++;
					int p = PieceEvaluator.getScore(cur, r, c);
					if(cur.toPlayer() == player)
						score += p;
					else
						score -= p;
				}
					
			}
		}
		
		if(gameState != GameState.EndGame && piecesLeft < 10)
			gameState = GameState.EndGame;
		
		
		//Check scenarios
		if(Chess.kingIsInCheck(squares, player))
			score -= 10;
		else if(Chess.kingIsInCheck(cBoard.getBoard(), getOpponent(player)))
			score += 10;
		
		
		return score ;
	}

	
	public int simpleEvaluate(Board board, int player) {
		int score = 0;
		
		ChessBoard cBoard = (ChessBoard)board;
		PieceType[][] squares = cBoard.getBoard();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				PieceType cur = squares[r][c];
				
				
				if(cur != PieceType.Empty) {
					int p = PieceEvaluator.getPieceScore(cur);
					if(cur.toPlayer() == player)
						score += p;
					else
						score -= p;
				}
					
			}
		}
		
		return score;
	}
	
	
	private int getOpponent(int player) {
		return player == 0 ? 1 : 0;
	}
	
	
	
	

}
