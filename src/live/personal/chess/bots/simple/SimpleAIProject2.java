package live.personal.chess.bots.simple;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleAIProject2 implements AIProject {

    private final int maxDepth;

    public SimpleAIProject2(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public BoardAI createBoardAI() {
        return new ABNegamax(maxDepth, new SimpleChessMoveGenerator(),false);
    }

    @Override
    public BoardEvaluator createBoardEvaluator() {
        SimpleEvaluator evaluator = new SimpleEvaluator();
        evaluator.addEvaluator(new MaterialEvaluator());
        evaluator.addEvaluator(new MateEvaluator());
        evaluator.addEvaluator(new MobilityEvaluator());
        return evaluator;
    }

    @Override
    public Image createProjectLogo() {

        try {

            return new Image("./src/live/boardgames/chss/bots/simple/res/koala.png");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "AB Negamax";
    }
}
