package live.boardgames.ai;

public class MinimaxEntry {
    EvaluatedMove evaluatedMove;
    int depth;

    public MinimaxEntry(EvaluatedMove em, int depth) {
        this.evaluatedMove = em;
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
