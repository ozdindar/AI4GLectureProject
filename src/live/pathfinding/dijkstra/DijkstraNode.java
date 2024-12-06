package live.pathfinding.dijkstra;

import live.pathfinding.base.Connection;

public class DijkstraNode<Node> {
    Node node;
    Connection<Node> connection;
    double costSoFar;

    public DijkstraNode(Node node, Connection<Node> connection, double costSoFar) {
        this.node = node;
        this.connection = connection;
        this.costSoFar = costSoFar;
    }

    public Node getNode() {
        return node;
    }

    public Connection<Node> getConnection() {
        return connection;
    }

    public double getCostSoFar() {
        return costSoFar;
    }
}
