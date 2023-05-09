package ecs.entities.Traps;


import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;

public class Spikes extends Traps {

    private final String  on = "dungeon/Traps/on_Spikes/falle_Spickes_1.png";
    private final String  off = "animation/missingTexture.png";

    public Spikes(){
        super();
        new PositionComponent(this, Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint());
        setupAnimationComponent();
        setupHitboxComponent();
        new Lever(this);


    }
    private void setupAnimationComponent() {
        Animation spikeOn = AnimationBuilder.buildAnimation(on);
        new AnimationComponent(this, spikeOn, spikeOn);
    }
    private void setupHitboxComponent() {
        new HitboxComponent(this, new ICollide() {
            @Override
            public void onCollision(Entity a, Entity b, Tile.Direction from) {
                if(b.getComponent(HealthComponent.class).isPresent()) {
                    HealthComponent health = (HealthComponent) b.getComponent(HealthComponent.class).get();
                    health.receiveHit(new Damage(1, DamageType.PHYSICAL, null));
                }
            }
        },
            (you, other, direction) -> System.out.println("Left Spike Hitbox"));
    }
 }

