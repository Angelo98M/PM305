package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import graphic.Animation;
import starter.Game;

/** Class for the Entity NpcPenguin */
public class NpcPenguin extends Entity {
    private Animation idle =
            AnimationBuilder.buildAnimation(
                    "game/assets/character/Npc/NPC_pinguin_Sprechblase.png");
    /** Entity with Components */
    public NpcPenguin() {
        super();
        new PositionComponent(this);
        new AnimationComponent(this, idle);
        new InteractionComponent(
                this,
                1.0f,
                false,
                new IInteraction() {
                    @Override
                    public void onInteraction(Entity entity) {
                        Game.toggleRaetsel();
                    }
                });
    }
}
