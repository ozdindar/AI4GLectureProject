package live.movement;



import live.movement.steering.SteeringBehavior;
import math.geom2d.Vector2D;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.geom.Rectangle2D;

public class MovingEntity extends BasicGameEntity {


    double maxSpeed = 3;

    private static final float TIME_COEFFICIENT = 0.1f;
    Velocity velocity;

    Acceleration acceleration = Acceleration.NoAcceleration;

    public void setSteeringBehavior(SteeringBehavior steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    SteeringBehavior steeringBehavior;


    CollisionHandler collisionHandler = new SimpleBoundaryCollisionHandler();


    Rectangle2D boundary;

    public void setBoundary(Rectangle2D boundary) {
        this.boundary = boundary;
    }



    public MovingEntity(StaticInfo staticInfo, Renderable body) {
        super(staticInfo, body);
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame, Rectangle2D boundary) {
        super.init(gameContainer, stateBasedGame,boundary);

        this.boundary = boundary;

    }



    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, float time) {
        super.update(gameContainer, stateBasedGame, time);

        if (steeringBehavior != null)
            acceleration = steeringBehavior.getSteering(staticInfo,velocity);

        time = 1*TIME_COEFFICIENT; // for debug time :1

        if (collisionHandler!=null)
            collisionHandler.handle(this);

        staticInfo.update(velocity,acceleration,time);

        updateVelocity(acceleration,time);




    }

    private void updateVelocity(Acceleration acceleration, float time) {
        velocity.update(acceleration,time);

        if (velocity.linear.norm()>maxSpeed)
        {
            velocity.setLinear(velocity.linear.normalize().times(maxSpeed));
        }
    }

    public Rectangle2D getBoundary() {
        return boundary;
    }

    public Velocity getVelocity() {
        return velocity;
    }
}
