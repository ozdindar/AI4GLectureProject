package live.pathfinding;

import live.pathfinding.base.Connection;
import live.pathfinding.base.Path;

import java.util.List;

public class SimplePath<Node> implements Path<Node> {

    List<Connection<Node>> connections;

    public SimplePath(List<Connection<Node>> connections) {
        this.connections = connections;
    }

    @Override
    public List<Connection<Node>> getConnections() {
        return connections;
    }

    @Override
    public double getTotalCost() {
        double total =0;

        for (Connection<Node>connection:connections) {
            total+= connection.getCost();
        }

        return total;
    }

    @Override
    public Node getStart() {
        if (connections.isEmpty())
            return null;

        return connections.get(0).getFrom();
    }

    @Override
    public Node getEnd() {
        if (connections.isEmpty())
            return null;

        return connections.get(connections.size()-1).getTo();
    }

    public String toString()
    {
        String total ="Path: [";

        for (Connection<Node>connection:connections) {
            total+= connection;
        }

        return total + " ]:"+ getTotalCost();
    }

}
