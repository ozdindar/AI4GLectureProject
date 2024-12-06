package live.boardgames.chss.bots.berkaybayindir;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.util.RandomUtils;

import java.util.List;

public class LynxAi implements BoardAI {
    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        List<Move> moveList = board.getMoves();
        if (moveList.isEmpty())
            return null;
        int i = RandomUtils.randomInt(moveList.size()); // Hocam burda kaçıncı move u kullanıcağımı anlamadım o yüzden burası random kaldı.
        return moveList.get(i);
    }
}
