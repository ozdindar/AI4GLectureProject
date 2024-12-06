package live.boardgames.base;

public abstract class AbstractBoard implements Board {

    protected int moveCount;
    protected int currentPlayer;

    public AbstractBoard() {
        moveCount=0;
        currentPlayer=0;
    }

    public int getMoveCount() {

        return moveCount;
    }

    protected AbstractBoard(AbstractBoard other)
    {
        moveCount = other.moveCount;
        currentPlayer = other.currentPlayer;
    }

    @Override
    public void init() {
        moveCount =0;
        currentPlayer = 0;
    }

    @Override
    public int currentPlayer() {
        return currentPlayer;
    }

    public Board makeMove(Move m)
    {
        Board cloned = cloneBoard();
        if (isValid(m))
        {

            cloned.perform(m);
            return cloned;
        }
        return cloneBoard();
    }

    @Override
    public void perform(Move m) {
        moveCount++;
        _updateBoard(m);
        currentPlayer = (currentPlayer+1)% playerCount();
    }

    protected abstract void _updateBoard(Move m);
}