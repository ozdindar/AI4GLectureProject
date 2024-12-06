package live.boardgames.chss.bots.AlaraIscan;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Frida implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new FridaAI();
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new FridaEvaluator();
    }

    @Override
    public Image createProjectLogo() {

        try {
            return new Image("./src/live/boardgames/chss/AlaraIscan/fridaLogo.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "AlaraIscan";
    }
}
