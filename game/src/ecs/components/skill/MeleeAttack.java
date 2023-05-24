package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

import static java.lang.Math.sqrt;

public class MeleeAttack extends DamageProjectileSkill{

    private static String animationPath= "";
    private static float speed = 0.05f;
    private static Damage dmg = new Damage(2, DamageType.PHYSICAL,null);
    private static Point size = new Point(0.5f,0.2f);
    private static ITargetSelection targetting = new ClosestToHero();
    private static float range = 0.5f;

    /**
     * Constructor for the MeeleAttack
     *
     * @Param animationPath for the standard animationPath
     * @Param speed for the speed of the Attack
     * @Param dmg for the Damage the attack does
     * @Param size for the Size of the Sword
     * @Param targetting the ITargetSelection
     * @Param range is the Range the attack travels.
     */
    public MeleeAttack(){
        super(animationPath,speed,dmg,size,targetting,range);
    }

    /**
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        Entity projectile = new Entity();
        PositionComponent epc =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());
        Point aimedOn = targetting.selectTargetPoint();
        updateAnimation(aimedOn);
        Animation animation = AnimationBuilder.buildAnimation(animationPath);
        new AnimationComponent(projectile, animation);


        Point targetPoint =
            SkillTools.calculateLastPositionInRange(
                epc.getPosition(), aimedOn, range);
        Point velocity =
            SkillTools.calculateVelocity(epc.getPosition(), targetPoint, speed);
        VelocityComponent vc =
            new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);
        ICollide collide =
            (a, b, from) -> {
                if (b != entity) {
                    b.getComponent(HealthComponent.class)
                        .ifPresent(
                            hc -> {
                                ((HealthComponent) hc).receiveHit(dmg);
                                Game.removeEntity(projectile);
                            });
                    b.getComponent(PositionComponent.class)
                        .ifPresent(
                            pc -> {
                                if( b.getClass().getSuperclass().getSimpleName().equals("Monster")) {
                                    Point now = ((PositionComponent) pc).getPosition();
                                    ((PositionComponent) pc).setPosition(getKnockbackCoordinate(now));
                                }
                            }
                        );
                }
            };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), size, collide, null);
    }

    /**
     *
     * @param now The Point at which the Knockbacked enemy is at right now
     * @return the updated Point the enemy should be knocked into
     */

    public Point getKnockbackCoordinate(Point now){
        Point hero = ((PositionComponent)Game.getHero().get().getComponent(PositionComponent.class).get()).getPosition();
        float directionX = hero.x-now.x;
        float directionY = hero.y-now.y;

        if(directionX<=-1){
            if(directionY<-0.5){
                return new Point(now.x+0.2f,now.y+0.2f);
            }
            if(directionY<0.5){
                return new Point(now.x+0.2f,now.y-0.2f);
            }
            if(directionY>-0.5&&directionY<0.5){
                return new Point(now.x+0.2f,now.y);
            }
        }
        if (directionX >= 1) {
            if(directionY<-0.5){
                return new Point(now.x-0.2f,now.y+0.2f);
            }
            if(directionY<0.5){
                return new Point(now.x-0.2f,now.y-0.2f);
            }
            if(directionY>-0.5&&directionY<0.5){
                return new Point(now.x-0.2f,now.y);
            }
        }
        if(1>directionX&&directionX>-1){
            if(directionY<=0){
                return new Point(now.x,now.y+0.2f);
            }
            if(directionY>0){
                return new Point(now.x,now.y-0.2f);
            }

        }
        return new Point(0,0);
    }

    /**
     *
     * @param target The Point of the Target to Calculate the angle at which the Enemy is struck
     */
    public void updateAnimation(Point target){
        Point hero = ((PositionComponent)Game.getHero().get().getComponent(PositionComponent.class).get()).getPosition();
        float directionX = hero.x-target.x;
        float directionY = hero.y-target.y;
        float hypothenuse = (float) sqrt((directionX * directionX + directionY * directionY));
        if(hypothenuse==0){
            hypothenuse=1;
        }
        double arc = Math.sin((directionY/hypothenuse));

        if(directionX==0){
            if(directionY<0){
                animationPath = "character/knight/attack/north/north";
            }
            if(directionY>0){
                animationPath = "character/knight/attack/south/south";
            }
        }
        if (directionX >0) {

            if(directionY==0){
                animationPath = "character/knight/attack/west";
            }
            if(directionY<0){
                if (arc>-0.4){
                    animationPath="character/knight/attack/west";
                }else if (arc<-0.80){
                    animationPath="character/knight/attack/north/north";
                }else if(arc>=-0.8&&arc<=-0.4) {
                    animationPath = "character/knight/attack/northwest";
                }
            }
            if(directionY>0){
                if (arc<0.4){
                    animationPath="character/knight/attack/west";
                }else if (arc>0.80){
                    animationPath="character/knight/attack/south/south";
                }else if(arc<=0.8&&arc>=0.4) {
                    animationPath = "character/knight/attack/southwest";
                }
            }
        }
        if(directionX<0){
            if(directionY==0){
                animationPath = "character/knight/attack/east";
            }
            if(directionY>0){
                if (arc<0.4){
                    animationPath="character/knight/attack/east";
                }else if (arc>0.80){
                    animationPath="character/knight/attack/north/north";
                }else if(arc<=0.80&&arc>=0.4) {
                    animationPath = "character/knight/attack/southeast";
                }
            }
            if(directionY<0){
                if (arc>-0.4){
                    animationPath="character/knight/attack/east";
                }else if (arc<-0.80){
                    animationPath="character/knight/attack/north/north";
                }else if(arc>=-0.80&&arc<=-0.4) {
                    animationPath = "character/knight/attack/northeast";
                }
            }
        }

    }
}
