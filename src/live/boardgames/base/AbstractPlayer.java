package live.boardgames.base;

public abstract class AbstractPlayer implements Player {
    String name;
    PlayerType type;

    protected Move move;

    public AbstractPlayer(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerType getType() {
        return type;
    }

    @Override
    public Move getMove() {
        Move m = move;
        move = null;
        return m;
    }

    public boolean hasMove()
    {
        return move != null;
    }
}
