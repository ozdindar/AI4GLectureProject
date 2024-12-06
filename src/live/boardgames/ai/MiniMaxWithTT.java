package live.boardgames.ai;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.List;

public class MiniMaxWithTT implements BoardAI {
    int maxDepth;

    TranspositionTable<MinimaxEntry> table = new TranspositionTable<>();
    private long trueHitCount=0;
    private long falseHitCount=0;

    public MiniMaxWithTT(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        EvaluatedMove em =getBestMove(board,evaluator, board.currentPlayer(),0);

        return em.getMove();
    }

    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int player, int depth) {

        long key = board.getKey();
         if (table.contains(key))
         {
             MinimaxEntry entry= table.get(key);

             if (entry.depth >= maxDepth-depth)
             {
                 System.out.println("True Hit Count:" + trueHitCount++);
                 return entry.evaluatedMove;
             }
             else       System.out.println("False Hit Count:" + falseHitCount++);
             table.remove(key);
         }

        if (board.isGameOver() || depth>maxDepth)
        {
            double score = evaluator.evaluate(board,player);
            EvaluatedMove em = new EvaluatedMove(null,score);
            table.put(key,new MinimaxEntry(em,0));
            return em;
        }

        List<Move> moves= board.getMoves();

        Move bestMove = null;
        double bestScore = (player == board.currentPlayer()) ? - Double.MAX_VALUE: Double.MAX_VALUE;

        for (Move m:moves)
        {
            Board newBoard= board.makeMove(m);
            EvaluatedMove opponentMove = getBestMove(newBoard,evaluator,player,depth+1);

            if ( ( player == board.currentPlayer()&& bestScore<opponentMove.getScore())||
                 ( player != board.currentPlayer()&& bestScore>opponentMove.getScore()))
            {
                bestMove = m;
                bestScore = opponentMove.getScore();
            }


        }

        EvaluatedMove em = new EvaluatedMove(bestMove,bestScore);
        table.put(key,new MinimaxEntry(em,maxDepth-depth) );
        return new EvaluatedMove(bestMove,bestScore);
    }
}
