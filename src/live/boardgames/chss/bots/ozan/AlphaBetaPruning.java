package live.boardgames.chss.bots.ozan;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class AlphaBetaPruning implements BoardAI {
	int maxDepth;
	//int count = 9;
	//boolean thinking = false;
	//Thread thread;

	public AlphaBetaPruning(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public Move getBestMove(Board board, BoardEvaluator evaluator) {

		EvaluatedMove em = getBestMove(board, evaluator, board.currentPlayer(), 0, -Double.MAX_VALUE, Double.MAX_VALUE);

		return em.getMove();
	}

	private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int player, int depth, double alpha,
			double beta) {
		if (board.isGameOver() || depth > maxDepth) {
			double score = evaluator.evaluate(board, player);
			return new EvaluatedMove(null, score);
		}

		List<Move> moves = board.getMoves();
		EvaluatedMove bestMove = new EvaluatedMove(null, 0);

		//Trying to maximize
		if (board.currentPlayer() == 0) {
			//Start from -infinity
			bestMove = new EvaluatedMove(null, -Double.MAX_VALUE);
			for (Move m : moves) {
				Board newBoard = board.makeMove(m);
				EvaluatedMove value = getBestMove(newBoard, evaluator, 1, depth + 1, alpha, beta);

				if (value.getScore() > bestMove.getScore()) {
					bestMove = new EvaluatedMove(m, value.getScore());
				}

				alpha = Math.max(alpha, bestMove.getScore());

				//Prunning
				if (beta <= alpha) {
					break;
				}
			}
			return bestMove;

		}

		//Trying to minimize
		else {
			//Starts from + infinity
			bestMove = new EvaluatedMove(null, +Double.MAX_VALUE);
			for (Move m : moves) {
				Board newBoard = board.makeMove(m);
				EvaluatedMove value = getBestMove(newBoard, evaluator, 0, depth + 1, alpha, beta);

				if (value.getScore() < bestMove.getScore()) {
					bestMove = new EvaluatedMove(m, value.getScore());
				}

				beta = Math.min(beta, bestMove.getScore());
				
				//Prunning
				if (beta <= alpha) {
					break;
				}
			}
			return bestMove;

		}
	}
}