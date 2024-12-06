package live.pathfinding.astar;


import live.pathfinding.GridNode;

/**
 * Created by dindar.oz on 10/3/2017.
 */
public class ManhattanDistanceHeuristic implements AstarHeuristic<GridNode> {

    private final double edgeCost;
    GridNode target;

    public ManhattanDistanceHeuristic(GridNode target,double edgeCost) {
        this.target = target;
        this.edgeCost = edgeCost;
    }

    @Override
    public double estimate(GridNode gridNode) {
        return edgeCost*(Math.abs(target.getCol()-gridNode.getCol())+Math.abs(target.getRow()-gridNode.getRow()));
    }
}
