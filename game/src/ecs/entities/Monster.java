package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.Quests.QuestLog;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import level.elements.tile.Tile;
import org.antlr.v4.runtime.atn.Transition;
import tools.Point;
/**
 * The Monsters are unfriendly NPCs. It's entity in the ECS. This class helps to setup the Monsters with
 * all their components and attributes .
 */
public abstract class Monster extends Entity{
    /** Entity with Components */
    public Monster(){

    }
    public Monster(float speed, Animation idleLeft, Animation idleRight, Animation runLeft, Animation runRight, IFightAI fight, IIdleAI idle, ITransition trans,Animation getHitAnimation, Animation dieAnimation,int maxHealth){
        super();
        PositionComponent pos = new PositionComponent(this);

        new AnimationComponent(this,idleLeft,idleRight);
        VelocityComponent vel = new VelocityComponent(this, speed, speed, runLeft, runRight);
        AIComponent ai = new AIComponent (this, fight, idle, trans);
        Damage dmg = new Damage(1, DamageType.PHYSICAL,null);
        HealthComponent health = new HealthComponent(this, maxHealth, new IOnDeathFunction() {
            @Override
            public void onDeath(Entity entity) {
                QuestLog.getInstance().checkAllQuests(entity);
            }
        },dieAnimation, getHitAnimation);
        HitboxComponent hit = new HitboxComponent(this, new ICollide() {
            @Override
            public void onCollision(Entity a, Entity b, Tile.Direction from) {
                if (b.getComponent(HealthComponent.class).isPresent()){
                    ((HealthComponent)b.getComponent(HealthComponent.class).get()).receiveHit(dmg);
                }
            }
        },
            (you, other, direction) -> System.out.println("HeroCollisionLeave")/*health.receiveHit(dmg)*/);
}
}
