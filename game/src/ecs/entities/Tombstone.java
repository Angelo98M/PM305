package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import ecs.components.collision.OnColilisonDeleteGrabstein;
import graphic.Animation;

public class Tombstone extends Entity {
    /**
     * The Tomestone is an Entity that spawns wiht a Gohst attacht to it so if the player findes and
     * touches the Tomestone both the Tomestone and the Gohst will be removed form the Level
     */
    private Ghost geist;

    private Animation idle = AnimationBuilder.buildAnimation("dungeon/Tombstone");
    private ICollide onColison = new OnColilisonDeleteGrabstein();
    /**
     * The Constructor helps to set up the Components
     *
     * @param geist Ghost
     */
    public Tombstone(Ghost geist) {
        super();
        this.geist = geist;
        new PositionComponent(this);
        new AnimationComponent(this, idle);
        new HitboxComponent(this, onColison, null);
    }

    @Override
    public void OnDelete() {
        // reward
        geist.RemoveGeist();
    }
}
