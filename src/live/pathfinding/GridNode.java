package live.pathfinding;

import java.util.Objects;

public class GridNode {
    int row;
    int col;

    public GridNode(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridNode node = (GridNode) o;
        return getRow() == node.getRow() &&
                getCol() == node.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }

    public String toString()
    {
        return "[" + row + "," + col+ "]";
    }
}
