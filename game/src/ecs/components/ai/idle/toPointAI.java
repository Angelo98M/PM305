package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import dslToGame.graph.Graph;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

public class toPointAI implements IIdleAI{

    private Point endPoint;
    private Point startPoint;
    private GraphPath<Tile> path;

    public toPointAI(Point startPoint,Point endPoint)
    {
        this.endPoint=endPoint;
        this.startPoint=startPoint;

    }

    @Override
    public void idle(Entity entity) {
        if(path==null) {
            path=AITools.calculatePath(startPoint,endPoint);
        }
        if(AITools.pathFinished(entity,path))
        {
            Game.removeEntity(entity);
        }

        AITools.move(entity,path);

    }
}
