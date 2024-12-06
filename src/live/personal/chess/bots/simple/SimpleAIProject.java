package live.personal.chess.bots.simple;

import live.boardgames.base.BoardAI;
import live.boardgames.base.BoardEvaluator;
import live.boardgames.chss.AIProject;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleAIProject implements AIProject {

    private final int maxDepth;
    private final boolean transpose;


    public SimpleAIProject() {
        maxDepth = 3;
        transpose = true;

    }

    public SimpleAIProject(int maxDepth, boolean transposition) {
        this.maxDepth = maxDepth;
        this.transpose = transposition;
    }

    @Override
    public BoardAI createBoardAI() {
        return new ABNegamax(maxDepth, new SimpleChessMoveGenerator(),transpose);
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

            return new Image("./src/live/personal/chess/bots/simple/res/koala.png");
        } catch (SlickException e) {
            return null;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public String getName() {
        return "AB T:"+ transpose + " D:"+ maxDepth;
    }
}
