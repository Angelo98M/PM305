package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.entities.Entity;
import ecs.items.Armor;
import ecs.items.IOnCollect;
import graphic.Animation;
import starter.Game;

public class RubberArmor extends Armor {

    static Animation inventory = AnimationBuilder.buildAnimation("RubberArmor.png");
    static Animation world = AnimationBuilder.buildAnimation("RubberArmor.png");
    static String description = "It lets Attacks bounce right of you";
    static String itemName = "Gummirüstung";

    public RubberArmor() {

        super(inventory, world, itemName, description);
        setItemPrice(60);
        this.setOnCollect(
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
                        HealthComponent health =
                                (HealthComponent)
                                        Game.getHero()
                                                .get()
                                                .getComponent(HealthComponent.class)
                                                .get();
                        health.setMaximalHealthpoints(health.getMaximalHealthpoints() + 5);
                        health.setCurrentHealthpoints(health.getCurrentHealthpoints() + 5);
                    }
                });
    }
}
