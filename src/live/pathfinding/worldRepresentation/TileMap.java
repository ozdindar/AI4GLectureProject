package live.pathfinding.worldRepresentation;

import live.base.GameEntity;
import live.base.SimplePanel;
import live.pathfinding.GridGraph;
import live.pathfinding.GridNode;
import live.pathfinding.base.Connection;
import live.pathfinding.base.Graph;
import live.pathfinding.base.Path;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class TileMap implements WorldRepresentation<GridNode>, GameEntity {

    Point2D topLeft;
    Tile tiles[][];
    GridGraph graph;
    float tileSize;
    private List<Path> paths;
    private static final  float DotRadius= 5;
    private GridNode start;
    private GridNode end;




    GridNode selected;
    private Color[] PathColors = new Color[]{Color.black,Color.green,Color.yellow};

    public TileMap(float tileSize, int rowCount, int colCount) {
        this.topLeft = new Point2D.Float(0,0);
        this.tileSize = tileSize;

        paths = new ArrayList<>();

        createTiles(rowCount,colCount);
        createGraph(rowCount,colCount);
    }


    public TileMap(Point2D topLeft, float tileSize, int rowCount, int colCount) {
        this.topLeft = topLeft;
        this.tileSize = tileSize;

        createTiles(rowCount,colCount);
        createGraph(rowCount,colCount);
    }

    private void createGraph(int rowCount, int colCount) {
        graph = new GridGraph(rowCount,colCount,tileSize);
    }

    private void createTiles(int rowCount, int colCount) {
        tiles = new Tile[rowCount][colCount];

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                float dx = (float) (topLeft.getX()+ tileSize*c);
                float dy = (float) (topLeft.getY()+ tileSize*r);

                tiles[r][c] = new Tile(TileType.Empty, new Point2D.Float(dx,dy),r,c,tileSize);
            }
        }
    }

    @Override
    public Graph<GridNode> getGraph() {
        return graph;
    }

    @Override
    public GridNode quantize(Point2D p) {

        int row = (int) ((p.getY()-topLeft.getY()- SimplePanel.FRAME_WIDTH)/tileSize);
        int col = (int) ((p.getX()-topLeft.getX()- SimplePanel.FRAME_WIDTH)/tileSize);


        return graph.getNode(row,col);
    }

    @Override
    public Point2D localize(GridNode gridNode) {
        return tiles[gridNode.getRow()][gridNode.getCol()].center();
    }

    @Override
    public void addPath(Path path) {
        paths.add(path);
    }

    @Override
    public GridNode getStart() {
        return start;
    }

    @Override
    public GridNode getEnd() {
        return end;
    }

    @Override
    public void removePaths() {
        paths.clear();
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_S)
        {
            if (selected != null)
            {
                setStart(selected);
            }
        }
        if (key == Input.KEY_E)
        {
            if (selected != null)
            {
                setEnd(selected);
            }
        }

        if (key == Input.KEY_O)
        {
            if (selected != null)
            {
                if (tiles[selected.getRow()][selected.getCol()].getType()== TileType.Empty) {
                    putObstacle(selected.getRow(),selected.getCol());
                }
                else if (tiles[selected.getRow()][selected.getCol()].getType()== TileType.Obstacle) {
                    removeObstacle(selected.getRow(),selected.getCol());
                }
            }
        }


    }

    private void setStart(GridNode node) {
        Tile selectedTile = getTile(node);
        if (selectedTile.getType() == TileType.Obstacle || selectedTile.getType()== TileType.End )
            return;

        if (start != null)
            tiles[start.getRow()][start.getCol()].setType(TileType.Empty);

        start = node;
        selectedTile.setType(TileType.Start);
    }
    private void setEnd(GridNode node) {
        Tile selectedTile = getTile(node);
        if (selectedTile.getType() == TileType.Obstacle || selectedTile.getType()== TileType.Start )
            return;

        if (end != null)
            tiles[end.getRow()][end.getCol()].setType(TileType.Empty);

        end = node;
        selectedTile.setType(TileType.End);

    }

    private Tile getTile(GridNode node) {
        return tiles[node.getRow()][node.getCol()];
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (button == Input.MOUSE_LEFT_BUTTON)
        {
            GridNode node = quantize(new Point2D.Float(x,y));

            select(node);
        }
    }

    private void select(GridNode node) {
        if (selected!= null)
            tiles[selected.getRow()][selected.getCol()].setSelected(false);
        selected = node;
        tiles[node.getRow()][node.getCol()].setSelected(true);
    }

    public void putObstacle(int row, int col)
    {
      // Check here there is no start and end

        tiles[row][col].setType(TileType.Obstacle);
        graph.removeConnections(row,col);
    }

    public void removeObstacle(int row, int col)
    {
        if (tiles[row][col].getType()== TileType.Obstacle) {
            tiles[row][col].setType(TileType.Empty);
            graph.createConnections(row, col);
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame, Rectangle2D boundary) {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                tiles[r][c].init(gameContainer,stateBasedGame,boundary);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                tiles[r][c].render(gameContainer,stateBasedGame,graphics);
            }
        }




        renderPaths(graphics);
    }


    private void renderPaths(Graphics graphics) {
        for (int p = 0; p < paths.size(); p++) {
            graphics.setColor(PathColors[p%PathColors.length]);
            renderPath(graphics,paths.get(p));
        }
    }

    private void renderPath(Graphics graphics, Path path) {

        List<Connection> connections = path.getConnections();
        for(Connection<GridNode> connection:connections)
        {
            renderConnection(graphics,connection);
        }
    }

    private void renderConnection(Graphics graphics, Connection<GridNode> connection) {
        Point2D start = localize( connection.getFrom());
        Point2D end = localize( connection.getTo());

        //graphics.setColor(Color.black);

        float sx = (float) (start.getX()-DotRadius);
        float sy = (float) (start.getY()-DotRadius);

        float ex = (float) (end.getX()-DotRadius);
        float ey = (float) (end.getY()-DotRadius);

        graphics.fillOval(sx,sy,2*DotRadius, 2*DotRadius);
        graphics.fillOval(ex,ey,2*DotRadius, 2*DotRadius);

        graphics.drawLine((float) start.getX(),(float)start.getY(),(float)end.getX(),(float)end.getY());

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, float time) {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                tiles[r][c].update(gameContainer,stateBasedGame,time);
            }
        }
    }
}
