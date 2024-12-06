package live.pathfinding.astar;



import live.pathfinding.base.Connection;

/**
 * Created by dindar.oz on 10/2/2017.
 */
public class AstarNode<Node> {
    Node node;
    Connection<Node> connection;
    double costSoFar;
    double estimatedTotalCost;

    public AstarNode(Node node, Connection<Node> connection, double costSoFar, double estimatedTotalCost) {
        this.node = node;
        this.connection = connection;
        this.costSoFar = costSoFar;
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public double getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public Node getNode() {
        return node;
    }

    public double getCostSoFar() {
        return costSoFar;
    }



    public Connection<Node> getConnection() {
        return connection;
    }
}
