package live.pathfinding.worldRepresentation.tag;

import live.pathfinding.GridNode;
import live.pathfinding.base.Graph;
import live.pathfinding.base.Path;
import live.pathfinding.worldRepresentation.WorldRepresentation;
import org.newdawn.slick.geom.Circle;

import java.awt.geom.Point2D;
import java.util.List;

public class TAGWorld implements WorldRepresentation {

    TAG tag;




    public TAGWorld(List<Circle> obstacles , Point2D start, Point2D end) {
        tag = new TAG(obstacles,start,end);
    }

    @Override
    public Graph getGraph() {
        return null;
    }

    @Override
    public Object quantize(Point2D p) {
        return null;
    }

    @Override
    public Point2D localize(Object o) {
        return null;
    }

    @Override
    public void addPath(Path path) {

    }

    @Override
    public GridNode getStart() {
        return null;
    }

    @Override
    public GridNode getEnd() {
        return null;
    }

    @Override
    public void removePaths() {

    }

    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {

    }
}
