package live.boardgames.chss.bots.halil;

import live.boardgames.ai.AlphaBetaPruning;
import live.boardgames.ai.MiniMax;
import live.boardgames.ai.MiniMaxWithTT;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HalilAIProject implements AIProject {
	@Override
	public BoardAI createBoardAI() {
		return new AlphaBetaPruning(3);
	}

	@Override
	public BoardEvaluator createBoardEvaluator() {
		return new HalilEvaluator();
	}

	@Override
	public Image createProjectLogo() {

		try {

			return new Image("./src/live/boardgames/chss/sample/logo.jpg");
		} catch (SlickException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getName() {
		return "HALOSHOT";
	}
}
