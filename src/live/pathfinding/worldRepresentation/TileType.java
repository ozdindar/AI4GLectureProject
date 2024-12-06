package live.pathfinding.worldRepresentation;

import org.newdawn.slick.Color;

public enum  TileType {

    Empty, Obstacle, Start, End;

    public Color toColor() {

        switch (this)
        {
            case Empty : return Color.white;
            case Obstacle: return Color.black;
            case Start: return Color.blue;
            case End: return Color.orange;
        }

        return Color.white;
    }
}
