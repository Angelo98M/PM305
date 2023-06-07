package ecs.entities.Traps;

import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import graphic.Animation;

public abstract class Traps extends Entity {
    public Traps() {}

    public Traps(boolean isActivate, int dmg, Animation sprite) {
        super();
        new AnimationComponent(this, sprite);
        PositionComponent pos = new PositionComponent(this);
        setupHitboxComponent();
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> System.out.println("arrowCollisionEnter"),
                (you, other, direction) -> System.out.println("arrowCollisionLeave"));
    }
}
