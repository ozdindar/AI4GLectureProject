package live.boardgames.chss.bots.ozan;

import live.boardgames.base.Board;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.PieceType;

public class Evaluator37 implements BoardEvaluator {

	double[][] king;
	double[][] queen;
	double[][] rook;
	double[][] bishop;
	double[][] knight;
	double[][] pawn;

	public Evaluator37() {
		super();
		arrayInit();
	}

	private void arrayInit() {
		king = new double[][] { { 2, 3, 1, 0, 0, 1, 3, 2 }, /* Row 0 */
				{ 2, 2, 0, 0, 0, 0, 2, 2 }, /* Row 1 */
				{ -1, -2, -2, -2, -2, -2, -2, -1 }, /* Row 2 */
				{ -2, -3, -3, -4, -4, -3, -3, -2 }, /* Row 3 */
				{ -3, -4, -4, -5, -5, -4, -4, -3 }, /* Row 4 */
				{ -3, -4, -4, -5, -5, -4, -4, -3 }, /* Row 5 */
				{ -3, -4, -4, -5, -5, -4, -4, -3 }, /* Row 6 */
				{ -3, -4, -4, -5, -5, -4, -4, -3 }, /* Row 7 */ };

		queen = new double[][] { { -2, -1, -1, -0.5, -0.5, -1, -1, -2 }, /* Row 0 */
				{ -1, 0, 0.5, 0, 0, 0, 0, -1 }, /* Row 1 */
				{ -1, 0.5, 0.5, 0.5, 0.5, 0.5, 0, -1 }, /* Row 2 */
				{ 0, 0, 0.5, 0.5, 0.5, 0.5, 0, -0.5 }, /* Row 3 */
				{ -0.5, 0, 0.5, 0.5, 0.5, 0.5, 0, -0.5 }, /* Row 4 */
				{ -1, 0, 0.5, 0.5, 0.5, 0.5, 0, -1 }, /* Row 5 */
				{ -1, 0, 0, 0, 0, 0, 0, -1 }, /* Row 6 */
				{ -2, -1, -1, -0.5, -0.5, -1, -1, -2 } /* Row 7 */ };

		rook = new double[][] { { 0, -1, -1, -1, -1, -1, -1, 0 }, /* Row 0 */
				{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 }, /* Row 1 */
				{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 }, /* Row 2 */
				{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 }, /* Row 3 */
				{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 }, /* Row 4 */
				{ -0.5, 0, 0, 0, 0, 0, 0, -0.5 }, /* Row 5 */
				{ 0.5, 1, 1, 1, 1, 1, 1, 0.5 }, /* Row 6 */
				{ 0, 0, 0, 0, 0, 0, 0, 0 } /* Row 7 */ };

		bishop = new double[][] { { -2, -1, -1, -1, -1, -1, -1, -2 }, /* Row 0 */
				{ -1, 0.5, 0, 0, 0, 0, 0.5, -1 }, /* Row 1 */
				{ -1, 1, 1, 1, 1, 1, 1, -1 }, /* Row 2 */
				{ -1, 0, 1, 1, 1, 1, 0, -1 }, /* Row 3 */
				{ -1, 0.5, 0.5, 1, 1, 0.5, 0.5, -1 }, /* Row 4 */
				{ -1, 0, 0.5, 1, 1, 0.5, 0, -1 }, /* Row 5 */
				{ -1, 0, 0, 0, 0, 0, 0, -1 }, /* Row 6 */
				{ -2, -1, -1, -1, -1, -1, -1, -2 } /* Row 7 */ };

		knight = new double[][] { { -5, -4, -3, -3, -3, -3, -4, -5 }, /* Row 0 */
				{ -4, -2, 0, 0.5, 0.5, 0, -2, -4 }, /* Row 1 */
				{ -3, 0.5, 1, 1.5, 1.5, 1, 0.5, -3 }, /* Row 2 */
				{ -3, 0, 1.5, 2, 2, 1.5, 0, -3 }, /* Row 3 */
				{ -3, 0.5, 1.5, 2, 2, 1.5, 0.5, -3 }, /* Row 4 */
				{ -3, 0, 1, 1.5, 1.5, 1, 0, -3 }, /* Row 5 */
				{ -4, -2, 0, 0, 0, 0, -2, -4 }, /* Row 6 */
				{ -5, -4, -3, -3, -3, -3, -4, -5 } /* Row 7 */ };

		pawn = new double[][] { { 0, 0, 0, 0, 0, 0, 0, 0 }, /* Row 0 */
				{ 0.5, 1, 1, -2, -2, 1, 1, 0.5 }, /* Row 1 */
				{ 0.5, 2, -1, -5, -2, -1, 2, 0.5 }, /* Row 2 */
				{ 0, 2, 0, 2, 2, 0, 2, 0 }, /* Row 3 */
				{ 0.5, 0.5, 1, 2.5, 2.5, 1, 0.5, 0.5 }, /* Row 4 */
				{ 1, 1, 2, 3, 3, 2, 1, 1 }, /* Row 5 */
				{ 5, 5, 5, 5, 5, 5, 5, 5 }, /* Row 6 */
				{ 0, 0, 0, 0, 0, 0, 0, 0 }/* Row 7 */ };
	}

	@Override
	public double evaluate(Board board, int player) {
		ChessBoard chessBoard = (ChessBoard) board;
		double totalPoint = 0, point = 0;

		if (chessBoard.isGameOver()) {
			if (chessBoard.winner() == 0) {
				return Double.MAX_VALUE;
			} else if (chessBoard.winner() == 1) {
				return -Double.MAX_VALUE;
			} else {
				return 0;
			}
		}

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				PieceType piece = chessBoard.get(r, c);
				point = EvaluateFunciton(piece, r, c);
				totalPoint += point;
			}
		}

		return totalPoint;
	}

	private double EvaluateFunciton(PieceType p, int r, int c) {
		double point = 0;
		//int index;

		switch (p) {
		case WKing:
			point += 900;
			point += king[r][c];
			break;
		case WQueen:
			point += 90;
			point += queen[r][c];
			break;
		case WRook:
			point += 50;
			point += rook[r][c];
			break;
		case WBishop:
			point += 30;
			point += bishop[r][c];
			break;
		case WKnight:
			point += 30;
			point += knight[r][c];
			break;
		case WPawn:
			point += 10;
			point += pawn[r][c];
			break;
		case BKing:			
			point -= 900;
			r = 7 - r;
			point -= king[r][c];
			break;
		case BQueen:
			point -= 90;
			r = 7 - r;
			point -= queen[r][c];
			break;
		case BRook:
			point -= 50;
			r = 7 - r;
			point -= rook[r][c];
			break;
		case BBishop:
			point -= 30;
			r = 7 - r;
			point -= bishop[r][c];
			break;
		case BKnight:
			point -= 30;
			r = 7 - r;
			point -= knight[r][c];
			break;
		case BPawn:
			point -= 10;
			r = 7 - r;
			point -= pawn[r][c];
			break;
		default:
			break;
		}

		return point;
	}
}
