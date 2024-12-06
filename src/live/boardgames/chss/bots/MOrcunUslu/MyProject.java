package live.boardgames.chss.bots.MOrcunUslu;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MyProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new NegaMax(4);
   }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new Evaluator_v2();
    }

    @Override
    public Image createProjectLogo() {
        try {
            return new Image("live/boardgames/chss/bots/MOrcunUslu/Qrow.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "     Qrow";
    }
}
