package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import starter.Game;


/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */

    public class Hero extends Entity {

        private final int fireballCoolDown = 5;
        private final float xSpeed = 0.3f;
        private final float ySpeed = 0.3f;
        private Damage dmg = new Damage(2, DamageType.PHYSICAL,null);
        private final String pathToIdleLeft = "knight/idleLeft";
        private final String pathToIdleRight = "knight/idleRight";
        private final String pathToRunLeft = "knight/runLeft";
        private final String pathToRunRight = "knight/runRight";
        private Skill firstSkill;

        /** Entity with Components */
        public Hero() {
            super();
            new PositionComponent(this);
            new HealthComponent(this, 3333, new IOnDeathFunction() {
                @Override
                public void onDeath(Entity entity) {

                }
            },AnimationBuilder.buildAnimation("character/knight/hit/knight_m_hit_anim_f0.png"),AnimationBuilder.buildAnimation("character/knight/hit/knight_m_hit_anim_f0.png"));
            new InventoryComponent(this,4);
            setupVelocityComponent();
            setupAnimationComponent();
            setupHitboxComponent();
            PlayableComponent pc = new PlayableComponent(this);
            setupFireballSkill();
            pc.setSkillSlot1(firstSkill);

        }

        private void setupVelocityComponent() {
            Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
            Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
            new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
        }

        private void setupAnimationComponent() {
            Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
            Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
            new AnimationComponent(this, idleLeft, idleRight);
        }

        private void setupFireballSkill() {
            firstSkill =
                    new Skill(
                            new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
        }

        private void setupHitboxComponent() {
            HitboxComponent hit = new HitboxComponent(this, (you, other, direction) -> ((HealthComponent)other.getComponent(HealthComponent.class).get()).receiveHit(dmg),
                (you, other, direction) -> System.out.println("chortCollisionLeave")/*health.receiveHit(dmg)*/);
        }
    }

