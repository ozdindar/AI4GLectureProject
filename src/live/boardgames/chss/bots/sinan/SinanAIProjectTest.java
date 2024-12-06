package live.boardgames.chss.bots.sinan;

import live.boardgames.ai.MiniMax;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SinanAIProjectTest implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new MiniMax(2);
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new Evaluator();
    }

    @Override
    public Image createProjectLogo() {

        try {

            return new Image("C:/AIforGames/live/boardgames/chss/bots/sinan/Cockroach.png");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "Cockroach but worse";
    }
}
