package live.pathfinding.worldRepresentation;

import live.base.GameEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Tile implements GameEntity {

    TileType tileType;
    Point2D topLeft;
    int row;
    int col;
    float tileSize;
    private boolean selected;

    public Tile(TileType tileType, Point2D topLeft, int row, int col, float tileSize) {
        this.tileType = tileType;
        this.topLeft = topLeft;
        this.row = row;
        this.col = col;
        this.tileSize = tileSize;
    }

    public void setType(TileType type) {
        this.tileType = type;
    }

    public TileType getType() {
        return tileType;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame, Rectangle2D boundary) {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        Color bgColor = tileType.toColor();

        graphics.setColor(bgColor);
        graphics.fillRect((float) topLeft.getX(), (float) topLeft.getY(), tileSize, tileSize);

        graphics.setColor(Color.black);

        graphics.drawRect((float) topLeft.getX(), (float) topLeft.getY(), tileSize, tileSize);

        if (selected) {
            graphics.setColor(Color.red);
            graphics.fillOval((float) (topLeft.getX() + tileSize / 4), (float) (topLeft.getY() + tileSize / 4), tileSize / 2, tileSize / 2);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, float time) {

    }

    public Point2D center() {
        return new Point2D.Float((float)(topLeft.getX()+tileSize/2), (float)(topLeft.getY()+ tileSize/2));
    }

    public void setSelected(boolean b) {
        selected = b;
    }
}
