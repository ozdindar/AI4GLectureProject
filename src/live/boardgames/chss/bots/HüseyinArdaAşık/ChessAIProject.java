package live.boardgames.chss.bots.HüseyinArdaAşık;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ChessAIProject implements AIProject {
    @Override
    public BoardAI createBoardAI() {
        return new ChessAI(3);
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        return new ChessBoardEvaluater();
    }

    @Override
    public Image createProjectLogo() {
        try {

            return new Image("./src/live/boardgames/chss/HüseyinArdaAşık/logo.jpg");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "King Lothric";
    }
}
