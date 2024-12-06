package live.pathfinding;

import live.pathfinding.base.Connection;
import live.pathfinding.base.Graph;
import live.pathfinding.base.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class SimpleGraph<Node> implements Graph<Node> {

    HashMap<Node, List<Connection<Node>>> connectionHash;

    public SimpleGraph() {
        connectionHash = new HashMap<>();
    }

    @Override
    public List<Connection<Node>> getConnections(Node from) {
        if (!connectionHash.containsKey(from))
            return null;

        return connectionHash.get(from);
    }

    public void addNode(Node node)
    {
        if (!connectionHash.containsKey(node))
        {
            connectionHash.put(node, new ArrayList<>());
        }
    }

    public void addConnection(Node from, Node to, double cost)
    {
        addNode(from);
        addNode(to);

        List<Connection<Node>> connections = connectionHash.get(from);

        connections.add(new SimpleConnection(from,to,cost));
    }

    public void removeConnections(Node from)
    {
        if (connectionHash.containsKey(from))
        {
            connectionHash.get(from).clear();
        }
    }

    public void removeConnections(Node from , Node to)
    {
        if (connectionHash.containsKey(from))
        {
            List<Connection<Node>> connections = connectionHash.get(from);
            connections.removeIf(new Predicate<Connection<Node>>() {
                @Override
                public boolean test(Connection<Node> connection) {
                    return connection.getTo().equals(to);
                }
            });


        }

    }

    public static void main(String[] args) {
        SimpleGraph<Integer> graph = new SimpleGraph<>();

        graph.addConnection(1,2,2);
        graph.addConnection(1,3,4);
        graph.addConnection(2,3,1);
        graph.addConnection(3,4,5);

        List<Connection<Integer>> connections = new ArrayList<>();


        connections.add(new SimpleConnection<>(1,2,2));
        connections.add(new SimpleConnection<>(2,3,1));
        connections.add(new SimpleConnection<>(3,4,5));


        Path<Integer> path = new SimplePath<>(connections);

        System.out.println(path);

    }

}
