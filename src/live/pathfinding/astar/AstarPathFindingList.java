package live.pathfinding.astar;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by dindar.oz on 10/2/2017.
 */
public class AstarPathFindingList<Node> {

    FibonacciHeap<Node> heap = new FibonacciHeap<>();
    HashMap<Node, FibonacciHeapNode<Node>> fibonacciHash = new HashMap<>();
    HashMap<Node, AstarNode<Node>> astarHash = new HashMap<>();

    long insertCount=0;


    public void init()
    {
        insertCount=0;
        heap.clear();
        fibonacciHash.clear();
        astarHash.clear();
    }

    void printStats()
    {
        System.out.println("Insertion Count:" + insertCount);
    }

    void insert(AstarNode<Node> record)
    {
        FibonacciHeapNode<Node> fRecord= new FibonacciHeapNode<Node>(record.getNode());
        heap.insert(fRecord,record.getEstimatedTotalCost());
        fibonacciHash.put(record.getNode(),fRecord);
        astarHash.put(record.getNode(),record);
        insertCount++;
    }

    public Set<Node> getNodes()
    {
        return  astarHash.keySet();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }




    public AstarNode<Node> removeSmallest() {
        Node min =  heap.min().getData();
        AstarNode minRecord= astarHash.get(min);
        fibonacciHash.remove(min);
        astarHash.remove(min);
        heap.removeMin();
        return minRecord;
    }
    public boolean contains(Node node) {

        return fibonacciHash.containsKey(node);
    }

    public AstarNode<Node> find(Node node) {
        return astarHash.get(node);
    }

    public void update(AstarNode<Node> record) {
        if (!contains(record.getNode()))
            return;
        heap.decreaseKey(fibonacciHash.get(record.getNode()),record.getEstimatedTotalCost());
        astarHash.put(record.getNode(),record);

    }

    public void remove(Node endNode) {
        if (!fibonacciHash.containsKey(endNode))
            return;

        heap.delete(fibonacciHash.get(endNode));
        fibonacciHash.remove(endNode);
        astarHash.remove(endNode);
    }
}
