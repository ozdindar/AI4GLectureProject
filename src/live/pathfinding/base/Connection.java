package live.pathfinding.base;

public interface Connection<Node> {

    Node getFrom();
    Node getTo();

    double getCost();


}
