package live.boardgames.chss.bots.sample;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SampleAIProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new RandomAI();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new NullEvaluator();
    }

    @Override
    public Image createProjectLogo() {

        try {

            return new Image("./src/live/boardgames/chss/sample/logo.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "SampleBot";
    }
}
