package live.personal.chess.bots.simple;

import javafx.util.Pair;
import live.boardgames.ai.TranspositionTable;
import live.boardgames.base.Board;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;

import java.util.List;

public class SimpleChessMoveGenerator implements MoveGenerator {


   TranspositionTable<MoveHistory> moveHistoryTable = new TranspositionTable<>();
    TranspositionTable<Pair<Integer,Move>> bestMoveTable = new TranspositionTable<>();

   MoveOrderer moveOrderer = new SimpleChessMoveOrderer();
    private long hitCount=0;

    @Override
    public List<Move> generateMoves(Board b) {
        ChessBoard cb = (ChessBoard) b;

        if (moveHistoryTable.contains(cb.getKey()))
        {
            //System.out.println("Hit Count:"+ hitCount++);
            return moveHistoryTable.get(cb.getKey()).cloneMoves();
        }
        List<Move> moves = Chess.getMoves(cb.getBoard(),b.currentPlayer());


        moveHistoryTable.put(cb.getKey(),new MoveHistory(moves));
        return moves;
    }

    @Override
    public void setBestMove(Board board, Move bestMove, int depth) {
        if (bestMoveTable.contains(board.getKey()))
        {
            Pair<Integer,Move> entry = bestMoveTable.get(board.getKey());
            if (entry.getKey()<=depth)
                return;
        }
        bestMoveTable.put(board.getKey(), new Pair<>(depth,bestMove));
        if (moveHistoryTable.get(board.getKey())!= null)
            moveHistoryTable.get(board.getKey()).setBestMove(bestMove);
    }
}
