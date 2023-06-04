package ecs.entities;

import dslToGame.AnimationBuilder;

import ecs.components.*;
import ecs.items.ItemData;
import graphic.Animation;
import starter.Game;


/**
 *Class for the Entity NpcQuestion
 * */
public class NpcQuestion extends Entity {
    private Animation idle= AnimationBuilder.buildAnimation("game/assets/character/Npc/NPCQuestion.png");

    public NpcQuestion(){
        super();
        new PositionComponent(this, Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint());
        new AnimationComponent(this, idle);
        new InteractionComponent(this,1.0f,false, new IInteraction() {

            @Override
            public void onInteraction(Entity entity) {
                System.out.println("hello");
            }
        });
    }
    public void giveReward(ItemData item)
    {
        Game.getHero().get().getComponent(InventoryComponent.class).ifPresent((x) ->{
            ((InventoryComponent) x)
                .addItem((item));
        });
    }
}
