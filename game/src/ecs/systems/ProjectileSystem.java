package ecs.systems;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.components.skill.ProjectileComponent;
import ecs.components.skill.ShatterFloorSkill;
import ecs.components.skill.SkillTools;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

import static java.util.function.Predicate.not;

public class ProjectileSystem extends ECS_System {

    // private record to hold all data during streaming
    private record PSData(
            Entity e, ProjectileComponent prc, PositionComponent pc, VelocityComponent vc) {}

    /** sets the velocity and removes entities that reached their endpoint
     *  also executes functions for Projectiles that trigger on reaching their destination*/
    @Override
    public void update() {

        Game.getEntities().stream()
            // Consider only entities that have a ProjectileComponent
            .flatMap(e -> e.getComponent(ProjectileComponent.class).stream())
            .map(prc -> buildDataObject((ProjectileComponent) prc))
            .map(this::setVelocity)
            // Filter all entities that have reached their endpoint
            .filter(
                psd ->
                    hasReachedEndpoint(
                        psd.prc.getStartPosition(),
                        psd.prc.getGoalLocation(),
                        psd.pc.getPosition()))
            // Remove all entities who reached their endpoint and are not ShatterFloorSkill-Projectiles
            // if it is ShatterFloorSkill-Projectiles assing Damage to every Monster Entity around it and set new animation
            .forEach(psd -> {
                if(!((VelocityComponent)psd.e.getComponent(VelocityComponent.class).get()).getMoveLeftAnimation().getNextAnimationTexturePath().equals("C:/Users/Philipp/Documents/GitHub/PM305/game/assets/skills/shatterFloor/target/Shatter_Target.png")){
                removeEntitiesOnEndpoint(psd);
                }else{
                    Game.getEntities().stream()
                        .filter(e -> e.getComponent(PositionComponent.class).isPresent())
                        .filter(e -> e.getComponent(HealthComponent.class).isPresent())
                        .forEach(e -> {
                            if(e!=Game.getHero().get()){
                                if(getInRange(psd.e,e)){
                                    ((HealthComponent)e.getComponent(HealthComponent.class).get()).receiveHit(new Damage(2, DamageType.PHYSICAL,null));
                                }
                            }
                        });
                    VelocityComponent ani = ((VelocityComponent)psd.e.getComponent(VelocityComponent.class).get());
                    ani.setMoveLeftAnimation(AnimationBuilder.buildAnimation("game/assets/skills/shatterFloor/explo/shatter_2.png"));
                    ani.setMoveRightAnimation(AnimationBuilder.buildAnimation("game/assets/skills/shatterFloor/explo/shatter_2.png"));
                }
            });
   }

    private PSData buildDataObject(ProjectileComponent prc) {
        Entity e = prc.getEntity();

        PositionComponent pc =
                (PositionComponent)
                        e.getComponent(PositionComponent.class)
                                .orElseThrow(ProjectileSystem::missingAC);
        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(ProjectileSystem::missingAC);

        return new PSData(e, prc, pc, vc);
    }

    private PSData setVelocity(PSData data) {
        data.vc.setCurrentYVelocity(data.vc.getYVelocity());
        data.vc.setCurrentXVelocity(data.vc.getXVelocity());

        return data;
    }

    private void removeEntitiesOnEndpoint(PSData data) {
        Game.removeEntity(data.pc.getEntity());
    }

    /**
     * checks if the endpoint is reached
     *
     * @param start position to start the calculation
     * @param end point to check if projectile has reached its goal
     * @param current current position
     * @return true if the endpoint was reached or passed, else false
     */
    public boolean hasReachedEndpoint(Point start, Point end, Point current) {
        float dx = start.x - current.x;
        float dy = start.y - current.y;
        double distanceToStart = Math.sqrt(dx * dx + dy * dy);

        dx = start.x - end.x;
        dy = start.y - end.y;
        double totalDistance = Math.sqrt(dx * dx + dy * dy);

        if (distanceToStart > totalDistance) {
            // The point has reached or passed the endpoint
            return true;
        } else {
            // The point has not yet reached the endpoint
            return false;
        }
    }

    private static MissingComponentException missingAC() {
        return new MissingComponentException("AnimationComponent");
    }

    /** Check for ShatterFloorSkill if Enemies are in Range of the detonation */
    private boolean getInRange(Entity checkFrom, Entity checkIf){
        PositionComponent pcFrom = (PositionComponent) checkFrom.getComponent(PositionComponent.class).get();
        PositionComponent pcIf = (PositionComponent) checkIf.getComponent(PositionComponent.class).get();
        if((2>pcFrom.getPosition().x-pcIf.getPosition().x&&-2<pcFrom.getPosition().x-pcIf.getPosition().x)&&(2>pcFrom.getPosition().y-pcIf.getPosition().y&&-2<pcFrom.getPosition().y-pcIf.getPosition().y)){
            return true;
        }
        return false;
    }
}
