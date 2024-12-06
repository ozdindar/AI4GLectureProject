package live.boardgames.chss.bots.okehanokcu;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HiraOkehanProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new MyAI();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new MyEvaluator();
    }

    @Override
    public Image createProjectLogo() {

        try {
            return new Image("./src/live/boardgames/chss/bots/okehanokcu/wojak.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "Big Brain";
    }
}
