package live.pathfinding.worldRepresentation.tag;

import live.pathfinding.base.Connection;
import live.pathfinding.base.Graph;
import org.newdawn.slick.geom.Circle;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TAG implements Graph<Point2D> {

    HashMap<Point2D,List<Connection<Point2D>>> connectionHash;

    List<TAGCircle> obstacles;

    public TAG(List<Circle> obstacles , Point2D start, Point2D end) {

        buildObstacles();
        buildLineConnections(obstacles,start,end);
        buildArcConnections(obstacles,start,end);
    }

    private void buildConnections(List<Circle> obstacles, Point2D start, Point2D end) {
        HashMap<Point2D,List<Connection<Point2D>>> connectionHash = new HashMap<>();
        connectionHash.put(start, new ArrayList<>());
        for (Circle c:obstacles) {
            // start ile circle arasındaki connectionu hesapla
            // Intersection yoksa connectionHash e ekle
        }
    }

    @Override
    public List<Connection<Point2D>> getConnections(Point2D from) {
        return connectionHash.get(from);
    }
}
