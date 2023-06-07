package ecs.items;

import ecs.entities.Entity;
import java.io.Serializable;
import tools.Point;

public interface IOnDrop extends Serializable {
    void onDrop(Entity user, ItemData which, Point position);
}
