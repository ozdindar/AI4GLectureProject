package live.pathfinding.gui;

import live.base.GameEntity;
import live.base.SimpleGame;
import live.base.SimplePanel;
import live.pathfinding.GridNode;
import live.pathfinding.astar.Astar;
import live.pathfinding.astar.EucledianDistanceHeuristic;
import live.pathfinding.astar.ManhattanDistanceHeuristic;
import live.pathfinding.astar.NullHeuristic;
import live.pathfinding.base.Path;
import live.pathfinding.base.PathFinder;
import live.pathfinding.dijkstra.Dijkstra;
import live.pathfinding.worldRepresentation.TileMap;
import live.pathfinding.worldRepresentation.WorldRepresentation;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Bootstrap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PathFindingPanel extends SimplePanel {

    WorldRepresentation worldRepresentation;
    List<PathFinder> pathFinders;




    public PathFindingPanel(WorldRepresentation worldRepresentation, List<PathFinder> pathFinders) {
        this.worldRepresentation = worldRepresentation;
        this.pathFinders = pathFinders;
        addEntity((GameEntity) worldRepresentation);
    }


    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);


        if (key == Input.KEY_SPACE)
        {
            findPath();
        }
        if (key == Input.KEY_X)
        {
            worldRepresentation.removePaths();
        }

        worldRepresentation.keyPressed(key,c);


    }

    private void findPath() {
        Object s = worldRepresentation.getStart();
        Object e = worldRepresentation.getEnd();
        if (s==null || e==null)
            return;

        for ( PathFinder pathFinder: pathFinders) {
            Path path = pathFinder.findPath(worldRepresentation.getGraph(), s, e);
            if (path!= null)
                worldRepresentation.addPath(path);
        }


    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);

        worldRepresentation.mouseClicked(button,x,y,clickCount);
    }

    public static void main(String[] args) {

        TileMap tileMap = new TileMap(20,27,37);
        tileMap.putObstacle(5,5);
        tileMap.putObstacle(25,13);
        PathFinder<GridNode> dijkstra =  new Dijkstra<>();
        PathFinder<GridNode> astar1 =  new Astar<>(new ManhattanDistanceHeuristic(new GridNode(26,36),20));
        PathFinder<GridNode> astar2 =  new Astar<>(new EucledianDistanceHeuristic(new GridNode(26,36),20));
        PathFindingPanel pathFindingPanel = new PathFindingPanel(tileMap, Arrays.asList(dijkstra,astar1,astar2));

        SimpleGame demo = new SimpleGame("Path Finding Demo", pathFindingPanel);


        Bootstrap.runAsApplication(demo,800,600,false);

    }
}
