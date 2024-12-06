package live.slick2DDemo;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Bootstrap;

public class DemoGame extends StateBasedGame {


    public static final int SID_MainMenu = 0;
    public static final int SID_InGameState = 1;

    public DemoGame(String name) {
        super(name);
        addState(new MainMenu());
        addState(new InGameState());
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        enterState(SID_MainMenu);
    }

    public static void main(String[] args) {
        Game game = new DemoGame("Demo");


        Bootstrap.runAsApplication(game,600,400,false);
    }
}
