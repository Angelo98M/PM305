package ecs.entities.Items;

import dslToGame.AnimationBuilder;
import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.IOnCollect;
import ecs.items.Swords;
import graphic.Animation;
import starter.Game;

public class GreatSword extends Swords {
    int dmg = 4;
    static Animation inventory = AnimationBuilder.buildAnimation("greatSword.png");
    static Animation world = AnimationBuilder.buildAnimation("greatSword.png");
    static String description = "Your mom bought you this for your grand Adventure";
    static String itemName = "Großschwert";

    public GreatSword() {
        super(inventory, world, itemName, description);
        setItemPrice(40);
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
                        Hero hero = (Hero) Game.getHero().get();
                        hero.setDmg(dmg);
                    }
                });
    }
}
