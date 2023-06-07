package ecs.items;

import static ecs.items.ItemType.Passive;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.components.stats.DamageModifier;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public abstract class Armor extends ItemData {
    static IOnCollect collect =
            new IOnCollect() {
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
                                                                    Game.removeEntity(
                                                                            WorldItemEntity);
                                                            });
                                        }
                                    });
                }
            };

    static IOnDrop drop =
            new IOnDrop() {
                @Override
                public void onDrop(Entity user, ItemData which, Point position) {
                    WorldItemBuilder itemBuilder = new WorldItemBuilder();
                    itemBuilder.buildWorldItem(which, position);
                }
            };

    static ItemType type = Passive;

    static IOnUse use = null;

    /**
     * creates a new Armor object.
     *
     * @param inventoryTexture
     * @param worldTexture
     * @param itemName
     * @param description
     */
    public Armor(
            Animation inventoryTexture,
            Animation worldTexture,
            String itemName,
            String description) {
        super(
                type,
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
