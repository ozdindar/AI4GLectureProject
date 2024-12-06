package live.boardgames.chss.bots.ozan;

import live.boardgames.ai.MiniMax;
import live.boardgames.ai.MiniMaxWithTT;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AI37 implements AIProject {
	@Override
	public BoardAI createBoardAI() {
		//If it times out, it needs to be reduced to 2 depth.
		return new AlphaBetaPruning(3);
	}


	public BoardEvaluator createBoardEvaluator() {
		return new Evaluator37();
	}

	@Override
	public Image createProjectLogo() {

		try {

			return new Image("./src/live/boardgames/chss/bots/ozan/37.png");
		} catch (SlickException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getName() {
		return "AI37";
	}
}
