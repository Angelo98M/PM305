package ecs.items;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.components.stats.DamageModifier;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

import static ecs.items.ItemType.Passive;

public abstract class Swords extends ItemData{

    static IOnCollect collect = new IOnCollect() {
        @Override
        public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
            Game.getHero()
                .ifPresent(
                    hero -> {
                        if (whoCollides.equals(hero)) {
                            hero.getComponent(InventoryComponent.class)
                                .ifPresent(
                                    (x) -> {
                                        if (((InventoryComponent) x)
                                            .addItem(
                                                WorldItemEntity
                                                    .getComponent(
                                                        ItemComponent
                                                            .class)
                                                    .map(
                                                        ItemComponent
                                                            .class
                                                            ::cast)
                                                    .get()
                                                    .getItemData()))
                                            Game.removeEntity(WorldItemEntity);
                                    });
                        }
                    });
        }
    };

    static IOnDrop drop = null;

    static ItemType type = Passive;

    static IOnUse use = null;


    /**
     * creates a new Sword object.
     *
     *
     * @param inventoryTexture
     * @param worldTexture
     * @param itemName
     * @param description
     *
     */
    public Swords(Animation inventoryTexture,
                  Animation worldTexture,
                  String itemName,
                  String description){
        super(type,
            inventoryTexture,
            worldTexture,
            itemName,
            description,
            collect,
            drop,
            use,
            new DamageModifier());
    }
}
