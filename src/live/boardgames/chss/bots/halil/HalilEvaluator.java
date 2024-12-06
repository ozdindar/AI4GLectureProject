package live.boardgames.chss.bots.halil;

import static live.boardgames.chss.internal.knowledge.Chess.BLACK;
import static live.boardgames.chss.internal.knowledge.Chess.EMPTY;
import static live.boardgames.chss.internal.knowledge.Chess.WHITE;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.PieceType;

public class HalilEvaluator implements BoardEvaluator {

	double[][] WQ_Array;
	double[][] WKing_Array;
	double[][] WR_Array;
	double[][] WB_Array;
	double[][] WK_Array;
	double[][] WP_Array;

	double[][] BQ_Array;
	double[][] BKing_Array;
	double[][] BR_Array;
	double[][] BB_Array;
	double[][] BK_Array;
	double[][] BP_Array;

	public HalilEvaluator() {
		super();
		createArrays();
	}

//	get(int r, int c)
	@Override
	public double evaluate(Board board, int player) {
		ChessBoard cb = (ChessBoard) board;

		double score = 0;

		// if check mate
		if (board.isGameOver()) {
			// Whites win
			if (board.winner() == 0) {
				return Double.MAX_VALUE;
			}
			// black wins
			else if (board.winner() == 1) {
				return -Double.MAX_VALUE;
			}
			// draw
			else {
				return 0;
			}
		}

		// For each Square in Board
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				// get piece
				PieceType piece = cb.get(row, col);
				// Evaluate score by square
				score += EvaluateSquare(piece, row, col);
			}
		}

		return score;
	}

	private double EvaluateSquare(PieceType piece, int row, int col) {

		int score = 0;
		// Piece Switch
		switch (piece) {
		case WKing:
			score += 900;
			score += WKing_Array[row][col];
			break;
		case WQueen:
			score += 90;
			score += WQ_Array[row][col];
			break;
		case WRook:
			score += 50;
			score += WR_Array[row][col];
			break;
		case WBishop:
			score += 30;
			score += WB_Array[row][col];
			break;
		case WKnight:
			score += 30;
			score += WK_Array[row][col];
			break;
		case WPawn:
			score += 10;
			score += WP_Array[row][col];
			break;
		case BKing:			
			score -= 900;
			score -= WKing_Array[7 - row][col];
			break;
		case BQueen:
			score -= 90;
			score -= WQ_Array[7 - row][col];
			break;
		case BRook:
			score -= 50;
			score -= WR_Array[7 - row][col];
			break;
		case BBishop:
			score -= 30;
			score -= WB_Array[7 - row][col];
			break;
		case BKnight:
			score -= 30;
			score -= WK_Array[7 - row][col];
			break;
		case BPawn:
			score -= 10;
			score -= WP_Array[7 - row][col];
			break;
		default:
			break;
		}

		return score;
	}

	public void createArrays() {		
		WKing_Array = new double[][]{
			{ 2, 3, 1, 0, 0, 1, 3, 2 },
			{ 2, 2, 0, 0, 0, 0, 2, 2 },
			{ -1, -2, -2, -2, -2, -2, -2, -1 },
			{ -2, -3, -3, -4, -4, -3, -3, -2 },
			{ -3, -4, -4, -5, -5, -4, -4, -3 },
			{ -3, -4, -4, -5, -5, -4, -4, -3 },
			{ -3, -4, -4, -5, -5, -4, -4, -3 },
			{ -3, -4, -4, -5, -5, -4, -4, -3 }
		};
		
		WQ_Array = new double[][]{
			{ -2, -1, -1, -0.5, -0.5, -1, -1, -2 },
			{ -1, 0, 0.5, 0, 0, 0, 0, -1 },
			{ -1, 0.5, 0.5, 0.5, 0.5, 0.5, 0, -1 },
			{ 0, 0, 0.5, 0.5, 0.5, 0.5, 0, -0.5 },
			{ -0.5, 0, 0.5, 0.5, 0.5, 0.5, 0, -0.5 },
			{ -1, 0, 0.5, 0.5, 0.5, 0.5, 0, -1 },
			{ -1, 0, 0, 0, 0, 0, 0, -1 },
			{ -2, -1, -1, -0.5, -0.5, -1, -1, -2 }
		};
		
		WR_Array = new double[][]{
			{ 0, 0, 0, 0.5, 0.5, 0, 0, 0 },
			{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 },
			{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 },
			{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 },
			{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 },
			{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 },
			{ 0.5, 1, 1, 1, 1, 1, 1, 0.5 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		
		WB_Array = new double[][]{
			{ -2, -1, -1, -1, -1, -1, -1, -2 },
			{ -1, 0.5, 0, 0, 0, 0, 0.5, -1 },
			{ -1, 1, 1, 1, 1, 1, 1, -1 },
			{ -1, 0, 1, 1, 1, 1, 0, -1 },
			{ -1, 0.5, 0.5, 1, 1, 0.5, 0.5, -1 },
			{ -1, 0, 0.5, 1, 1, 0.5, 0, -1 },
			{ -1, 0, 0, 0, 0, 0, 0, -1 },
			{ -2, -1, -1, -1, -1, -1, -1, -2 }
		};
		
		WK_Array = new double[][]{
			{ -5, -4, -3, -3, -3, -3, -4, -5 },
			{ -4, -2, 0, 0.5, 0.5, 0, -2, -4 },
			{ -3, 0.5, 1, 1.5, 1.5, 1, 0.5, -3 },
			{ -3, 0, 1.5, 2, 2, 1.5, 0, -3 },
			{ -3, 0.5, 1.5, 2, 2, 1.5, 0.5, -3 },
			{ -3, 0, 1, 1.5, 1.5, 1, 0, -3 },
			{ -4, -2, 0, 0, 0, 0, -2, -4 },
			{ -5, -4, -3, -3, -3, -3, -4, -5 }
		};
		
		WP_Array = new double[][]{
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0.5, 1, 1, -2, -2, 1, 1, 0.5 },
			{ 0.5, -0.5, -1, 0, 0, -1, -0.5, 0.5 },
			{ 0, 0, 0, 2, 2, 0, 0, 0 },
			{ 0.5, 0.5, 1, 2.5, 2.5, 1, 0.5, 0.5 },
			{ 1, 1, 2, 3, 3, 2, 1, 1 },
			{ 5, 5, 5, 5, 5, 5, 5, 5 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
	}
}
