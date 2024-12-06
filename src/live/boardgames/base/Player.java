package live.boardgames.base;


import org.newdawn.slick.Image;

public interface Player {
    Image getLogo();
    String getName();
    PlayerType getType();
    void init(Board board);

    boolean hasMove();

    Move getMove();


}
