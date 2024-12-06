package live.boardgames.chss.bots.botMutluDikmen;

//15070006003 Mutlu Dikmen

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import live.boardgames.chss.bots.sample.NullEvaluator;
import live.boardgames.chss.bots.sample.RandomAI;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AIProject_X implements AIProject {

    @Override
    public BoardAI createBoardAI() {
        return new BoardAI_X();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new BoardEvaluatorX();
    }

    @Override
    public Image createProjectLogo() {

        try {

            return new Image("./src/live/boardgames/chss/bots/botMutluDikmen/alucard.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "Alucard";
    }
}
