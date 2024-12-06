package live.personal.chess.bots.simple;


import live.boardgames.ai.EvaluatedMove;
import live.boardgames.ai.TranspositionTable;
import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edreichua on 2/1/16.
 */
public class AlphaBetaAI implements BoardAI {

    private final static boolean transpose = true;
    private int nodesExplored = 0;
    private int depthExplored = 0;
    private final static int WIN_UTILITY = Integer.MAX_VALUE;
    private final static int LOSS_UTILITY = Integer.MIN_VALUE;
    private final static int DRAW_UTILITY = 0;
    private int MAX_DEPTH = 7;
    private static int CURR_DEPTH = 1;
    private static int aiPlayer;


    private TranspositionTable<tableNode> transpositionTable = new TranspositionTable<>();
    private Map<Long,tableNode> transposition = new HashMap<>();

    BoardEvaluator evaluator;
    MoveGenerator moveGenerator;

    public AlphaBetaAI(int maxDepth, SimpleChessMoveGenerator moveGenerator) {
        this.moveGenerator = moveGenerator;
        MAX_DEPTH = maxDepth;
    }


    private class tableNode{
        protected int score;
        protected int depth;
        protected int type; // 0 is exact, 1 is upper bound and -1 is lower bound
        public tableNode(int score, int depth, int type){
            this.score = score;
            this.depth = depth;
            this.type = type;
        }
    }

    // create wrapper class so alphaBetaSearch can return two variables
    private class node{
        protected short move;
        protected int utility;

        public node(short move, int utility){
            this.move = move;
            this.utility = utility;
        }

        public node(){
            this.move = Short.MIN_VALUE;
            this.utility = LOSS_UTILITY;
        }
    }

    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        aiPlayer = board.currentPlayer();
        this.evaluator = evaluator;
        EvaluatedMove bestMove = null;

        for(int i=0; i<MAX_DEPTH; i++){

            try {
                CURR_DEPTH = i;
                bestMove = alphaBetaSearch(board);

                //printStats();
                //resetStats();

                // short circuit for best move found
                if(bestMove.getScore() == WIN_UTILITY){
                    System.out.println("Utilty function yield score: "+bestMove.getScore());
                    return bestMove.getMove();
                }

            }catch(Exception e){
                e.printStackTrace();
            }

        }
        System.out.println("Utility function yield score: "+bestMove.getScore());
        return bestMove.getMove();
    }

    // alpha beta search algorithm (pp 170 of book)
    private EvaluatedMove alphaBetaSearch(Board board) throws Exception{

        Move bestMove = null;
        int bestMoveUtility = LOSS_UTILITY;

        List<Move> moves = moveGenerator.generateMoves(board);

        for(Move move: moves){
            Board newBoard = board.makeMove(move);

            int currMoveUtility = minValue(newBoard, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if(currMoveUtility > bestMoveUtility){
                bestMoveUtility = currMoveUtility;
                bestMove = move;
            }
        }
        return new EvaluatedMove(bestMove,bestMoveUtility);
    }

    // calculate min value for alpha beta algorithm (pp 170 of book)
    private int minValue(Board board, int depth, int alpha, int beta) throws Exception {

        int min = WIN_UTILITY;
        //incrementNodeCount();
        updateDepth(depth);

        long boardKey =board.getKey();
        if(transpose && transposition.containsKey(boardKey) && transposition.get(boardKey).depth > CURR_DEPTH){
            int score = transposition.get(boardKey).score;
            if(transposition.get(board.getKey()).type == 0){
                return score;
            }else if(transposition.get(boardKey).type < 0 && score >= beta){
                return score;
            }else if(transposition.get(boardKey).type > 0 && score <= alpha){
                return score;
            }else if(beta <= alpha){
                return score;
            }
        }
        if(cutoffTest(board,depth)){
            int score = (int) evaluator.evaluate(board,board.currentPlayer());
            if(transpose && score <= alpha){
                transposition.put(boardKey,new tableNode(score,depth,1));
            }else if(transpose && score >= beta){
                transposition.put(boardKey,new tableNode(score,depth,-1));
            }else if(transpose){
                transposition.put(boardKey,new tableNode(score,depth,0));
            }
            return score;
        }

        List<Move> moves = moveGenerator.generateMoves(board);
        for(Move move: moves){
            Board newBoard = board.makeMove(move);

            min = Math.min(min,maxValue(newBoard,depth+1,alpha,beta));


            //pruning
            if(min <= alpha){
                return min;
            }
            beta = Math.min(beta,min);

        }
        return min;
    }

    // calculate max value for alpha beta algorithm (pp 166 of book)
    private int maxValue(Board board, int depth, int alpha, int beta) throws Exception{

        int max = LOSS_UTILITY;
        //incrementNodeCount();
        updateDepth(depth);

        long boardKey = board.getKey();

        if(transpose && transposition.containsKey(boardKey) && transposition.get(boardKey).depth > CURR_DEPTH){
            int score = transposition.get(boardKey).score;
            if(transposition.get(boardKey).type == 0){
                return score;
            }else if(transposition.get(boardKey).type < 0 && score >= beta){
                return score;
            }else if(transposition.get(boardKey).type > 0 && score <= alpha){
                return score;
            }else if(beta <= alpha){
                return score;
            }
        }
        if(cutoffTest(board,depth)){
            int score = (int) evaluator.evaluate(board,board.currentPlayer());
            if(transpose && score <= alpha){
                transposition.put(boardKey,new tableNode(score,depth,1));
            }else if(transpose && score >= beta){
                transposition.put(boardKey,new tableNode(score,depth,-1));
            }else if(transpose){
                transposition.put(boardKey,new tableNode(score,depth,0));
            }
            return score;
        }

        List<Move> moves = moveGenerator.generateMoves(board);
        for(Move move: moves){
            Board newBoard = board.makeMove(move);
            max = Math.max(max, minValue(newBoard, depth + 1, alpha, beta));


            //pruning
            if(max >= beta){
                return max;
            }
            alpha = Math.max(alpha, max);

        }
        return max;
    }




    // return true if game has ended or the maximum depth has been searched
    private boolean cutoffTest(Board board, int depth){
        return (depth >= CURR_DEPTH || board.isGameOver());
    }

    protected void resetStats() {
        nodesExplored = 0;
        depthExplored = 0;
    }

    protected void printStats() {
        System.out.println("Nodes explored during last search:  " + nodesExplored);
        System.out.println("Depth explored during last search " + depthExplored);
    }

    protected void updateDepth(int depth) {
        depthExplored = Math.max(depth, depthExplored);
    }

    protected void incrementNodeCount() {
        nodesExplored++;
    }



}