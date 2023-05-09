package ecs.entities.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.toPointAI;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class Pressureplate extends Traps {

    public Pressureplate(Arrow arrow) {
        super();
        new AnimationComponent(this,AnimationBuilder.buildAnimation("dungeon/Traps/PressurePlate/PressurePlate.png"),AnimationBuilder.buildAnimation("dungeon/Traps/PressurePlate/PressurePlate.png"));
        new PositionComponent(this);
        new HitboxComponent(this,
            new ICollide() {
                @Override
                public void onCollision(Entity a, Entity b, Tile.Direction from) {
                    PositionComponent pos =
                        (PositionComponent)
                            a.getComponent(PositionComponent.class)
                                .orElseThrow(
                                    () -> new MissingComponentException("VelocityComponent"));

                    VelocityComponent velo =
                        (VelocityComponent)
                            arrow.getComponent(VelocityComponent.class)
                                .orElseThrow(
                                    () -> new MissingComponentException("VelocityComponent"));
                    velo.setCurrentXVelocity(arrow.getxSpeed());
                    velo.setCurrentYVelocity(arrow.getySpeed());
                    if(!arrow.getComponent(AIComponent.class).isPresent()) {

                        new AIComponent(arrow, new CollideAI(0), new toPointAI(arrow.getPosition(), arrow.getDestination()), new RangeTransition(0f));
                    }
                }
            },
            (you, other, direction) -> System.out.println("pressureplatCollisionLeave"));

    }


}
