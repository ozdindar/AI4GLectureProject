package live.personal.chess.bots.simple;


import live.boardgames.ai.EvaluatedMove;
import live.boardgames.ai.TranspositionTable;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ABNegamax implements BoardAI {

    private boolean transpose = true;
    private class TableNode{
        Move move;
        protected double score;
        protected int depth;
        protected int type; // 0 is exact, 1 is upper bound and -1 is lower bound
        public TableNode(Move move,double score, int depth, int type){
            this.move= move;
            this.score = score;
            this.depth = depth;
            this.type = type;
        }
    }


    private Map<Long, TableNode> transposition = new HashMap<>();

    long exploredNodes;
    long startTime;
    int maxDepth;


    MoveGenerator moveGenerator;


    public ABNegamax(int maxDepth, MoveGenerator moveGenerator, boolean transpose) {
        this.maxDepth= maxDepth;
        this.moveGenerator = moveGenerator;
        this.transpose = transpose;
    }

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {

        startTime = System.currentTimeMillis();
        exploredNodes =0;

        EvaluatedMove em = getBestMove(board,evaluator,maxDepth, 0,-Double.MAX_VALUE, Double.MAX_VALUE);


        printStats();
        return em.getMove();

    }

    private void printStats() {
        System.out.println("[D:"+maxDepth + " T:"+ transpose+ "]  Time:" + (System.currentTimeMillis()-startTime) +" Nodes:"+ exploredNodes );
    }

    private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator, int maxDepth, int depth, double alpha, double beta) {

        double orgAlpha = alpha;
        exploredNodes++;

        long boardKey =board.getKey();
        if(transpose && transposition.containsKey(boardKey) && transposition.get(boardKey).depth <= depth){
            TableNode tableNode = transposition.get(boardKey);
            double score = tableNode.score;
            if(transposition.get(board.getKey()).type == 0){
                return new EvaluatedMove(tableNode.move,tableNode.score);
            }else if(transposition.get(boardKey).type < 0){
                alpha = Math.max(alpha,tableNode.score);
            }else if(transposition.get(boardKey).type > 0 ){
                beta = Math.min(beta,tableNode.score);
            }

            if (alpha>=beta)
                return new EvaluatedMove(tableNode.move,tableNode.score);
        }

        if (board.isGameOver()|| depth == maxDepth)
            return new EvaluatedMove(null,evaluator.evaluate(board,board.currentPlayer()));

        double bestScore = -Integer.MAX_VALUE;
        Move bestMove = null;

        List<Move> moves = moveGenerator.generateMoves(board);

        for (Move m : moves)
        {
            Board newBoard = board.makeMove(m);
            EvaluatedMove currentChoice = getBestMove(newBoard,evaluator,maxDepth,depth+1,-beta,-alpha);
            double currentScore = -currentChoice.getScore();

            if (currentScore>bestScore)
            {
                bestMove  = m;
                bestScore = currentScore;
            }

            if (currentScore>alpha)
            {
                alpha = currentScore;
            }
            if (alpha>=beta)
                break;;
        }

        if (transpose) {
            int nodeType = (bestScore <= orgAlpha) ? 1 : (bestScore >= beta) ? -1 : 0;

            TableNode tableNode = new TableNode(bestMove, bestScore, depth, nodeType);
            transposition.put(boardKey, tableNode);
        }

        moveGenerator.setBestMove(board,bestMove,depth);
        return new EvaluatedMove(bestMove,bestScore);
    }



    public static void main(String[] args) {
        simulateAGame();
    }

    private static void testPerformance() {
        ChessBoard board = new ChessBoard();

        BoardEvaluator evaluator = new SimpleEvaluator();

        ABNegamax abNegamaxTT = new ABNegamax(4,new SimpleChessMoveGenerator(),true);
        ABNegamax abNegamax   = new ABNegamax(4,new SimpleChessMoveGenerator(),false);

        System.out.println(abNegamaxTT.getBestMove(board,evaluator));
        System.out.println(abNegamax.getBestMove(board,evaluator));

        System.out.println(abNegamaxTT.getBestMove(board,evaluator));
        System.out.println(abNegamax.getBestMove(board,evaluator));

    }

    private static void simulateAGame() {
        ChessBoard board = new ChessBoard();

        SimpleEvaluator evaluator = new SimpleEvaluator();
        evaluator.addEvaluator(new MaterialEvaluator());
        evaluator.addEvaluator(new MateEvaluator());
        evaluator.addEvaluator(new MobilityEvaluator());

        ABNegamax abNegamaxTT = new ABNegamax(4,new SimpleChessMoveGenerator(),false);
        ABNegamax abNegamax   = new ABNegamax(4,new SimpleChessMoveGenerator(),true);

        while (!board.isGameOver()) {
            Move m = abNegamax.getBestMove(board,evaluator);
            System.out.println(m);
            System.out.println(abNegamaxTT.getBestMove(board, evaluator));

            board.perform(m);
        }


    }
}
