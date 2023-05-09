package ecs.components.collision;

import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;
import starter.Game;

public class OnColilisonDeleteGrabstein implements ICollide{

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        if(b instanceof Hero)
        {
            Game.removeEntity(a);


        }
    }

}
