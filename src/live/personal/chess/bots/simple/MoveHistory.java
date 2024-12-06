package live.personal.chess.bots.simple;

import live.boardgames.base.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveHistory {
    List<Move> moves;

    public MoveHistory(List<Move> moves) {
        this.moves = moves;
    }

    public void setBestMove(Move bestMove) {
        int index= moves.indexOf(bestMove);
        if (index>0)
        {
            moves.remove(index);
            moves.add(0,bestMove);
        }
    }

    public List<Move> cloneMoves() {
        List<Move> cloned= new ArrayList<>();
        for (Move m:moves)
        {
            cloned.add(m);
        }

        return cloned;
    }
}
