package ecs.entities.Items.DropSystem;

import ecs.entities.Entity;
import ecs.entities.Items.ITreasures;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
/** This class is the Strategy for implementing ChestLootDrops */
public class ChestLoot implements ITreasures {

    /** Calls the defaultMethod dropItems from ITreasures with the corresponding ItemData */
    public ChestLoot(Entity entity) {
        dropItems(entity, getLoot());
    }

    @Override
    public ItemData getLoot() {
        ItemDataGenerator gen = new ItemDataGenerator();
        return gen.generateItemData(2);
    }
}
