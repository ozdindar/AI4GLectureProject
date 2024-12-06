package live.movement.steering;

import live.movement.Acceleration;
import live.movement.MovingEntity;
import live.movement.StaticInfo;
import live.movement.Velocity;
import math.geom2d.Vector2D;

public class Pursue implements SteeringBehavior {

    MovingEntity targetEntity;

    Arrive arrive;


    public Pursue(MovingEntity targetEntity, double maxAcceleration) {
        this.targetEntity = targetEntity;
        arrive = new Arrive(targetEntity.getStaticInfo().getPos(),maxAcceleration);
    }

    @Override
    public Acceleration getSteering(StaticInfo staticInfo, Velocity velocity) {

        Vector2D target = targetEntity.getStaticInfo().getPos().plus(targetEntity.getVelocity().getLinear().times(100));

        arrive.setTarget(target);
        return arrive.getSteering(staticInfo,velocity);
    }
}
