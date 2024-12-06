package live.pathfinding.base;

import java.util.List;

public interface Path<Node> {
    List<Connection<Node>> getConnections();

    double getTotalCost();

    Node getStart();
    Node getEnd();


}
