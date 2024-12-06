package live.pathfinding.base;

public interface PathFinder<Node> {

    Path<Node> findPath(Graph<Node> graph, Node start, Node end);


    long visitedNodeCount(); // Returns the number of nodes that are visited
    long discoveredNodeCount(); // Returns the number of nodes that are discovered
}
