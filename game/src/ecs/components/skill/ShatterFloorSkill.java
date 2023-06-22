package ecs.components.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;


/** This Class implements the ShatterFloorSkill which is a projectile without a Hitbox that slowly traverses the dungeonfloor
 *  when it reaches its target destination it explodes and damages Enemies around it. Some handling for this skills functionality is handeled in ProjectileSystem*/
public class ShatterFloorSkill extends DamageProjectileSkill{

    private static String animationPath = "game/assets/skills/shatterFloor/target";
    private static float speed = 0.05f;
    private static Damage dmg = new Damage(10, DamageType.PHYSICAL, null);
    private static Point size = new Point(2f, 2f);

    private static ITargetSelection targetting = SkillTools::getCursorPositionAsPoint;
    private static float range = 30f;

    private static String pathToTexturesOfProjectile = "game/assets/skills/shatterFloor/explo";

    public ShatterFloorSkill() {
        super(animationPath, speed, dmg, size, targetting, range);

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
        Animation animation = AnimationBuilder.buildAnimation(animationPath);
        new AnimationComponent(projectile, animation);
        Point aimedOn = targetting.selectTargetPoint();
        Point velocity =
            SkillTools.calculateVelocity(epc.getPosition(), aimedOn, speed);
        VelocityComponent vc =
            new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), aimedOn);
        System.out.println(((PositionComponent)projectile.getComponent(PositionComponent.class).get()).getPosition().x+"/"+((PositionComponent)projectile.getComponent(PositionComponent.class).get()).getPosition().y);
        System.out.println(projectile.toString());
        new HitboxComponent(projectile,new Point(0,0),size,null, null);
    }
}
