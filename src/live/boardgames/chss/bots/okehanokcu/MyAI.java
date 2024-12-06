package live.boardgames.chss.bots.okehanokcu;

import live.boardgames.base.Board;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.base.Move;
import live.boardgames.chss.ProjectAIPlayer;
import live.boardgames.chss.internal.ChessBoard;
import live.boardgames.chss.internal.knowledge.Chess;
import live.boardgames.chss.internal.knowledge.PieceType;

import java.util.HashMap;
import java.util.List;

public class MyAI implements BoardAI {
    private double alphaInitialValue = Integer.MIN_VALUE;
    private double betaInitialValue = Integer.MAX_VALUE;
    private int maxSearchDepth = 30;
    HashMap<Long, Double> boardKeyAndScore = new HashMap<>(); // Transposition Table.
    public static boolean timeIsUp = false;
    private int bestMoveIndexOfLastSearchedRoot = -1;

    @Override
    public Move getBestMove(Board board, BoardEvaluator evaluator) {
        long startedTime = System.currentTimeMillis();
        bestMoveIndexOfLastSearchedRoot = -1;
        timeIsUp = false;
        Runnable timeJob = new Runnable() {
            double startTime = System.currentTimeMillis();
            @Override
            public void run() {
                //System.out.println("Thread is running.");
                while(!timeIsUp)
                {
                    if ((System.currentTimeMillis() - startTime) >= (ProjectAIPlayer.MAX_THINK_TIME * 1000) - 100)
                    {
                        timeIsUp = true;
                        System.out.println("Time is up!");
                    }
                }
            }
        };
        Thread thread = new Thread(timeJob);
        thread.start();
        int convertedPlayerNo = board.currentPlayer() == 0 ? 1 : -1; // 1 is white, -1 is black.
        Move bestMove = PVSWithIterativeDeepening(board, convertedPlayerNo, evaluator);

        //System.out.println("Size of boardkeyandscore :" + boardKeyAndScore.size());
        thread.stop();
        System.out.println("Elapsed Time: " + (System.currentTimeMillis() - startedTime));
        return bestMove;
    }

    public Move PVSRoot (Board board, List<Move> moves, int depth, double alpha, double beta, int player, BoardEvaluator evaluator)
    {
        if (timeIsUp)
            return null;
        double bestScore = -100000;
        Move bestMove = moves.get(0);
        int bestMoveIndex = -1;
        for (int i = 0; i < moves.size(); i++) {
            if (timeIsUp)
                break;


            Move move = moves.get(i);
            Board newBoard = board.makeMove(move);
            double score;
            score = -PVS(
                    newBoard,
                    MoveOrdering(newBoard, newBoard.getMoves(), 0),
                    depth - 1,
                    -beta, -alpha,
                    -player,
                    evaluator);
            if (score > bestScore)
            {
                bestScore = score;
                bestMove = move;
                bestMoveIndex = i;
            }
        }

        if (timeIsUp)
        {
            return null;
        } else
        {
            //System.out.println("Best Score is : " + bestScore);
            bestMoveIndexOfLastSearchedRoot = bestMoveIndex; // Update best move index of the root.
            return bestMove;
        }
    }

    // player = 1 is me, player = -1 is opponent.
    public double PVS(Board board, List<Move> moves, int depth, double alpha, double beta, int player, BoardEvaluator evaluator) {

        if (timeIsUp)
            return 0; // Just return, does not matter what you return because pvsRoot always will return null if the time is up.

        long boardKey = board.getKey();
        double score = 0;
            if (depth == 0)
            {
                if (boardKeyAndScore.containsKey(boardKey))
                {
                    return boardKeyAndScore.get(boardKey);
                } else
                {
                    double evaluatedScore = player * evaluator.evaluate(board, player);
                    boardKeyAndScore.put(boardKey, evaluatedScore);
                    return evaluatedScore;
                }

            } else
            {
                for (int i = 0; i < moves.size(); i++) {
                    if (timeIsUp)
                        break;
                    Board newBoard = board.makeMove(moves.get(i));
                    List<Move> orderedMoves = MoveOrdering(newBoard, newBoard.getMoves(), 0);

                    if (i == 0) // If child is the first child.
                    {
                        score = -PVS(newBoard,
                                orderedMoves,
                                depth - 1,
                                -beta,
                                -alpha,
                                -player,
                                evaluator
                        );
                    } else
                    {
                        score = -PVS(newBoard,
                                orderedMoves,
                                depth - 1,
                                -alpha - 1,
                                -alpha,
                                -player,
                                evaluator
                        ); // Zero window search.
                        if (score > alpha && score < beta)
                        {
                            score = -PVS(newBoard,
                                    orderedMoves,
                                    depth - 1,
                                    -beta,
                                    -score,
                                    -player,
                                    evaluator
                            ); // Do a full research
                        }
                    }
                    alpha = Math.max(alpha, score);

                    if (alpha >= beta)
                    {
                        break; // cut off
                    }

                }
            }
        if (timeIsUp)
        {
            return 0; // Just return, does not matter what you return because pvsRoot always will return null if the time is up.
        } else
        {
            return alpha;
        }
    }

    private Move PVSWithIterativeDeepening(Board board, int convertedPlayerNo, BoardEvaluator evaluator)
    {
        Move bestMove = null;
        for (int i = 2; i <= maxSearchDepth; i++) {
            if (!timeIsUp)
            {
                // Put the best found move (in the last iteration) as the first child.
                List<Move> defaultMoves = board.getMoves();
                if (bestMoveIndexOfLastSearchedRoot != -1)
                {
                    Move tempMove = defaultMoves.get(0);
                    defaultMoves.set(0, defaultMoves.get(bestMoveIndexOfLastSearchedRoot));
                    defaultMoves.set(bestMoveIndexOfLastSearchedRoot, tempMove);
                }
                List<Move> moves = MoveOrdering(board, defaultMoves, 1); // I do not want to make the first one swapped, so I am protecting it.
                // End of Put the found move to the first child.
                Move returnedMove = PVSRoot(board, moves, i - 1, alphaInitialValue, betaInitialValue, convertedPlayerNo, evaluator);
                if (returnedMove != null) {
                    bestMove = returnedMove;
                    System.out.println("Finished depth " + i + "!");
                }
            } else
            {
                //System.out.println("!RETURNED PREVIOUS BEST MOVE!");
                break;
            }
        }
        return bestMove;
    }

    private List<Move> MoveOrdering (Board board, List<Move> moves, int orderStartIndex)
    {
        PieceType[][] pieces = ((ChessBoard)board).getBoard();
        int swapIndex = orderStartIndex;

        for (int i = orderStartIndex; i < moves.size(); i++) {
            if (timeIsUp)
                break;
            Move move = moves.get(i);
            if (Chess.isCaptureMove(pieces,move) || Chess.kingIsInCheck(pieces, board.currentPlayer()))
            {
                Move tempMove = moves.get(swapIndex);
                moves.set(swapIndex, move);
                moves.set(i, tempMove);
                swapIndex++;
            }
        }
        return moves;
    }
}
