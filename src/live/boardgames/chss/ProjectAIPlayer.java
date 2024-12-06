package live.boardgames.chss;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import live.boardgames.base.*;
import org.newdawn.slick.Image;


import java.util.concurrent.*;

public class ProjectAIPlayer extends AbstractPlayer implements AIPlayer {

    public final static int MAX_THINK_TIME = 10;
    public final static int MAX_TIMEOUT = 2;
    private final AIProject project;

    BoardAI ai;
    BoardEvaluator evaluator;

    Image logo;


    int maxTimePerMove;
    private boolean calculating = false;
    private int timeOutCount;

    public ProjectAIPlayer( AIProject project) {
        super(project.getName(), PlayerType.AIPlayer);
        this.ai = project.createBoardAI();
        this.evaluator = project.createBoardEvaluator();
        this.maxTimePerMove = MAX_THINK_TIME;
        this.project = project;
    }



    public void calculateMoveSimple(Board board) {

        //move = ai.getBestMove(board,evaluator);

        TimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());


        Callable<Move> timedAIJob = () -> ai.getBestMove(board, evaluator);

        try {
            move = timeLimiter.callWithTimeout(timedAIJob,maxTimePerMove, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("TIMEOUT! First move will be played..");
            move = board.getMoves().get(0);
            timeOutCount++;

        }



    }



    public void calculateMove(Board board) {

        if (timeOutCount>MAX_TIMEOUT)
        {
            System.out.println("MAX_TIMEOUT is reached. Returning the first move!");
            if (!board.isGameOver())
                move = board.getMoves().get(0);
            return;
        }
        if (calculating)
            return;

        calculating = true;
        Runnable aiJob= new Runnable() {
            @Override
            public void run() {
                TimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());


                Callable<Move> timedAIJob = () -> ai.getBestMove(board, evaluator);

                try {
                    move = timeLimiter.callWithTimeout(timedAIJob,maxTimePerMove, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    System.out.println("TIMEOUT! First move will be played..");
                    move = board.getMoves().get(0);
                    timeOutCount++;

                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    move = board.getMoves().get(0);
                } catch (ExecutionException e) {
                    System.out.println(e.getMessage());
                    move = board.getMoves().get(0);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    move = board.getMoves().get(0);
                }
                calculating = false;
            }


        };

        Thread aiThread = new Thread(aiJob);
        aiThread.start();



    }


    @Override
    public Image getLogo() {
        return project.createProjectLogo();
    }

    @Override
    public void init(Board board) {
        timeOutCount =0;
        calculating =false;
    }
}
