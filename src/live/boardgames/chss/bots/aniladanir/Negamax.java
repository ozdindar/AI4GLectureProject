package live.boardgames.chss.bots.aniladanir;


import java.util.List;

import live.boardgames.ai.EvaluatedMove;
import live.boardgames.ai.TranspositionTable;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

public class Negamax implements BoardAI {

	TranspositionTable<NegamaxEntry> table = new TranspositionTable<>();
	int trueHit = 0;
	int falseHit = 0;
	public final int maxDepth;
	public Negamax(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public Move getBestMove(Board board, BoardEvaluator evaluator) {
		EvaluatedMove bestMove = abNegaMax(board, evaluator, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
		return bestMove.getMove();
	}
	
	private EvaluatedMove abNegaMax(Board board, BoardEvaluator evaluator, int depth, int alpha, int beta) {
		int a = alpha;
		long key = board.getKey();
        if (table.contains(key))
        {
            NegamaxEntry entry= table.get(key);

            if (entry.depth >= maxDepth - depth)
            {
                switch(entry.type) {
                	case lower:
                		alpha = Math.max(alpha, (int)entry.bestMove.getScore());
                		break;
                	case upper:
                		beta = Math.max(beta, (int)entry.bestMove.getScore());
                		break;
                	case exact:
                		return entry.bestMove;
                }
                if (alpha > beta) {
                	return entry.bestMove;
                }
            }
            
            table.remove(key);
        }
		
		if(depth == maxDepth || board.isGameOver()) {
			return new EvaluatedMove(null, evaluator.evaluate(board, board.currentPlayer()));
		}
			
		
		Move bestMove = null;
		int bestScore = -Integer.MAX_VALUE;
		
 		List<Move> moves = board.getMoves();
		
		for(Move move : orderMoves(moves, board, evaluator, board.currentPlayer())) {
			Board newBoard = board.makeMove(move);
			EvaluatedMove currentMove = abNegaMax(newBoard, evaluator, depth + 1, -beta, -Math.max(alpha,bestScore));
			
			int currentScore = -(int)currentMove.getScore();
			
			if(currentScore > bestScore) {
				bestMove = move;
				bestScore = currentScore;
				
				if(bestScore >= beta)
					return new EvaluatedMove(bestMove, bestScore);
			}
			
		}
		
		
		NegamaxEntry entry = new NegamaxEntry();
		
		if(bestScore <= a)
			entry.type = ScoreType.upper;
		else if(bestScore >= beta)
			entry.type = ScoreType.lower;
		else 
			entry.type = ScoreType.exact;
		entry.depth = depth;
		entry.bestMove = new EvaluatedMove(bestMove, bestScore);
		
		table.put(key, entry);
		
		
		return new EvaluatedMove(bestMove, bestScore);
	}
	 
	
	private List<Move> orderMoves(List<Move> moves, Board board, BoardEvaluator evaluator, int player){
		
		int bestScore = Integer.MIN_VALUE;
		MyBoardEvaluator e = (MyBoardEvaluator)evaluator;
		
		for(int i = 0; i < moves.size(); i++) {
			Board newBoard = board.makeMove(moves.get(i));
			int score = 0;
			long key = newBoard.getKey();
			if(table.contains(key) && table.get(key).type == ScoreType.exact)
				score = (int)table.get(key).bestMove.getScore();
			else
				score = e.simpleEvaluate(newBoard, player);
			
			if(score > bestScore) {
				bestScore = score;
				Move bestMove = moves.get(i);
				moves.remove(i);
				moves.add(0, bestMove);
			}
			
			
		}
		
		return moves;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
