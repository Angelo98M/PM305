package ecs.entities.Monsters;

import dslToGame.AnimationBuilder;
import ecs.Quests.QuestLog;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.Sleep;
import ecs.components.ai.transition.ITransition;
import ecs.entities.Entity;
import ecs.entities.Items.DropSystem.MobLoot;
import ecs.entities.Monster;
import graphic.Animation;

/**
 * the Mimic is a Monster that tries to disguise itself as a chest. It randomly spawns in the
 * Dungeon but is peaceful until the Hero tries to Interact with it.
 * onDeath the Mimik drops an Item via calling the MobLoot-Strategy
 */
public class Mimic extends Monster {
    static float speed = 0.1f;
    static Animation animation = AnimationBuilder.buildAnimation("character/monster/mimik_passive");
    static Animation walkAnimation =
            AnimationBuilder.buildAnimation("character/monster/mimik_active");

    static IFightAI fight = new CollideAI(1);
    static IIdleAI idle = new Sleep();
    ITransition trans;
    static Animation getHitAnimation =
            AnimationBuilder.buildAnimation("character/monster/chort/mimik_spill");
    static Animation dieAnimation =
            AnimationBuilder.buildAnimation("character/monster/mimik/mimik_spill");

    static int maxHealth = 10;

    private boolean interacted;

    public Mimic() {

        super(
                speed,
                animation,
                animation,
                walkAnimation,
                walkAnimation,
                fight,
                idle,
                new ITransition() {
                    @Override
                    public boolean isInFightMode(Entity entity) {
                        return false;
                    }
                },
                getHitAnimation,
                dieAnimation,
                maxHealth);
        interacted = false;
        ((AIComponent) this.getComponent(AIComponent.class).get())
                .setTransitionAI(
                        new ITransition() {
                            @Override
                            public boolean isInFightMode(Entity entity) {
                                return interacted;
                            }
                        });

        new InteractionComponent(
                this,
                1f,
                false,
                new IInteraction() {
                    @Override
                    public void onInteraction(Entity entity) {
                        interacted = true;

                        updateAnimation();
                    }
                });
        ((HealthComponent) this.getComponent(HealthComponent.class).get())
                .setOnDeath(
                        new IOnDeathFunction() {
                            @Override
                            public void onDeath(Entity entity) {
                                QuestLog.getInstance().checkAllQuests(entity);
                                new MobLoot(entity);
                            }
                        });
    }
    /** Updates the Animation of the Mimic after it was interacted with */
    public void updateAnimation() {

        this.removeComponent(AnimationComponent.class);
        this.addComponent(new AnimationComponent(this, walkAnimation, walkAnimation));
    }

    public boolean getInteracted() {
        return interacted;
    }
}
