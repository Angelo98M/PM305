package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.systems.ProjectileSystem;
import graphic.Animation;
import level.elements.tile.WallTile;
import starter.Game;
import tools.Point;

import java.util.List;

/** This Class implements a Piercing Arrow Projectile which only despawns on Collision with WallTiles*/
public class ArrowProjectileSkill extends DamageProjectileSkill{

        private static String animationPath = "game/assets/dungeon/Arrow";
        private static float speed = 0.4f;
        private static Damage dmg = new Damage(1, DamageType.PHYSICAL, null);
        private static Point size = new Point(0.5f, 0.5f);

        private static ITargetSelection targetting = SkillTools::getCursorPositionAsPoint;
        private static float range = 30f;

        private static String pathToTexturesOfProjectile = "game/assets/dungeon/Arrow";

        public ArrowProjectileSkill() {
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

            Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
            new AnimationComponent(projectile, animation);

            Point aimedOn = targetting.selectTargetPoint();
            List<WallTile> walltiles = Game.currentLevel.getWallTiles();
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
                                    SkillTools.takeKnockback(((PositionComponent)Game.getHero().get().getComponent(PositionComponent.class).get()).getPosition(),b);
                                });
                        if(walltiles.contains(b)){
                            Game.removeEntity(projectile);
                        }
                    }
                };
            new HitboxComponent(projectile, new Point(0.25f, 0.25f), size, collide, null);
        }


}


