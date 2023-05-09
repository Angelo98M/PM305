package ecs.entities.Monsters;

import dslToGame.AnimationBuilder;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.idle.Sleep;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Monster;
import graphic.Animation;
/**
 * A Blue Chort is a sleepy Monster. It sleeps in the Dungeon until awoken by the player
 * this classs has all its components and attributes .
 */
public class BlueChort extends Monster {
    static float speed = 0.1f;
    static Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/chortblue/idleLeft");
    static Animation idleRight = AnimationBuilder.buildAnimation("character/monster/chortblue/idleRight");

    static Animation runLeft = AnimationBuilder.buildAnimation("character/monster/chortblue/runLeft");
    static Animation runRight = AnimationBuilder.buildAnimation("character/monster/chortblue/runRight");

    static IFightAI fight = new CollideAI(1);
    static IIdleAI idle = new Sleep();
    static ITransition trans = new RangeTransition(2);
    static Animation getHitAnimation = AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
    static Animation dieAnimation = AnimationBuilder.buildAnimation("character/monster/chort/runLeft");

    static int maxHealth = 5;
    public BlueChort(){
        super(speed,idleLeft,idleRight,runLeft,runRight,fight,idle,trans, getHitAnimation, dieAnimation,maxHealth);
    }
}
