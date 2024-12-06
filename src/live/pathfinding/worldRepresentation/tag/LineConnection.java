package live.pathfinding.worldRepresentation.tag;

import live.pathfinding.base.Connection;

import java.awt.geom.Point2D;

public class LineConnection implements Connection<Point2D> {
    @Override
    public Point2D getFrom() {
        return null;
    }

    @Override
    public Point2D getTo() {
        return null;
    }

    @Override
    public double getCost() {
        return 0;
    }
}
