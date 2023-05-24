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

public class MeeleAttack extends DamageProjectileSkill{

    private static String animationPath= "hallo";
    private static float speed = 3f;
    private static Damage dmg = new Damage(2, DamageType.PHYSICAL,null);
    private static Point size = new Point(4,2);
    private static ITargetSelection targetting = new ClosestToHero();
    private static float range = 3f;

    public MeeleAttack(){
        super(animationPath,speed,dmg,size,targetting,range);
    }
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
                                Point now =((PositionComponent) pc).getPosition();
                                ((PositionComponent)pc).setPosition(getKnockbackCoordinate(now));
                            }
                        );
                }
            };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), size, collide, null);
    }
    public Point getKnockbackCoordinate(Point now){
        Point hero = ((PositionComponent)Game.getHero().get().getComponent(PositionComponent.class).get()).getPosition();
        float directionX = hero.x-now.x;
        float directionY = hero.y-now.y;
        if(directionX<=-0.2){
            if(directionY<-0.5){
                return new Point(now.x+1,now.y+1);
            }
            if(directionY<0.5){
                return new Point(now.x+1,now.y-1);
            }
            if(directionY>-0.5&&directionY<0.5){
                return new Point(now.x+1,now.y);
            }
        }
        if (directionX >= 0.2) {
            if(directionY<-0.5){
                return new Point(now.x-1,now.y+1);
            }
            if(directionY<0.5){
                return new Point(now.x-1,now.y-1);
            }
            if(directionY>-0.5&&directionY<0.5){
                return new Point(now.x-1,now.y);
            }
        }
        if(0.2>directionX&&directionX>-0.2){
            if(directionY<=0){
                return new Point(now.x,now.y+1);
            }
            if(directionY>0){
                return new Point(now.x,now.y-1);
            }

        }
        return new Point(0,0);
    }
    public void updateAnimation(Point target){
        Point hero = ((PositionComponent)Game.getHero().get().getComponent(PositionComponent.class).get()).getPosition();
        float directionX = hero.x-target.x;
        float directionY = hero.y-target.y;
        if(directionX<=-0.2){
            if(directionY<-0.5){
                animationPath = "character/knight/attack/nw";
            }
            if(directionY<0.5){
                animationPath = "character/knight/attack/nw";
            }
            if(directionY>-0.5&&directionY<0.5){
                animationPath = "character/knight/attack/w";
            }
        }
        if (directionX >= 0.2) {
            if(directionY<-0.5){
                animationPath = "character/knight/attack/ne";
            }
            if(directionY<0.5){
                animationPath = "character/knight/attack/se";
            }
            if(directionY>-0.5&&directionY<0.5){
                animationPath = "character/knight/attack/e";
            }
        }
        if(0.2>directionX&&directionX>-0.2){
            if(directionY<=0){
                animationPath = "character/knight/attack/n";
            }
            if(directionY>0){
                animationPath = "character/knight/attack/s";
            }

        }

    }
}
