package ecs.entities.Monsters;

import dslToGame.AnimationBuilder;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.*;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Monster;
import graphic.Animation;

/**
 * A Chort is a dutiful Monster. It patrols the Dungeon until it is in range of the Player this
 * classs has all its components and attributes .
 */
public class Chort extends Monster {
    static float speed = 0.1f;
    static Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/chort/idleLeft");
    static Animation idleRight =
            AnimationBuilder.buildAnimation("character/monster/chort/idleRight");

    static Animation runLeft = AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
    static Animation runRight = AnimationBuilder.buildAnimation("character/monster/chort/runRight");

    static IFightAI fight = new CollideAI(1);
    static IIdleAI idle = new RadiusWalk(4f, 3);
    static ITransition trans = new RangeTransition(3);
    static Animation getHitAnimation =
            AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
    static Animation dieAnimation =
            AnimationBuilder.buildAnimation("character/monster/chort/runLeft");

    static int maxHealth = 3;

    public Chort() {
        super(
                speed,
                idleLeft,
                idleRight,
                runLeft,
                runRight,
                fight,
                idle,
                trans,
                getHitAnimation,
                dieAnimation,
                maxHealth);
    }
}
