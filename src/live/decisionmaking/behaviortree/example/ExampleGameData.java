package live.decisionmaking.behaviortree.example;

import java.awt.geom.Point2D;

public class ExampleGameData {

    Point2D pos;
    Point2D enemyPos;
    String message;

    public ExampleGameData(Point2D pos, Point2D enemyPos, String message) {
        this.pos = pos;
        this.enemyPos = enemyPos;
        this.message = message;
    }

    public ExampleGameData(Point2D pos, Point2D enemyPos) {
        this.pos = pos;
        this.enemyPos = enemyPos;
    }
}
