package live.personal.chess.bots.simple;

import live.boardgames.base.Board;
import live.boardgames.base.Move;

import java.util.Comparator;

public interface MoveOrderer extends Comparator<Move> {

    void setBoard(Board board);
}
