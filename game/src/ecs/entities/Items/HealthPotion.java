package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.items.Consumables;
import ecs.items.IOnUse;
import ecs.items.ItemData;
import graphic.Animation;
import starter.Game;

public class HealthPotion extends Consumables {

    static Animation inventoryTexture = AnimationBuilder.buildAnimation("items/potions/Healthpotion");

    static String itemName = "Potion of Healing";

    static String description = "A Potion that restores 3 HP";

    static IOnUse use = new IOnUse() {
                            @Override
                            public void onUse(Entity e, ItemData item) {
                                Game.getHero()
                                    .ifPresent(
                                        hero -> {
                                            hero.getComponent(HealthComponent.class).ifPresent(
                                                (x) -> {
                                                    ((HealthComponent) x)
                                                        .setCurrentHealthpoints(
                                                            ((HealthComponent)x).getCurrentHealthpoints()+3);
                                                }
                                            );
                    });
            InventoryComponent inv = (InventoryComponent) Game.getHero().get().getComponent(InventoryComponent.class).get();
            inv.removeItem(item);
        }
    };
    public HealthPotion(){
        super(inventoryTexture,
              inventoryTexture,
              itemName,
              description,
              use);
    }
}
