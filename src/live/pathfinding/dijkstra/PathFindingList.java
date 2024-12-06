package live.pathfinding.dijkstra;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import java.util.HashMap;

public class PathFindingList<Node> {


    FibonacciHeap<Node> heap;
    HashMap<Node, DijkstraNode<Node>> dijkstraHash;
    HashMap<Node, FibonacciHeapNode<Node>> fibonacciHash;


    long insertCount=0;

    public PathFindingList() {
        heap = new FibonacciHeap<>();
        dijkstraHash = new HashMap<>();
        fibonacciHash = new HashMap<>();
    }

    public void init()
    {
        insertCount=0;
        dijkstraHash.clear();
        fibonacciHash.clear();
        heap.clear();
    }

    public void insert(DijkstraNode<Node> dNode) {

        FibonacciHeapNode<Node> newRecord = new FibonacciHeapNode<>(dNode.getNode());
        heap.insert(newRecord,dNode.getCostSoFar());

        fibonacciHash.put(dNode.getNode(),newRecord);
        dijkstraHash.put(dNode.getNode(),dNode);

        insertCount++;

    }

    public boolean isEmpty() {
        return dijkstraHash.isEmpty();
    }

    public DijkstraNode<Node> removeMin() {

        FibonacciHeapNode<Node> record = heap.removeMin();
        fibonacciHash.remove(record.getData());
        return dijkstraHash.remove(record.getData());
    }

    public boolean contains(Node neighbor) {
        return dijkstraHash.containsKey(neighbor);
    }

    public DijkstraNode get(Node neighbor) {
        return dijkstraHash.get(neighbor);
    }

    public void update(DijkstraNode<Node> newRecord) {

        FibonacciHeapNode<Node> record = fibonacciHash.get(newRecord.getNode());
        heap.decreaseKey(record,newRecord.getCostSoFar());
    }
}
