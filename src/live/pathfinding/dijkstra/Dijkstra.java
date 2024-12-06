package live.pathfinding.dijkstra;

import live.pathfinding.GridGraph;
import live.pathfinding.GridNode;
import live.pathfinding.SimpleGraph;
import live.pathfinding.SimplePath;

import live.pathfinding.base.Connection;
import live.pathfinding.base.Graph;
import live.pathfinding.base.Path;
import live.pathfinding.base.PathFinder;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra<Node> implements PathFinder<Node> {

    PathFindingList<Node> openList = new PathFindingList();
    PathFindingList<Node> closedList = new PathFindingList();


    @Override
    public Path<Node> findPath(Graph<Node> graph, Node start, Node end) {

        if ( start.equals(end))
            return new SimplePath<Node>(new ArrayList<>());


        init(start);


        while (!openList.isEmpty())
        {
            DijkstraNode<Node> currentDNode = openList.removeMin();
            Node currentNode = currentDNode.getNode();
            if (currentNode.equals(end))
            {
                System.out.println("Dijkstra Inserted: "+openList.insertCount);
                return buildPath(currentDNode,start,closedList);
            }

            List<Connection<Node>> neighbors = graph.getConnections(currentNode);

            for (Connection<Node> connection:neighbors)
            {
                Node neighbor = connection.getTo();

                if (closedList.contains(neighbor))
                    continue;

                DijkstraNode<Node> newRecord = new DijkstraNode<>(neighbor,connection,currentDNode.getCostSoFar()+connection.getCost());

                if ( openList.contains(neighbor))
                {
                    if ( openList.get(neighbor).getCostSoFar()> newRecord.getCostSoFar() )
                        openList.update(newRecord);
                }
                else openList.insert(newRecord);


            }

            closedList.insert(currentDNode);

        }

        return null;
    }

    @Override
    public long visitedNodeCount() {

        return closedList.insertCount;
    }

    @Override
    public long discoveredNodeCount() {
        return openList.insertCount;
    }

    private void init(Node start) {

        openList.init();
        closedList.init();
        DijkstraNode<Node> dNode = new DijkstraNode<>(start,null,0);

        openList.insert(dNode);
    }

    private Path<Node> buildPath(DijkstraNode<Node> currentDNode, Node start, PathFindingList<Node> closedList) {

        List<Connection<Node>> path = new ArrayList<>();

        while (!currentDNode.getNode().equals(start))
        {
            path.add(currentDNode.getConnection());

            currentDNode = closedList.get(currentDNode.getConnection().getFrom());
        }

        Collections.reverse(path);




        return new SimplePath<Node>(path);
    }

    public static void main(String[] args) {
        SimpleGraph<Integer> graph = new SimpleGraph<>();

        graph.addConnection(1,2,2);
        graph.addConnection(1,3,4);
        graph.addConnection(2,3,1);
        graph.addConnection(3,4,5);
        graph.addConnection(2,4,1);


        Graph g = new GridGraph(20,20,2);

        GridNode s = new GridNode(0,0);
        GridNode t = new GridNode(15,12);

        PathFinder<GridNode> dijkstra = new Dijkstra<>();

        Path<GridNode> path = dijkstra.findPath(g,s,t);



        System.out.println(path);
    }


}
