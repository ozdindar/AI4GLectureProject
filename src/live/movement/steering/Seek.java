package live.movement.steering;

import live.movement.Acceleration;
import live.movement.AccelerationType;
import live.movement.StaticInfo;
import live.movement.Velocity;
import math.geom2d.Vector2D;

public class Seek implements SteeringBehavior {

    Vector2D target;

    double maxAcceleration;

    public Seek(Vector2D target, double maxAcceleration) {
        this.target = target;
        this.maxAcceleration = maxAcceleration;
    }

    @Override
    public Acceleration getSteering(StaticInfo staticInfo, Velocity velocity) {

        Vector2D linear = target.minus(staticInfo.getPos());

        if (linear.norm()>maxAcceleration)
        {
            linear = linear.normalize().times(maxAcceleration);
        }

        return new Acceleration(linear,0, AccelerationType.Dynamic);
    }
}
