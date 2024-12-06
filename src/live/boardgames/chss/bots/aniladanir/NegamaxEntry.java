package live.boardgames.chss.bots.aniladanir;

import live.boardgames.ai.EvaluatedMove;

public class NegamaxEntry {

	public ScoreType type;
	public int depth;
	public EvaluatedMove bestMove;
	
	public NegamaxEntry() {}
	
	public NegamaxEntry(ScoreType type, byte depth, EvaluatedMove bestMove) {
		this.type = type;
		this.depth = depth;
		this.bestMove = bestMove;
	}
	
	
	
	
}
