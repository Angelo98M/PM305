package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;

public class Imp extends Monster{
    static float speed = 0.1f;
    static Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/imp/idleLeft");
    static Animation idleRight = AnimationBuilder.buildAnimation("character/monster/imp/idleRight");

    static Animation runLeft = AnimationBuilder.buildAnimation("character/monster/imp/runLeft");
    static Animation runRight = AnimationBuilder.buildAnimation("character/monster/imp/runRight");

    static IFightAI fight = new CollideAI(1);
    static IIdleAI idle = new RadiusWalk(15, 1);
    static ITransition trans = new RangeTransition(1);
    static Animation getHitAnimation = AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
    static Animation dieAnimation = AnimationBuilder.buildAnimation("character/monster/chort/runLeft");

    static int maxHealth = 1;
    public Imp(){
        super(speed,idleLeft,idleRight,runLeft,runRight,fight,idle,trans, getHitAnimation, dieAnimation,maxHealth);
    }
}
