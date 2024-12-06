package live.pathfinding.worldRepresentation.tag;

import live.pathfinding.base.Connection;

import java.awt.geom.Point2D;

public class ArcConnection implements Connection<Point2D> {

    Point2D from;
    Point2D to;
    Point2D center;

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
