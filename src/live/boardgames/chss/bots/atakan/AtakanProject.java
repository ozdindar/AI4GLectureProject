package live.boardgames.chss.bots.atakan;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AtakanProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new AlphaBeta_1(4);
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new ChessEvaluator();
    }

    @Override
    public Image createProjectLogo() {
        try {

            return new Image("./res/white_elephant.png");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "N/A";
    }
}
