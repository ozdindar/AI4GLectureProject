package live.pathfinding;

public class GridGraph extends SimpleGraph<GridNode> {

    int rowCount;
    int colCount;
    double edgeCost;

    GridNode nodes[][];

    public GridGraph(int rowCount, int colCount, double edgeCost) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.edgeCost = edgeCost;

        createNodes();
        createConnections();
    }

    private void createConnections() {
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                createConnections(r,c);
            }
        }
    }

    public void createConnections(int r, int c) {
        if (r>0)
            addConnection(nodes[r][c],nodes[r-1][c],edgeCost);

        if (r< rowCount-1)
            addConnection(nodes[r][c],nodes[r+1][c],edgeCost);

        if (c>0)
            addConnection(nodes[r][c],nodes[r][c-1],edgeCost);

        if (c< colCount-1)
            addConnection(nodes[r][c],nodes[r][c+1],edgeCost);
    }


    public void removeConnections(int r, int c)
    {
        if (r>0)
            removeConnections(nodes[r][c],nodes[r-1][c]);

        if (r< rowCount-1)
            removeConnections(nodes[r][c],nodes[r+1][c]);

        if (c>0)
            removeConnections(nodes[r][c],nodes[r][c-1]);

        if (c< colCount-1)
            removeConnections(nodes[r][c],nodes[r][c+1]);
    }


    private void createNodes() {
        nodes= new GridNode[rowCount][colCount];

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                nodes[r][c] = new GridNode(r,c);
            }
        }
    }

    public GridNode getNode(int row, int col) {
        return nodes[row][col];
    }
}
