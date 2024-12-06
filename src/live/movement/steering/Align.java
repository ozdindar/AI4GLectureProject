package live.movement.steering;

import live.movement.Acceleration;
import live.movement.AccelerationType;
import live.movement.StaticInfo;
import live.movement.Velocity;
import math.geom2d.Vector2D;

public class Align implements SteeringBehavior{

    double targetAngle;

    double maxAcceleration = 0.2;
    double maxSpeed = 0.1;

    double targetRadius = 0.01;
    double slowDownRadius = 1;


    double timeToTarget = 0.1;


    public Align(double target, double maxAcceleration) {
        this.targetAngle = target;
        this.maxAcceleration = maxAcceleration;
    }

    @Override
    public Acceleration getSteering(StaticInfo staticInfo, Velocity velocity) {

        double direction = targetAngle-staticInfo.getOrientation();

        direction = mapToRange(direction);

        double targetSpeed = direction/Math.abs(direction)* maxSpeed;

        if ( Math.abs(direction)<targetRadius)
            targetSpeed = 0;
        else if (Math.abs(direction)<slowDownRadius)
        {
            targetSpeed *= Math.abs(direction)/slowDownRadius;
        }

        double targetVelocity = targetSpeed ;

        double angular = targetVelocity - velocity.getRotation();

        if ( Math.abs(angular)>maxAcceleration)
            angular *= maxAcceleration/Math.abs(angular);




        return new Acceleration(new Vector2D(0,0),angular, AccelerationType.Dynamic);
    }

    private double mapToRange(double direction) {

        while(direction>Math.PI)
        {
            direction -= Math.PI*2;
        }

        while(direction<-Math.PI)
        {
            direction += Math.PI*2;
        }

        return direction;
    }

    public void setTarget(double angle) {
        targetAngle = angle;
    }
}
