package live.boardgames.chss.bots.AlaraIscan;

import live.boardgames.ai.EvaluatedMove;

public class AlphaBetaPrunEntry {
    EvaluatedMove evaluatedMove;
    int depth;

    public AlphaBetaPrunEntry(EvaluatedMove bm, int depth) {
        this.evaluatedMove = bm;
        this.depth = depth;
    }

    public EvaluatedMove getEvaluatedMove() {
        return evaluatedMove;
    }

    public void setEvaluatedMove(EvaluatedMove evaluatedMove) {
        this.evaluatedMove = evaluatedMove;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
