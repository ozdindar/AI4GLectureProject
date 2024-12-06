package live.pathfinding.astar;

/**
 * Created by dindar.oz on 10/3/2017.
 */
public interface AstarHeuristic<Node> {
    double estimate(Node node);
}
