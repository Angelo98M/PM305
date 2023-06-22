package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Items.DropSystem.ChestLoot;
import java.util.List;

public class Chest extends Entity {

    public static final float defaultInteractionRadius = 1f;
    public static final List<String> DEFAULT_CLOSED_ANIMATION_FRAMES =
            List.of("objects/treasurechest/chest_full_open_anim_f0.png");
    public static final List<String> DEFAULT_OPENING_ANIMATION_FRAMES =
            List.of(
                    "objects/treasurechest/chest_full_open_anim_f0.png",
                    "objects/treasurechest/chest_full_open_anim_f1.png",
                    "objects/treasurechest/chest_full_open_anim_f2.png",
                    "objects/treasurechest/chest_empty_open_anim_f2.png");

    /** Creates a new Chest which uses a ChestLoot-Strategy on interaction */
    public Chest() {
        new PositionComponent(this);

        AnimationComponent ac =
                new AnimationComponent(
                        this,
                        AnimationBuilder.buildAnimation("objects/treasurechest_closed"),
                        AnimationBuilder.buildAnimation("objects/treasurechest_closed"));
        new InteractionComponent(
                this,
                defaultInteractionRadius,
                false,
                new IInteraction() {
                    @Override
                    public void onInteraction(Entity entity) {
                        new ChestLoot(entity);

                        ac.setCurrentAnimation(
                                AnimationBuilder.buildAnimation("objects/treasurechest_open"));
                    }
                });
    }
}
