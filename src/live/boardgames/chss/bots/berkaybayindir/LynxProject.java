package live.boardgames.chss.bots.berkaybayindir;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LynxProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new LynxAi();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new EvaluatorLynx();
    }

    @Override
    public Image createProjectLogo() {

            return null;

    }

    @Override
    public String getName() {
        return "berkaybayindir";
    }
}
