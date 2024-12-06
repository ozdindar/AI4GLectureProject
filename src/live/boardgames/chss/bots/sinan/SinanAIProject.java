package live.boardgames.chss.bots.sinan;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import live.personal.chess.bots.simple.SimpleEvaluator;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SinanAIProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new AI(2);
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new SimpleEvaluator();
    }

    @Override
    public Image createProjectLogo() {

        try {

            return new Image("./src/live/boardgames/chss/bots/sinan/Cockroach.png");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "Cockroach 1.0";
    }
}
