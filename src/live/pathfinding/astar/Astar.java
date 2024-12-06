package live.pathfinding.astar;



import live.pathfinding.SimplePath;
import live.pathfinding.base.Connection;
import live.pathfinding.base.Graph;
import live.pathfinding.base.Path;
import live.pathfinding.base.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dindar.oz on 10/2/2017.
 */
public class Astar<Node> implements PathFinder<Node> {

    AstarHeuristic<Node> heuristic;

    AstarPathFindingList<Node> openList = new AstarPathFindingList<>();
    AstarPathFindingList<Node> closedList = new AstarPathFindingList<>();


    public List<Node> getOpenNodes()
    {
        return new ArrayList<>(openList.getNodes());
    }
    public List<Node> getCloseNodes()
    {
        return new ArrayList<>(closedList.getNodes());
    }

    public Astar(AstarHeuristic<Node> heuristic) {
        this.heuristic = heuristic;
    }

    private void init(Node start)
    {
        AstarNode<Node> startRecord = new AstarNode<>(start,null,0,heuristic.estimate(start));

        openList.init();
        openList.insert(startRecord);

        closedList.init();
    }

    @Override
    public Path<Node> findPath(Graph<Node> graph, Node start, Node end) {

        init(start);

        AstarNode<Node> current= null;
        while (!openList.isEmpty())
        {
            current = openList.removeSmallest();
            if (current.getNode().equals(end) )
                break;

            List<Connection<Node>> outgoings= graph.getConnections(current.getNode());

            for (Connection<Node> connection:outgoings)
            {
                Node endNode = connection.getTo();
                double endNodeCost=current.getCostSoFar()+connection.getCost();
                double endNodeHeuristic;
                if (closedList.contains(endNode))
                {
                    AstarNode<Node> endRecord = closedList.find(endNode);
                    // If it is not a better path just ignore
                    if (endRecord.getCostSoFar()<=endNodeCost)
                        continue;

                    closedList.remove(endNode);
                    endNodeHeuristic =  endRecord.getEstimatedTotalCost()- endRecord.getCostSoFar();

                }
                else if (openList.contains(endNode))
                {
                    AstarNode<Node> endRecord = openList.find(endNode);
                    if (endRecord.getCostSoFar()<=endNodeCost)
                        continue;

                    endNodeHeuristic =  endRecord.getEstimatedTotalCost()- endRecord.getCostSoFar();
                }
                else
                {
                    endNodeHeuristic = heuristic.estimate(endNode);
                }


                AstarNode<Node> endNodeRecord= new AstarNode<Node>(endNode,connection,endNodeCost,endNodeCost+endNodeHeuristic);

                if (!openList.contains(endNode))
                    openList.insert(endNodeRecord);
                else openList.update(endNodeRecord);

            }

            closedList.insert(current);
        }

        if (current==null || !current.getNode().equals(end))
            return null;

        openList.printStats();


        System.out.println("A* Inserted: "+openList.insertCount);
        return buildPath(current,start,closedList);
    }

    @Override
    public long visitedNodeCount() {
        return closedList.insertCount;
    }

    @Override
    public long discoveredNodeCount() {
        return openList.insertCount;
    }

    private Path<Node> buildPath(AstarNode<Node> current, Node start, AstarPathFindingList<Node> closed) {
        List<Connection<Node> > path = new ArrayList<>();

        while (!current.node.equals(start))
        {
            Connection<Node> connection = current.getConnection();

            path.add(connection);
            current = closed.find(connection.getFrom());

        }

        Collections.reverse(path);
        return new SimplePath<>(path);
    }


}
