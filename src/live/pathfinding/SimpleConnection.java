package live.pathfinding;

import live.pathfinding.base.Connection;

public class SimpleConnection<Node> implements Connection<Node> {

    Node from;
    Node to;
    double cost;

    public SimpleConnection(Node from, Node to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    @Override
    public Node getFrom() {
        return from;
    }

    @Override
    public Node getTo() {
        return to;
    }

    @Override
    public double getCost() {
        return cost;
    }

    public String toString()
    {
        return "("+ from + "-" + to + ")["+cost+"]";
    }
}
