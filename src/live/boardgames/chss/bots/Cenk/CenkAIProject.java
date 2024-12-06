package live.boardgames.chss.bots.Cenk;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CenkAIProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new AI();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new Evaluator();
    }

    @Override
    public Image createProjectLogo() {
        try {
            return new Image("./live/boardgames/chss/bots/Cenk/beans.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }


    @Override
    public String getName() {
        return "Bean boy";
    }
}
