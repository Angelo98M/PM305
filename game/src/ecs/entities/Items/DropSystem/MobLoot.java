package ecs.entities.Items.DropSystem;

import ecs.entities.Entity;
import ecs.entities.Items.ITreasures;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
/** This class is the Strategy for implementing MonsterLootDrops */
public class MobLoot implements ITreasures {

    /** Calls the defaultMethod dropItems from ITreasures with the corresponding ItemData */
    public MobLoot(Entity entity) {
        dropItems(entity, getLoot());
    }

    @Override
    public ItemData getLoot() {
        ItemDataGenerator gen = new ItemDataGenerator();
        return gen.generateItemData(1);
    }
}
