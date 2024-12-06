package live.boardgames.chss.bots.aniladanir;

import org.newdawn.slick.Image;

import live.boardgames.ai.MiniMax;
import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import live.boardgames.chss.bots.sample.NullEvaluator;

public class MyAIProject  implements AIProject{

	@Override
	public BoardAI createBoardAI() {
		// TODO Auto-generated method stub
		return new Negamax(4);
	}

	@Override
	public BoardEvaluator createBoardEvaluator() {
		// TODO Auto-generated method stub
		return new MyBoardEvaluator();
	}

	@Override
	public Image createProjectLogo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Yatay Zeka";
	}

}
