package live.pathfinding.astar;


import live.pathfinding.GridNode;

/**
 * Created by dindar.oz on 10/3/2017.
 */
public class EucledianDistanceHeuristic implements AstarHeuristic<GridNode> {

    private final double edgeCost;
    GridNode target;

    public EucledianDistanceHeuristic(GridNode target, double edgeCost) {
        this.target = target;
        this.edgeCost = edgeCost;
    }

    @Override
    public double estimate(GridNode gridNode) {

        double dx = edgeCost*Math.abs(target.getCol()-gridNode.getCol());
        double dy = edgeCost*Math.abs(target.getRow()-gridNode.getRow());

        return Math.sqrt(dx*dx + dy*dy);
    }
}
