package live.movement.steering;

import live.movement.Acceleration;
import live.movement.StaticInfo;
import live.movement.Velocity;
import math.geom2d.Vector2D;

public class Face implements SteeringBehavior {

    Vector2D target;

    Align align ;




    public Face(Vector2D target, double maxAcceleration) {
        this.target = target;

        align = new Align(0,maxAcceleration);
    }

    @Override
    public Acceleration getSteering(StaticInfo staticInfo, Velocity velocity) {

        Vector2D direction = target.minus(staticInfo.getPos());

        double angle = Math.atan2(direction.y(),direction.x());

        align.setTarget(angle);


        return align.getSteering(staticInfo,velocity);
    }
}
