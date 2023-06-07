package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import graphic.Animation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import starter.Game;

/** Class for the Entity NpcQuestion */
public class NpcPenguin extends Entity {
    private Animation idle =
        AnimationBuilder.buildAnimation(
            "game/assets/character/Npc/NPC_pinguin_Sprechblase.png");

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
       // Pattern p = Pattern.compile("[eight\\\\EIGHT\\\\8]+");

