package ecs.entities.Traps;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.List;
import java.util.Random;
import level.elements.tile.Tile;
import level.elements.tile.WallTile;
import starter.Game;

public class Lever extends Traps {

    Animation right =
            AnimationBuilder.buildAnimation(
                    "dungeon/Traps/Left_Wall_Lever/Left_Wall_lever_off.png");

    public Lever(Traps spikes) {

        AnimationComponent anima = new AnimationComponent(this, right, right);
        Random rnd = new Random();
        new InteractionComponent(
                this,
                2f,
                false,
                new IInteraction() {
                    @Override
                    public void onInteraction(Entity entity) {
                        HitboxComponent hit =
                                (HitboxComponent) spikes.getComponent(HitboxComponent.class).get();
                        AnimationComponent ani =
                                (AnimationComponent)
                                        spikes.getComponent(AnimationComponent.class).get();
                        hit.setiCollideEnter(
                                new ICollide() {
                                    @Override
                                    public void onCollision(
                                            Entity a, Entity b, Tile.Direction from) {}
                                });
                        hit.setiCollideLeave(
                                new ICollide() {
                                    @Override
                                    public void onCollision(
                                            Entity a, Entity b, Tile.Direction from) {}
                                });
                        ani.setCurrentAnimation(
                                AnimationBuilder.buildAnimation(
                                        "dungeon/Traps/off_Spikes/floor_1.png"));
                        anima.setCurrentAnimation(
                                AnimationBuilder.buildAnimation(
                                        "dungeon/Traps/Right_Wall_Lever/Right_Wall_lever_off.png"));
                    }
                });
        List<WallTile> wall = Game.currentLevel.getWallTiles();
        new PositionComponent(this, wall.get(rnd.nextInt(wall.size())).getCoordinateAsPoint());
    }
}
