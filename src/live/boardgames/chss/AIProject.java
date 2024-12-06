package live.boardgames.chss;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import org.newdawn.slick.Image;

public interface AIProject {
    BoardAI createBoardAI();
    BoardEvaluator createBoardEvaluator();
    Image createProjectLogo();
    String getName();

}
