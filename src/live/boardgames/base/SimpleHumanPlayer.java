package live.boardgames.base;


import org.newdawn.slick.Image;

public class SimpleHumanPlayer extends AbstractPlayer implements HumanPlayer{


    public SimpleHumanPlayer(String name) {
        super(name, PlayerType.HumanPlayer);
    }

    @Override
    public void setMove(Move move) {
        this.move = move;
    }

    @Override
    public Image getLogo() {
        return null;
    }

    @Override
    public void init(Board board) {

    }
}
