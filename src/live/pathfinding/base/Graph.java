package live.pathfinding.base;

import java.util.List;

public interface Graph<Node> {

    List<Connection<Node>> getConnections(Node from);
}
