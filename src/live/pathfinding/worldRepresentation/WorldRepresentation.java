package live.pathfinding.worldRepresentation;

import live.pathfinding.GridNode;
import live.pathfinding.base.Graph;
import live.pathfinding.base.Path;

import java.awt.geom.Point2D;

public interface WorldRepresentation<Node> {
    Graph<Node> getGraph();
    Node quantize(Point2D p);
    Point2D localize(Node node);

    void addPath(Path path);

    GridNode getStart();
    GridNode getEnd();

    void removePaths();

    void keyPressed(int key, char c);

    void mouseClicked(int button, int x, int y, int clickCount);
}
