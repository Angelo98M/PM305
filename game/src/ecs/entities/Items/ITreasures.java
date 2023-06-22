package ecs.entities.Items;

import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.items.ItemData;
import java.util.logging.Logger;
import tools.Point;

/** This Interface is used to implement Loot dropping via a Strategy Pattern */
public interface ITreasures {
    Logger dropLogger = Logger.getLogger("Drop");

    default void dropItems(Entity entity, ItemData loot) {
        PositionComponent positionComponent =
                entity.getComponent(PositionComponent.class)
                        .map(PositionComponent.class::cast)
                        .orElseThrow(
                                () ->
                                        createMissingComponentException(
                                                PositionComponent.class.getName(), entity));
        loot.triggerDrop(entity, calculateDropPosition(positionComponent, 2));
        dropLogger.info(loot.getClass().getSimpleName() + " was dropped");
    }

    /**
     * small Helper to determine the Position of the dropped item simple circle drop
     *
     * @param positionComponent The PositionComponent of the Chest
     * @param radian of the current Item
     * @return a Point in a unit Vector around the Chest
     */
    default Point calculateDropPosition(PositionComponent positionComponent, double radian) {
        return new Point(
                (float) Math.cos(radian * Math.PI) + positionComponent.getPosition().x,
                (float) Math.sin(radian * Math.PI) + positionComponent.getPosition().y);
    }

    /**
     * Helper to create a MissingComponentException with a bit more information
     *
     * @param Component the name of the Component which is missing
     * @param e the Entity which did miss the Component
     * @return the newly created Exception
     */
    private static MissingComponentException createMissingComponentException(
            String Component, Entity e) {
        return new MissingComponentException(
                Component
                        + " missing in "
                        + Chest.class.getName()
                        + " in Entity "
                        + e.getClass().getName());
    }
    /** Function to get the wanted lootDrop for the Entity corresponding to the needed Pattern */
    ItemData getLoot();
}
