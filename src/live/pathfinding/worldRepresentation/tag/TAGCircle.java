package live.pathfinding.worldRepresentation.tag;

import live.pathfinding.base.Connection;

import java.awt.geom.Point2D;
import java.util.List;

public class TAGCircle {

    double radius;
    Point2D center;
    List<Point2D> arcPoints;

    List<Connection<Point2D>> getArcs();

}
