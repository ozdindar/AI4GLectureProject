package live.boardgames.chss.bots.tunaalaygut;

import live.boardgames.ai.MiniMax;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sailor implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new AlphaBetaPruning();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new StrongEvaluator();
    }

    @Override
    public Image createProjectLogo() {
        try {
            return new Image("./src/live/boardgames/chss/bots/tunaalaygut/sailor-logo.png");
        }
        catch (SlickException e){
            return null;
        }
    }

    @Override
    public String getName() {
        return "Sailor";    //Strong AI w/ Lack of Remorse
    }
}
