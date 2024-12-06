package live.movement;

import math.geom2d.Vector2D;

import java.awt.geom.Rectangle2D;

public class SimpleBoundaryCollisionHandler implements CollisionHandler {


    @Override
    public void handle(MovingEntity movingEntity) {
        Rectangle2D boundary = movingEntity.getBoundary();

        double radius = movingEntity.body.getRadius();
        Vector2D pos = movingEntity.staticInfo.getPos();

        Vector2D velocity = movingEntity.velocity.linear;



        if ( (  pos.x()+ radius > boundary.getMaxX()  &&  velocity.x()>0) ||
                (  pos.x()- radius < boundary.getMinX()  &&  velocity.x()<0) )
            movingEntity.setVelocity(new Velocity(new Vector2D(-velocity.x(),velocity.y()),movingEntity.getVelocity().rotation));

        velocity =movingEntity.velocity.linear;

        if ( (  pos.y()+ radius > boundary.getMaxY()  &&  velocity.y()>0) ||
                (  pos.y()- radius < boundary.getMinY()  &&  velocity.y()<0) )
            movingEntity.setVelocity(new Velocity(new Vector2D(velocity.x(),-velocity.y()),movingEntity.getVelocity().rotation));

    }
}
