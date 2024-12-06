package live.pathfinding.astar;

/**
 * Created by dindar.oz on 10/3/2017.
 */
public class NullHeuristic<Node> implements AstarHeuristic< Node> {
    @Override
    public double estimate(Node o) {
        return 20;
    }
}
