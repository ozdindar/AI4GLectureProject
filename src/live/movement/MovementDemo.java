package live.movement;




import live.movement.steering.*;
import math.geom2d.Vector2D;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Bootstrap;

import java.awt.geom.Rectangle2D;

public class MovementDemo extends StateBasedGame {
    private static final int DemoWidth = 800;
    private static final int DemoHeight = 600;
    private final int movementPanel=0;



    public MovementDemo(String name) {
        super(name);
        addState(new MovementPanel());
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        enterState(movementPanel);
    }

    MovementPanel getPanel()
    {
        return (MovementPanel) getState(movementPanel);
    }

    public static void main(String[] args) {
        MovementDemo demo = new MovementDemo("Movement Demo");

        MovementPanel panel = demo.getPanel();

        MovingEntity redBall = new MovingEntity(new StaticInfo( new Vector2D(300,400),
                                                                        5,OrientationType.VelocityBased
                                                                    ), new Ball(Color.red,20));

        redBall.setVelocity(new Velocity(new Vector2D(2,-2),0));



        MovingEntity greenBall = new MovingEntity(new StaticInfo( new Vector2D(200,100),
                                                            0,OrientationType.VelocityBased
                                                    ), new Ball(Color.green,20));


        MovingEntity blueBall = new MovingEntity(new StaticInfo( new Vector2D(500,300),
                                                0,OrientationType.VelocityBased
                                                  ), new Ball(Color.blue,20));

        greenBall.setVelocity(new Velocity(new Vector2D(0,0),0));
        greenBall.setSteeringBehavior(new Pursue(redBall,3));



        blueBall.setVelocity(Velocity.NoVelocity);



        panel.addEntity(redBall);
        panel.addEntity(greenBall);
        panel.addEntity(blueBall);


        Bootstrap.runAsApplication(demo,DemoWidth,DemoHeight,false);

    }


}
